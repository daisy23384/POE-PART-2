/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package com.mycompany.logandregchat;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Test class for Message
 * @author RC
 */
public class MessageNGTest {

    public MessageNGTest() {}

    /**
     * Test of checkRecipientNumber method, of class Message.
     */
    @Test
    public void testCheckRecipientNumber() {
        System.out.println("Testing checkRecipientNumber()");

        // ✅ Valid recipient number
        Message validMsg = new Message("+27831234567", "Hello");
        assertTrue(validMsg.checkRecipientNumber(),
                "Valid recipient number should return true");

        // 🚫 Missing '+'
        Message noPlus = new Message("27831234567", "Hello");
        assertFalse(noPlus.checkRecipientNumber(),
                "Recipient without '+' should return false");

        // 🚫 Too long
        Message tooLong = new Message("+2783123456789", "Hello");
        assertFalse(tooLong.checkRecipientNumber(),
                "Recipient number longer than 12 characters should return false");

        // 🚫 Too short
        Message tooShort = new Message("+271", "Hello");
        assertFalse(tooShort.checkRecipientNumber(),
                "Recipient number shorter than 10 characters should return false");
    }

    /**
     * Test of checkMessageLength method, of class Message.
     */
    @Test
    public void testCheckMessageLength() {
        System.out.println("Testing checkMessageLength()");

        // ✅ Valid message (under 250 chars)
        Message shortMsg = new Message("+27831234567", "This is a short test message.");
        assertTrue(shortMsg.checkMessageLength(),
                "Short message should return true");

        // 🚫 Message too long (over 250 chars)
        StringBuilder longText = new StringBuilder();
        for (int i = 0; i < 260; i++) {
            longText.append("a");
        }
        Message longMsg = new Message("+27831234567", longText.toString());
        assertFalse(longMsg.checkMessageLength(),
                "Message longer than 250 characters should return false");

        // 🚫 Null message
        Message nullMsg = new Message("+27831234567", null);
        assertFalse(nullMsg.checkMessageLength(),
                "Null message should return false");
    }

    /**
     * Test of createMessageHash method, of class Message.
     */
    @Test
    public void testCreateMessageHash() {
        System.out.println("Testing createMessageHash()");

        // ✅ Valid message
        Message msg = new Message("+27831234567", "Do you think thanks");
        String hash = msg.createMessageHash();

        // The hash should have structure like "DO:<id>:DO_THANKS"
        assertTrue(hash.startsWith("DO:"), "Hash should start with the message prefix (first two letters)");
        assertTrue(hash.contains(":DO_THANKS"), "Hash should contain first and last words in uppercase");

        // 🚫 Empty message
        Message emptyMsg = new Message("+27831234567", "");
        assertEquals(emptyMsg.createMessageHash(), "",
                "Empty message should return empty hash");

        // 🚫 Null message
        Message nullMsg = new Message("+27831234567", null);
        assertEquals(nullMsg.createMessageHash(), "",
                "Null message should return empty hash");
    }

    /**
     * Test of sendMessage method, of class Message.
     */
    @Test
    public void testSendMessage() {
        System.out.println("Testing sendMessage()");

        // ✅ Valid message and number
        Message valid = new Message("+27831234567", "Test");
        assertEquals(valid.sendMessage(), "Message successfully sent!");

        // 🚫 Invalid recipient number
        Message invalidRecipient = new Message("27831234567", "Test");
        assertEquals(invalidRecipient.sendMessage(), "Invalid recipient number format.");

        // 🚫 Too long message
        StringBuilder longText = new StringBuilder();
        for (int i = 0; i < 260; i++) {
            longText.append("a");
        }
        Message longMsg = new Message("+27831234567", longText.toString());
        assertEquals(longMsg.sendMessage(), "Please enter a message of less than 250 characters.");
    }
}
