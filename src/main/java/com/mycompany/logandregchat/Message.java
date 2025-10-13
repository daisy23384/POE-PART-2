package com.mycompany.logandregchat;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Handles message creation, validation, hashing, and storage.
 * Updated to integrate with QuickChat main program.
 * 
 * @author RC
 */
public class Message {
    private static int messageCount = 0;                    // Total sent messages
    private static List<Message> storedMessages = new ArrayList<>();  // Stored messages for later sending

    private String messageId;
    private String recipient;
    private String messageText;
    private String messageHash;
    private boolean sent; // true if message was successfully sent

    // Constructor
    public Message(String recipient, String messageText) {
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageId = generateMessageId();
        this.messageHash = createMessageHash();
        this.sent = false;
    }

    // === Generate Random Message ID ===
    private String generateMessageId() {
        Random rand = new Random();
        long id = 1000000000L + (long)(rand.nextDouble() * 8999999999L);
        return String.valueOf(id);
    }

    // === Check recipient format ===
    public boolean checkRecipientNumber() {
        return recipient != null && recipient.startsWith("+") &&
               recipient.length() >= 10 && recipient.length() <= 12;
    }

    // === Check message text length ===
    public boolean checkMessageLength() {
        return messageText != null && messageText.length() <= 250;
    }

    // === Create hash based on message contents ===
    public String createMessageHash() {
        if (messageText == null || messageText.isEmpty()) return "";
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();
        String prefix = messageText.length() >= 2 ?
                messageText.substring(0, 2).toUpperCase() :
                messageText.toUpperCase();
        return prefix + ":" + messageId + ":" + firstWord + "_" + lastWord;
    }

    // === Send message ===
    public String sendMessage() {
        if (!checkRecipientNumber())
            return "❌ Invalid recipient number format.";
        if (!checkMessageLength())
            return "❌ Please enter a message under 250 characters.";

        this.sent = true;
        messageCount++;
        return "✅ Message successfully sent!";
    }

    // === Store message for later sending ===
    public void storeMessage() {
        storedMessages.add(this);
    }

    // === Display all stored messages ===
    public static String showStoredMessages() {
        if (storedMessages.isEmpty()) {
            return "📭 No stored messages yet.";
        }

        StringBuilder sb = new StringBuilder("💾 Stored Messages:\n\n");
        for (Message msg : storedMessages) {
            sb.append("To: ").append(msg.getRecipient())
              .append("\nMessage: ").append(msg.getMessageText())
              .append("\nHash: ").append(msg.getMessageHashValue())
              .append("\nStatus: ").append(msg.sent ? "Sent ✅" : "Pending ⏳")
              .append("\n\n");
        }
        return sb.toString();
    }

    // === Getters ===
    public static int getMessageCount() { return messageCount; }
    public String getMessageId() { return messageId; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHashValue() { return messageHash; }

    // === Run from UI (optional manual use) ===
    public static void sendMessageViaUI() {
        String recipient = JOptionPane.showInputDialog("Enter recipient number (must start with '+'):");
        String messageText = JOptionPane.showInputDialog("Enter your message (max 250 characters):");

        Message message = new Message(recipient, messageText);
        String status = message.sendMessage();

        JOptionPane.showMessageDialog(null, status);

        if (status.equals("✅ Message successfully sent!")) {
            JOptionPane.showMessageDialog(null,
                "📨 Message Details:\n" +
                "Message ID: " + message.getMessageId() + "\n" +
                "Recipient: " + message.getRecipient() + "\n" +
                "Hash: " + message.getMessageHashValue() + "\n" +
                "Total Messages Sent: " + getMessageCount()
            );
        }
    }
}
