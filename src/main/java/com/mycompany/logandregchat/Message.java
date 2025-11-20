/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.logandregchat;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Message class updated for Part 3:
 * - keeps arrays for sent/stored/disregarded messages
 * - keeps arrays for message hashes, message IDs, recipients
 * - provides search, delete and reporting operations
 */
public class Message {

    // Arrays required by assignment (public static so tests and main can read them)
    public static final List<Message> sentMessages = new ArrayList<>();
    public static final List<Message> disregardedMessages = new ArrayList<>();
    public static final List<Message> storedMessages = new ArrayList<>();

    public static final List<String> messageHashes = new ArrayList<>();
    public static final List<String> messageIds = new ArrayList<>();
    public static final List<String> recipients = new ArrayList<>();

    private static int numMessagesSent = 0;

    private String messageId;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String flag = ""; // "Sent", "Stored", "Disregarded"

    public Message(String recipient, String messageText) {
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageId = generateMessageId();
        this.messageHash = createMessageHash();
    }

    private String generateMessageId() {
        Random rand = new Random();
        long id = 1000000000L + (long) (rand.nextDouble() * 8999999999L);
        return String.valueOf(id);
    }

    // validate recipient (starts with + and 10-12 chars)
    public boolean checkRecipientNumber() {
        return recipient != null && recipient.startsWith("+") && recipient.length() >= 10 && recipient.length() <= 12;
    }

    // validate message length <= 250
    public boolean checkMessageLength() {
        return messageText != null && messageText.length() <= 250;
    }

    // create hash "PR:MessageId:FIRST_LAST"
    public String createMessageHash() {
        if (messageText == null || messageText.isEmpty()) return "";
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();
        String prefix = messageText.length() >= 2 ? messageText.substring(0, 2).toUpperCase() : messageText.toUpperCase();
        return prefix + ":" + messageId + ":" + firstWord + "_" + lastWord;
    }

    /**
     * Attempt to send message using validation.
     * On success: add to sentMessages and arrays; increment counter and set flag.
     * Returns status string.
     */
    public String sendMessage() {
        if (!checkRecipientNumber()) {
            return "Invalid recipient number format.";
        }
        if (!checkMessageLength()) {
            return "Please enter a message of less than 250 characters.";
        }

        this.flag = "Sent";
        sentMessages.add(this);

        // maintain meta arrays
        messageHashes.add(this.messageHash);
        messageIds.add(this.messageId);
        recipients.add(this.recipient);

        numMessagesSent++;
        return "Message successfully sent!";
    }

    /**
     * Disregard the message (user chose to disregard).
     * Adds to disregardedMessages and metadata lists.
     */
    public void disregardMessage() {
        this.flag = "Disregarded";
        disregardedMessages.add(this);
        messageHashes.add(this.messageHash);
        messageIds.add(this.messageId);
        recipients.add(this.recipient);
    }

    /**
     * Store message to storedMessages (in-memory) and optionally write to file.
     */
    public void storeMessage() {
        this.flag = "Stored";
        storedMessages.add(this);
        messageHashes.add(this.messageHash);
        messageIds.add(this.messageId);
        recipients.add(this.recipient);
    }

    /**
     * Store message to a text file (no org.json) - convenient for manual inspection.
     */
    public void storeMessageToFile() {
        try (FileWriter fw = new FileWriter("stored_messages.txt", true)) {
            fw.write("Message ID: " + messageId + "\n");
            fw.write("Recipient: " + recipient + "\n");
            fw.write("Message: " + messageText + "\n");
            fw.write("Message Hash: " + messageHash + "\n");
            fw.write("Flag: " + flag + "\n");
            fw.write("----------------------------------------\n");
        } catch (IOException e) {
            System.out.println("Error storing message to file: " + e.getMessage());
        }
    }

    // ====== Part 3 utility methods ======

