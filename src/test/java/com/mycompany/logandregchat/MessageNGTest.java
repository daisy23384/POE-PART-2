/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.logandregchat;

import java.util.List;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * Test class for Message
 * @author RC
 */
public class MessageNGTest {

    public MessageNGTest() {}

    /**
     * Test checkRecipientNumber()
     */
    @Test
    public void testCheckRecipientNumber() {
        System.out.println("Testing checkRecipientNumber");

        Message valid = new Message("+27831234567", "Hello");
        assertTrue(valid.checkRecipientNumber(), "Valid number should return true");

        Message noPlus = new Message("27831234567", "Hello");
        assertFalse(noPlus.checkRecipientNumber(), "Missing + must fail");

        Message tooShort = new Message("+27", "Hello");
        assertFalse(tooShort.checkRecipientNumber(), "Too short number must fail");

        Message tooLong = new Message("+2783123456789", "Hello");
        assertFalse(tooLong.checkRecipientNumber(), "Too long number must fail");
    }

    /**
     * Test checkMessageLength()
     */
    @Test
    public void testCheckMessageLength() {
        System.out.println("Testing checkMessageLength");

        Message shortMsg = new Message("+27831234567", "Short message");
        assertTrue(shortMsg.checkMessageLength(), "Valid message length should pass");

        String longText = "a".repeat(260);
        Message longMsg = new Message("+27831234567", longText);
        assertFalse(longMsg.checkMessageLength(), "Over 250 chars should fail");

        Message nullMsg = new Message("+27831234567", null);
        assertFalse(nullMsg.checkMessageLength(), "Null message should fail");
    }

    /**
     * Test createMessageHash()
     */
    @Test
    public void testCreateMessageHash() {
        System.out.println("Testing createMessageHash");

        Message m = new Message("+27831234567", "Do you think thanks");
        String hash = m.createMessageHash();

        assertTrue(hash.startsWith("DO:"), "Prefix should be first 2 letters in uppercase");
        assertTrue(hash.contains("DO_THANKS"), "Should contain FIRST_LAST words");

        Message empty = new Message("+27831234567", "");
        assertEquals(empty.createMessageHash(), "", "Empty message returns empty hash");

        Message nullMsg = new Message("+27831234567", null);
        assertEquals(nullMsg.createMessageHash(), "", "Null message returns empty hash");
    }

    /**
     * Test sendMessage()
     */
    @Test
    public void testSendMessage() {
        System.out.println("Testing sendMessage");

        Message.sentMessages.clear();
        Message.messageHashes.clear();
        Message.messageIds.clear();
        Message.recipients.clear();

        Message valid = new Message("+27831234567", "Hello!");
        assertEquals(valid.sendMessage(), "Message successfully sent!");

        Message badRecipient = new Message("27831234567", "Hello");
        assertEquals(badRecipient.sendMessage(), "Invalid recipient number format.");

        Message longMsg = new Message("+27831234567", "a".repeat(260));
        assertEquals(longMsg.sendMessage(), "Please enter a message of less than 250 characters.");
    }

    /**
     * Test disregardMessage()
     */
    @Test
    public void testDisregardMessage() {
        System.out.println("Testing disregardMessage");

        Message.disregardedMessages.clear();
        Message.messageHashes.clear();

        Message m = new Message("+27831234567", "Ignore this");
        m.disregardMessage();

        assertEquals(m.getFlag(), "Disregarded");
        assertEquals(Message.disregardedMessages.size(), 1);
        assertTrue(Message.messageHashes.contains(m.getMessageHashValue()));
    }

    /**
     * Test storeMessage()
     */
    @Test
    public void testStoreMessage() {
        System.out.println("Testing storeMessage");

        Message.storedMessages.clear();
        Message.messageHashes.clear();

        Message m = new Message("+27831234567", "Store this");
        m.storeMessage();

        assertEquals(m.getFlag(), "Stored");
        assertEquals(Message.storedMessages.size(), 1);
        assertTrue(Message.messageHashes.contains(m.getMessageHashValue()));
    }

    /**
     * Test populateTestData()
     */
    @Test
    public void testPopulateTestData() {
        System.out.println("Testing populateTestData");

        Message.populateTestData();

        assertEquals(Message.sentMessages.size(), 2);
        assertEquals(Message.storedMessages.size(), 2);
        assertEquals(Message.disregardedMessages.size(), 1);
        assertEquals(Message.messageHashes.size(), 5);
        assertEquals(Message.recipients.size(), 5);
    }

    /**
     * Test searchByMessageId()
     */
    @Test
    public void testSearchByMessageId() {
        System.out.println("Testing searchByMessageId");

        Message.populateTestData();

        String id = Message.sentMessages.get(0).getMessageId();
        Message found = Message.searchByMessageId(id);

        assertNotNull(found, "Message must be found");
        assertEquals(found.getMessageId(), id);
    }

    /**
     * Test searchByRecipient()
     */
    @Test
    public void testSearchByRecipient() {
        System.out.println("Testing searchByRecipient");

        Message.populateTestData();

        String recipient = Message.storedMessages.get(0).getRecipient();
        List<Message> result = Message.searchByRecipient(recipient);

        assertFalse(result.isEmpty(), "Should find messages");
        assertEquals(result.get(0).getRecipient(), recipient);
    }

    /**
     * Test deleteMessageByHash()
     */
    @Test
    public void testDeleteMessageByHash() {
        System.out.println("Testing deleteMessageByHash");

        Message.populateTestData();

        String hash = Message.sentMessages.get(0).getMessageHashValue();

        boolean deleted = Message.deleteMessageByHash(hash);
        assertTrue(deleted, "Delete must return true");
        assertFalse(Message.messageHashes.contains(hash));
    }

    /**
     * Test getLongestMessageAcrossAll()
     */
    @Test
    public void testGetLongestMessageAcrossAll() {
        System.out.println("Testing getLongestMessageAcrossAll");

        Message.populateTestData();

        String longest = Message.getLongestMessageAcrossAll();

        assertTrue(longest.contains("late"), "Longest message should be the long stored one");
    }

    /**
     * Test displayFullReportOfSentMessages()
     */
    @Test
    public void testDisplayFullReportOfSentMessages() {
        System.out.println("Testing displayFullReportOfSentMessages");

        Message.populateTestData();

        String report = Message.displayFullReportOfSentMessages();

        assertTrue(report.contains("Sent Messages Report"));
        assertTrue(report.contains("Recipient"));
        assertTrue(report.contains("Message ID"));
    }
}
