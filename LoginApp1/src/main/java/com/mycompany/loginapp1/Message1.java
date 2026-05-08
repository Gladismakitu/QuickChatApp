/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginapp1;

import java.util.ArrayList;
import java.util.Random;

public class Message1 {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String message;
    private String messageHash;

    private static int totalMessages = 0;
    private static ArrayList<String> sentMessages = new ArrayList<>();

    public Message1(int messageNumber, String recipient, String message) {
        this.messageID     = generateMessageID();
        this.messageNumber = messageNumber;
        this.recipient     = recipient;
        this.message       = message;
        this.messageHash   = createMessageHash();
    }

    private String generateMessageID() {
        Random rand = new Random();
        long id = (long)(rand.nextDouble() * 9000000000L) + 1000000000L;
        return String.valueOf(id);
    }

    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        if (recipient.matches("^\\+[0-9]{1,3}[0-9]{1,10}$") && recipient.length() <= 13) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. " +
               "Please correct the number and try again.";
    }

    public String createMessageHash() {
        String firstTwo  = messageID.substring(0, 2);
        String[] words   = message.trim().split("\\s+");
        String firstWord = words[0].replaceAll("[^a-zA-Z0-9]", "");
        String lastWord  = words[words.length - 1].replaceAll("[^a-zA-Z0-9]", "");
        return (firstTwo + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    public String checkMessageLength() {
        if (message.length() <= 250) {
            return "Message ready to send.";
        }
        int over = message.length() - 250;
        return "Message exceeds 250 characters by " + over + "; please reduce the size.";
    }

    public String sentMessage(int choice) {
        if (choice == 1) {
            totalMessages++;
            String details = printMessages();
            sentMessages.add(details);
            return "Message successfully sent.";
        } else if (choice == 2) {
            return "Press 0 to delete the message.";
        } else if (choice == 3) {
            storeMessage();
            return "Message successfully stored.";
        }
        return "Invalid option. Please select 1, 2, or 3.";
    }

    public String printMessages() {
        return "Message ID: "   + messageID   + "\n" +
               "Message Hash: " + messageHash + "\n" +
               "Recipient: "    + recipient   + "\n" +
               "Message: "      + message;
    }

    public static int returnTotalMessages() {
        return totalMessages;
    }

    public static String getAllSentMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages sent yet.";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sentMessages.size(); i++) {
            sb.append("--- Message ").append(i + 1).append(" ---\n");
            sb.append(sentMessages.get(i)).append("\n\n");
        }
        return sb.toString();
    }

    public void storeMessage() {
        try {
            java.io.FileWriter file = new java.io.FileWriter("storedMessages.json", true);
            file.write("{\n");
            file.write("  \"messageID\": \""   + messageID   + "\",\n");
            file.write("  \"messageHash\": \"" + messageHash + "\",\n");
            file.write("  \"recipient\": \""   + recipient   + "\",\n");
            file.write("  \"message\": \""     + message     + "\"\n");
            file.write("},\n");
            file.flush();
            file.close();
        } catch (java.io.IOException e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }

    public String getMessageID()   { return messageID; }
    public String getRecipient()   { return recipient; }
    public String getMessage()     { return message; }
    public String getMessageHash() { return messageHash; }
    public int getMessageNumber()  { return messageNumber; }
}