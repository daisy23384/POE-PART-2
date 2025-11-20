/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.logandregchat;

import javax.swing.JOptionPane;
import java.util.List;

public class Logandregchat {

    public static void main(String[] args) {
        Registration registration = new Registration();
        Login login = new Login();

        String firstName = "";
        String lastName = "";
        String username = "";
        String password = "";
        String phone = "";
        boolean loggedIn = false;

        JOptionPane.showMessageDialog(null, " WELCOME TO QUICKCHAT ");

        // Registration (same as before)
        firstName = JOptionPane.showInputDialog("Enter your first name:");
        lastName = JOptionPane.showInputDialog("Enter your last name:");

        username = JOptionPane.showInputDialog("Create a username (must contain '_' and ≤ 5 chars):");
        while (!registration.checkUserName(username)) {
            username = JOptionPane.showInputDialog("❌ Invalid username! Must contain '_' and ≤ 5 chars.\nTry again:");
        }

        password = JOptionPane.showInputDialog("Create a password (min 8 chars, must include uppercase, number, and special char):");
        while (!registration.checkPasswordComplexity(password)) {
            password = JOptionPane.showInputDialog("❌ Weak password! Must have:\n- 8+ chars\n- Uppercase\n- Number\n- Special symbol\nTry again:");
        }

        phone = JOptionPane.showInputDialog("Enter your phone number (start with + and ≤ 12 digits):");
        while (!registration.checkCellPhoneNumber(phone)) {
            phone = JOptionPane.showInputDialog("❌ Invalid phone number! Must start with '+' and ≤ 12 digits.\nTry again:");
        }

        JOptionPane.showMessageDialog(null,
                "✅ Registration successful!\n\nUsername: " + username +
                "\nPhone: " + phone +
                "\n\nYou can now log in.");

        // Login
        int attempts = 3;
        while (attempts > 0 && !loggedIn) {
            String inputUsername = JOptionPane.showInputDialog("Enter your username to log in:");
            String inputPassword = JOptionPane.showInputDialog("Enter your password:");

            loggedIn = login.loginUser(inputUsername, inputPassword, username, password);

            if (loggedIn) {
                JOptionPane.showMessageDialog(null, login.returnLoginStatus(true, firstName, lastName));
            } else {
                attempts--;
                if (attempts > 0) {
                    JOptionPane.showMessageDialog(null, "❌ Incorrect credentials! Attempts left: " + attempts);
                } else {
                    JOptionPane.showMessageDialog(null, "🚫 Too many failed attempts. Exiting...");
                    System.exit(0);
                }
            }
        }

        // Main QuickChat menu (extended for Part 3)
        boolean exit = false;

        JOptionPane.showMessageDialog(null, "💬 Welcome to QUICKCHAT!");

        while (!exit) {
            String menu = JOptionPane.showInputDialog("""
                ==== QUICKCHAT MENU ====
               1. Send Message
               2. Show Recently Sent Message (Coming Soon)
               3. Load Test Data (auto-populate arrays)
               4. Reports & Searches Menu
               5. Quit

                Enter your choice:
            """);

            if (menu == null) break;

            switch (menu) {
                case "1": // SEND MESSAGE
                    boolean messageLoop = true;

                    while (messageLoop) {
                        String recipient = JOptionPane.showInputDialog("Enter recipient number (must start with '+'):");
                        String messageText = JOptionPane.showInputDialog("Enter your message (max 250 characters):");

                        Message message = new Message(recipient, messageText);
                        String sendStatus = message.sendMessage();
                        JOptionPane.showMessageDialog(null, sendStatus);

                        if (sendStatus.equals("Message successfully sent!")) {
                            // message already added to sentMessages by sendMessage()
                            JOptionPane.showMessageDialog(null,
                                    "📨 Message Details:\n" +
                                    "Message ID: " + message.getMessageId() + "\n" +
                                    "Recipient: " + message.getRecipient() + "\n" +
                                    "Hash: " + message.getMessageHashValue() + "\n\n" +
                                    "Total Messages Sent: " + Message.getMessageCount()
                            );
                        }

                        String next = JOptionPane.showInputDialog("""
                            What would you like to do next?
                            1. Send another message
                            2. Disregard message
                            3. Store message to send later
                        """);

                        if (next == null) {
                            messageLoop = false;
                        } else if (next.equals("1")) {
                            // loop again -> will create new Message in next iteration
                            messageLoop = true;
                        } else if (next.equals("2")) {
                            // disregard: create a Message object representing the disregarded message and add it
                            Message disregarded = new Message(recipient, messageText);
                            disregarded.disregardMessage();
                            JOptionPane.showMessageDialog(null, "🗑 Message disregarded.");
                            messageLoop = false;
                        } else if (next.equals("3")) {
                            Message stored = new Message(recipient, messageText);
                            stored.storeMessage();
                            stored.storeMessageToFile(); // also write to file (no JSON)
                            JOptionPane.showMessageDialog(null, "💾 Message stored for later sending.");
                            messageLoop = false;
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid choice! Returning to main menu.");
                            messageLoop = false;
                        }
                    }
                    break;

                case "2":
                    JOptionPane.showMessageDialog(null, "🕓 This feature is coming soon!");
                    break;

                case "3":
                    // Populate test data
                    Message.populateTestData();
                    JOptionPane.showMessageDialog(null, "Test data loaded into arrays.");
                    break;

                case "4":
                    // Reports & Search menu for Part 3
                    boolean reportExit = false;
                    while (!reportExit) {
                        String r = JOptionPane.showInputDialog("""
                            Part 3 - Reports & Searches
                            1. Display all sent messages
                            2. Display longest message (all arrays)
                            3. Search by Message ID
                            4. Search by Recipient
                            5. Delete message by Hash
                            6. Display full sent messages report
                            7. Back to main menu
                        """);
                        if (r == null) break;
                        switch (r) {
                            case "1":
                                StringBuilder sb1 = new StringBuilder();
                                for (Message m : Message.sentMessages) {
                                    sb1.append("To: ").append(m.getRecipient())
                                       .append(" | Message: ").append(m.getMessageText())
                                       .append("\n");
                                }
                                JOptionPane.showMessageDialog(null, sb1.length() == 0 ? "No sent messages." : sb1.toString());
                                break;
                            case "2":
                                String longest = Message.getLongestMessageAcrossAll();
                                JOptionPane.showMessageDialog(null, longest.isEmpty() ? "No messages yet." : longest);
                                break;
                            case "3":
                                String id = JOptionPane.showInputDialog("Enter Message ID to search for:");
                                if (id != null) {
                                    Message found = Message.searchByMessageId(id);
                                    if (found != null) {
                                        JOptionPane.showMessageDialog(null, "Found:\nTo: " + found.getRecipient() + "\nMessage: " + found.getMessageText());
                                    } else {
                                        JOptionPane.showMessageDialog(null, "No message found with that ID.");
                                    }
                                }
                                break;
                            case "4":
                                String rec = JOptionPane.showInputDialog("Enter recipient to search for:");
                                if (rec != null) {
                                    List<Message> results = Message.searchByRecipient(rec);
                                    if (results.isEmpty()) {
                                        JOptionPane.showMessageDialog(null, "No messages for that recipient.");
                                    } else {
                                        StringBuilder sb = new StringBuilder();
                                        for (Message mm : results) {
                                            sb.append("ID: ").append(mm.getMessageId()).append(" | ").append(mm.getMessageText()).append("\n");
                                        }
                                        JOptionPane.showMessageDialog(null, sb.toString());
                                    }
                                }
                                break;
                            case "5":
                                String hash = JOptionPane.showInputDialog("Enter message hash to delete:");
                                if (hash != null) {
                                    boolean deleted = Message.deleteMessageByHash(hash);
                                    JOptionPane.showMessageDialog(null, deleted ? "Message deleted." : "Message not found.");
                                }
                                break;
                            case "6":
                                JOptionPane.showMessageDialog(null, Message.displayFullReportOfSentMessages());
                                break;
                            case "7":
                                reportExit = true;
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Invalid choice.");
                        }
                    }
                    break;

                case "5":
                    exit = true;
                    JOptionPane.showMessageDialog(null, "👋 Thank you for using QuickChat!\nGoodbye!");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "❌ Invalid choice! Please select 1–5.");
                    break;
            }
        } // end main loop

        System.exit(0);
    }
}