    /**
     * Populate the required arrays with the assignment's test data.
     * This bypasses validation to ensure arrays contain the exact test rows.
     */
    public static void populateTestData() {
        // Clear any existing data first
        sentMessages.clear();
        storedMessages.clear();
        disregardedMessages.clear();
        messageHashes.clear();
        messageIds.clear();
        recipients.clear();
        numMessagesSent = 0;

        // Test Data Message 1 (Sent)
        Message m1 = new Message("+27844557896", "Did you get the cake?");
        m1.flag = "Sent";
        sentMessages.add(m1);
        messageHashes.add(m1.messageHash);
        messageIds.add(m1.messageId);
        recipients.add(m1.recipient);
        numMessagesSent++;

        // Test Data Message 2 (Stored)
        Message m2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.");
        m2.flag = "Stored";
        storedMessages.add(m2);
        messageHashes.add(m2.messageHash);
        messageIds.add(m2.messageId);
        recipients.add(m2.recipient);

        // Test Data Message 3 (Disregarded)
        Message m3 = new Message("+27834484567", "Yohooooo, I am at your gate.");
        m3.flag = "Disregarded";
        disregardedMessages.add(m3);
        messageHashes.add(m3.messageHash);
        messageIds.add(m3.messageId);
        recipients.add(m3.recipient);

        // Test Data Message 4 (Sent) — note recipient given without '+' in assignment, keep as provided
        Message m4 = new Message("0838884567", "It is dinner time!!");
        m4.flag = "Sent";
        sentMessages.add(m4);
        messageHashes.add(m4.messageHash);
        messageIds.add(m4.messageId);
        recipients.add(m4.recipient);
        numMessagesSent++;

        // Test Data Message 5 (Stored)
        Message m5 = new Message("+27834484567", "OK, I am leaving without you.");
        m5.flag = "Stored";
        storedMessages.add(m5);
        messageHashes.add(m5.messageHash);
        messageIds.add(m5.messageId);
        recipients.add(m5.recipient);
    }

    /**
     * Return the longest message text across all arrays (sent + stored + disregarded).
     */
    public static String getLongestMessageAcrossAll() {
        String longest = "";
        for (Message m : sentMessages) {
            if (m.messageText != null && m.messageText.length() > longest.length()) longest = m.messageText;
        }
        for (Message m : storedMessages) {
            if (m.messageText != null && m.messageText.length() > longest.length()) longest = m.messageText;
        }
        for (Message m : disregardedMessages) {
            if (m.messageText != null && m.messageText.length() > longest.length()) longest = m.messageText;
        }
        return longest;
    }

    /**
     * Search for a message by messageId across all arrays. Returns the Message or null.
     */
    public static Message searchByMessageId(String id) {
        for (Message m : sentMessages) if (m.messageId.equals(id)) return m;
        for (Message m : storedMessages) if (m.messageId.equals(id)) return m;
        for (Message m : disregardedMessages) if (m.messageId.equals(id)) return m;
        return null;
    }

    /**
     * Return a list of Message entries matching a recipient (exact match).
     */
    public static List<Message> searchByRecipient(String recipientToFind) {
        List<Message> result = new ArrayList<>();
        for (Message m : sentMessages) if (m.recipient.equals(recipientToFind)) result.add(m);
        for (Message m : storedMessages) if (m.recipient.equals(recipientToFind)) result.add(m);
        for (Message m : disregardedMessages) if (m.recipient.equals(recipientToFind)) result.add(m);
        return result;
    }

    /**
     * Delete a message using its messageHash. Returns true if deleted.
     * Also removes matching entries from messageHashes, messageIds and recipients if present.
     */
    public static boolean deleteMessageByHash(String hash) {
        // remove from sent
        for (int i = 0; i < sentMessages.size(); i++) {
            if (sentMessages.get(i).messageHash.equals(hash)) {
                Message removed = sentMessages.remove(i);
                messageHashes.remove(hash);
                messageIds.remove(removed.messageId);
                recipients.remove(removed.recipient);
                return true;
            }
        }
        // remove from stored
        for (int i = 0; i < storedMessages.size(); i++) {
            if (storedMessages.get(i).messageHash.equals(hash)) {
                Message removed = storedMessages.remove(i);
                messageHashes.remove(hash);
                messageIds.remove(removed.messageId);
                recipients.remove(removed.recipient);
                return true;
            }
        }
        // remove from disregarded
        for (int i = 0; i < disregardedMessages.size(); i++) {
            if (disregardedMessages.get(i).messageHash.equals(hash)) {
                Message removed = disregardedMessages.remove(i);
                messageHashes.remove(hash);
                messageIds.remove(removed.messageId);
                recipients.remove(removed.recipient);
                return true;
            }
        }
        return false;
    }

    /**
     * Produce a detailed report of all sent messages (string).
     */
    public static String displayFullReportOfSentMessages() {
        if (sentMessages.isEmpty()) return "No sent messages yet.";
        StringBuilder sb = new StringBuilder();
        sb.append("=== Sent Messages Report ===\n\n");
        for (Message m : sentMessages) {
            sb.append("Message ID: ").append(m.messageId).append("\n");
            sb.append("Recipient: ").append(m.recipient).append("\n");
            sb.append("Message: ").append(m.messageText).append("\n");
            sb.append("Hash: ").append(m.messageHash).append("\n");
            sb.append("--------------------------------\n");
        }
        return sb.toString();
    }

    // ===== Getters =====
    public static int getMessageCount() { return numMessagesSent; }
    public String getMessageId() { return messageId; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHashValue() { return messageHash; }
    public String getFlag() { return flag; }
}
