/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license


            // ==================== PART 1: LOGIN ====================
            Login1 user = registration.getRegisteredUser();

            System.out.println("\n=== Login ===\n");

            System.out.print("Enter your username: ");
            String enteredUsername = scanner.nextLine();

            System.out.print("Enter your password: ");
            String enteredPassword = scanner.nextLine();

            String loginStatus = user.returnLoginStatus(enteredUsername, enteredPassword);
            System.out.println("\n" + loginStatus);

            if (!user.loginUser(enteredUsername, enteredPassword)) {
                System.out.println("\nLogin failed. Please restart the application.");
                return; */

package com.mycompany.loginapp1;

import java.util.Scanner;

public class LoginApp1 {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            displayInstructions(scanner);

            // ==================== PART 1: REGISTRATION ====================
            System.out.println("\n=== Registration ===\n");

            System.out.print("Enter your username: ");
            String username = scanner.nextLine();

            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            System.out.print("Enter your cell phone number (e.g., +27831234567): ");
            String cellPhoneNumber = scanner.nextLine();

            System.out.print("Enter your first name: ");
            String firstName = scanner.nextLine();

            System.out.print("Enter your last name: ");
            String lastName = scanner.nextLine();

            Registration1 registration = new Registration1(username, password, cellPhoneNumber, firstName, lastName);

            String registrationResult = registration.registerUser();
            System.out.println("\n" + registrationResult);

            if (!registrationResult.startsWith("Welcome")) {
                System.out.println("\nRegistration failed. Please restart the application.");
                return;
            }

            // ==================== PART 1: LOGIN ====================
            System.out.println("\n=== Login ===\n");

            System.out.print("Enter your username: ");
            String enteredUsername = scanner.nextLine();

            System.out.print("Enter your password: ");
            String enteredPassword = scanner.nextLine();

            Login1 login = new Login1(enteredUsername, enteredPassword, registration);

            String loginStatus = login.returnLoginStatus();
            System.out.println("\n" + loginStatus);

            if (!login.loginUser()) {
                System.out.println("\nLogin failed. Please restart the application.");
                return;
            }

            // ==================== PART 2: QUICKCHAT ====================
            System.out.println("\nWelcome to QuickChat.");

            System.out.print("\nHow many messages would you like to send? ");
            int numMessages = Integer.parseInt(scanner.nextLine());

            boolean running = true;

            while (running) {
                System.out.println("\n=== QuickChat Menu ===");
                System.out.println("1) Send Messages");
                System.out.println("2) Show recently sent messages");
                System.out.println("3) Quit");
                System.out.print("Choose an option: ");

                int menuChoice = Integer.parseInt(scanner.nextLine());

                if (menuChoice == 1) {

                    int messageNumber = 0;

                    for (int i = 0; i < numMessages; i++) {
                        System.out.println("\n--- Message " + (i + 1) + " of " + numMessages + " ---");

                        System.out.print("Enter recipient cell number: ");
                        String recipient = scanner.nextLine();

                        System.out.print("Enter your message (max 250 characters): ");
                        String messageText = scanner.nextLine();

                        Message1 msg = new Message1(messageNumber, recipient, messageText);

                        String lengthCheck = msg.checkMessageLength();
                        if (!lengthCheck.equals("Message ready to send.")) {
                            System.out.println(lengthCheck);
                            System.out.println("Please enter a message of less than 250 characters.");
                            i--;
                            continue;
                        }

                        System.out.println(msg.checkRecipientCell());
                        System.out.println("Message ID generated: " + msg.getMessageID());
                        System.out.println("Message Hash: " + msg.getMessageHash());

                        System.out.println("\nWhat would you like to do with this message?");
                        System.out.println("1) Send Message");
                        System.out.println("2) Disregard Message");
                        System.out.println("3) Store Message to send later");
                        System.out.print("Choose an option: ");

                        int sendChoice = Integer.parseInt(scanner.nextLine());
                        String result = msg.sentMessage(sendChoice);
                        System.out.println(result);

                        if (sendChoice == 1) {
                            System.out.println("\n" + msg.printMessages());
                        }

                        messageNumber++;
                    }

                    System.out.println("\nTotal messages sent: " + Message1.returnTotalMessages());

                } else if (menuChoice == 2) {
                    System.out.println("\n" + Message1.getAllSentMessages());

                } else if (menuChoice == 3) {
                    System.out.println("\nGoodbye!");
                    running = false;

                } else {
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
                }
            }
        }
    }

    private static void displayInstructions(Scanner scanner) {
        System.out.println("=== Registration and Login Feature ===\n");
        System.out.println("Please read through the entire task before you start:\n");
        System.out.println("Task 1: Create an account.");
        System.out.println("   - Username: must contain an underscore (_) and be no more than 5 characters.");
        System.out.println("   - Password: must be at least 8 characters, contain a capital letter, a number, and a special character.");
        System.out.println("   - Cell phone number: must include international code (e.g. +27831234567).\n");
        System.out.println("Task 2: Login using the same username and password.\n");
        System.out.println("Task 3: Use QuickChat to send messages.\n");
        System.out.println("============================================\n");
        System.out.print("Press Enter to begin...");
        scanner.nextLine();
    }
}