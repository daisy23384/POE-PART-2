package com.mycompany.logandregchat;

import javax.swing.JOptionPane;

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

        // === REGISTRATION ===
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

        // === LOGIN ===
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

        // === QUICKCHAT MENU AFTER LOGIN ===
        boolean exit = false;

        JOptionPane.showMessageDialog(null, "💬 Welcome to QUICKCHAT!");

        while (!exit) {
            String menu = JOptionPane.showInputDialog("""
                ==== QUICKCHAT MENU ====
               1. Send Message
               2. Show Recently Sent Message
               3. Quit
                
                Enter your choice:
            """);

            if (menu == null) break; // user closed window

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
                            1️. Send another message
                            2️. Disregard message
                            3️. Store message to send later
                        """);

                        if (next == null || next.equals("2")) {
                            JOptionPane.showMessageDialog(null, "🗑 Message disregarded.");
                            messageLoop = false;
                        } else if (next.equals("3")) {
                            JOptionPane.showMessageDialog(null, "💾 Message stored for later sending (Coming Soon).");
                            messageLoop = false;
                        } else if (!next.equals("1")) {
                            JOptionPane.showMessageDialog(null, "Invalid choice! Returning to main menu.");
                            messageLoop = false;
                        }
                    }
                    break;

                case "2": // COMING SOON
                    JOptionPane.showMessageDialog(null, "🕓 This feature is coming soon!");
                    break;

                case "3": // QUIT
                    JOptionPane.showMessageDialog(null, "👋 Thank you for using QuickChat!\nGoodbye!");
                    exit = true;
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "❌ Invalid choice! Please select 1, 2, or 3.");
            }
        }

        System.exit(0);
    }
}
