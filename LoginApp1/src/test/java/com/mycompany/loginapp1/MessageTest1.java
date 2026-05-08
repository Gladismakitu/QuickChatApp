/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginapp1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest1 {

    private Message1 message;

    @BeforeEach
    void setUp() throws Exception {
        // Reset static fields before each test to ensure test isolation
        Field totalMessagesField = Message1.class.getDeclaredField("totalMessages");
        totalMessagesField.setAccessible(true);
        totalMessagesField.set(null, 0);

        Field sentMessagesField = Message1.class.getDeclaredField("sentMessages");
        sentMessagesField.setAccessible(true);
        sentMessagesField.set(null, new ArrayList<>());

        message = new Message1(0, "+27831234567", "Hello World");
    }

    // ==================== checkMessageID() ====================

    @Test
    void testMessageID_IsNotNull() {
        assertNotNull(message.getMessageID(), "Message ID should not be null.");
    }

    @Test
    void testMessageID_LengthIsAtMost10() {
        assertTrue(message.checkMessageID(),
                "Message ID length should be 10 or fewer characters.");
    }

    @Test
    void testMessageID_IsNumeric() {
        String id = message.getMessageID();
        assertTrue(id.matches("\\d+"), "Message ID should consist of digits only.");
    }

    // ==================== checkRecipientCell() ====================

    @Test
    void testCheckRecipientCell_ValidNumber() {
        Message1 msg = new Message1(0, "+27831234567", "Hello World");
        assertEquals("Cell phone number successfully captured.",
                msg.checkRecipientCell());
    }

    @Test
    void testCheckRecipientCell_MissingInternationalCode() {
        Message1 msg = new Message1(0, "0831234567", "Hello World");
        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code. " +
                "Please correct the number and try again.",
                msg.checkRecipientCell());
    }

    @Test
    void testCheckRecipientCell_TooLong() {
        Message1 msg = new Message1(0, "+278312345678901", "Hello World");
        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code. " +
                "Please correct the number and try again.",
                msg.checkRecipientCell());
    }

    @Test
    void testCheckRecipientCell_EmptyString() {
        Message1 msg = new Message1(0, "", "Hello World");
        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code. " +
                "Please correct the number and try again.",
                msg.checkRecipientCell());
    }

    // ==================== checkMessageLength() ====================

    @Test
    void testCheckMessageLength_WithinLimit() {
        Message1 msg = new Message1(0, "+27831234567", "Short message.");
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    @Test
    void testCheckMessageLength_ExactlyAtLimit() {
        String exactly250 = "A".repeat(250);
        Message1 msg = new Message1(0, "+27831234567", exactly250);
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    @Test
    void testCheckMessageLength_ExceedsLimit() {
        String tooLong = "A".repeat(260);
        Message1 msg = new Message1(0, "+27831234567", tooLong);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.",
                msg.checkMessageLength());
    }

    @Test
    void testCheckMessageLength_ExceedsByOne() {
        String over = "A".repeat(251);
        Message1 msg = new Message1(0, "+27831234567", over);
        assertEquals("Message exceeds 250 characters by 1; please reduce the size.",
                msg.checkMessageLength());
    }

    // ==================== createMessageHash() ====================

    @Test
    void testMessageHash_IsUpperCase() {
        String hash = message.getMessageHash();
        assertEquals(hash.toUpperCase(), hash,
                "Message hash should be fully uppercase.");
    }

    @Test
    void testMessageHash_ContainsMessageNumber() {
        Message1 msg = new Message1(3, "+27831234567", "Hello World");
        assertTrue(msg.getMessageHash().contains(":3:"),
                "Message hash should contain the message number.");
    }

    @Test
    void testMessageHash_ContainsFirstAndLastWord() {
        Message1 msg = new Message1(0, "+27831234567", "Hello World");
        String hash = msg.getMessageHash();
        assertTrue(hash.contains("HELLO") || hash.endsWith("HELLOWORLD"),
                "Message hash should contain first and last words.");
        assertTrue(hash.contains("WORLD"),
                "Message hash should contain the last word.");
    }

    @Test
    void testMessageHash_SingleWordMessage() {
        Message1 msg = new Message1(0, "+27831234567", "Hello");
        String hash = msg.getMessageHash();
        // First and last word are the same, so they should appear concatenated
        assertTrue(hash.contains("HELLOHELLO"),
                "Single-word message hash should repeat the word twice.");
    }

    // ==================== sentMessage() ====================

    @Test
    void testSentMessage_SendChoice_ReturnsSuccessMessage() {
        String result = message.sentMessage(1);
        assertEquals("Message successfully sent.", result);
    }

    @Test
    void testSentMessage_SendChoice_IncrementsTotalMessages() {
        message.sentMessage(1);
        assertEquals(1, Message1.returnTotalMessages());
    }

    @Test
    void testSentMessage_DisregardChoice() {
        String result = message.sentMessage(2);
        assertEquals("Press 0 to delete the message.", result);
    }

    @Test
    void testSentMessage_StoreChoice() {
        String result = message.sentMessage(3);
        assertEquals("Message successfully stored.", result);
    }

    @Test
    void testSentMessage_InvalidChoice() {
        String result = message.sentMessage(99);
        assertEquals("Invalid option. Please select 1, 2, or 3.", result);
    }

    // ==================== returnTotalMessages() ====================

    @Test
    void testReturnTotalMessages_InitiallyZero() {
        assertEquals(0, Message1.returnTotalMessages(),
                "Total messages should start at 0.");
    }

    @Test
    void testReturnTotalMessages_AfterMultipleSends() {
        message.sentMessage(1);
        message.sentMessage(1);
        message.sentMessage(1);
        assertEquals(3, Message1.returnTotalMessages());
    }

    @Test
    void testReturnTotalMessages_StoredOrDiscardedNotCounted() {
        message.sentMessage(2); // disregard
        message.sentMessage(3); // store
        assertEquals(0, Message1.returnTotalMessages(),
                "Only sent messages should be counted.");
    }

    // ==================== getAllSentMessages() ====================

    @Test
    void testGetAllSentMessages_WhenNoneYet() {
        assertEquals("No messages sent yet.", Message1.getAllSentMessages());
    }

    @Test
    void testGetAllSentMessages_AfterOneSend() {
        message.sentMessage(1);
        String result = Message1.getAllSentMessages();
        assertTrue(result.contains("Message ID:"), "Result should contain Message ID.");
        assertTrue(result.contains("Recipient:"),  "Result should contain Recipient.");
        assertTrue(result.contains("Message:"),    "Result should contain Message text.");
    }

    @Test
    void testGetAllSentMessages_AfterMultipleSends() {
        Message1 msg1 = new Message1(0, "+27831234567", "First message");
        Message1 msg2 = new Message1(1, "+27831234568", "Second message");
        msg1.sentMessage(1);
        msg2.sentMessage(1);
        String result = Message1.getAllSentMessages();
        assertTrue(result.contains("--- Message 1 ---"), "Should list first message.");
        assertTrue(result.contains("--- Message 2 ---"), "Should list second message.");
    }

    // ==================== printMessages() ====================

    @Test
    void testPrintMessages_ContainsAllFields() {
        String output = message.printMessages();
        assertTrue(output.contains("Message ID:"),   "Should contain Message ID label.");
        assertTrue(output.contains("Message Hash:"), "Should contain Message Hash label.");
        assertTrue(output.contains("Recipient:"),    "Should contain Recipient label.");
        assertTrue(output.contains("Message:"),      "Should contain Message label.");
    }

    @Test
    void testPrintMessages_ContainsCorrectRecipient() {
        assertTrue(message.printMessages().contains("+27831234567"));
    }

    @Test
    void testPrintMessages_ContainsCorrectMessageText() {
        assertTrue(message.printMessages().contains("Hello World"));
    }

    // ==================== Getters ====================

    @Test
    void testGetRecipient() {
        assertEquals("+27831234567", message.getRecipient());
    }

    @Test
    void testGetMessage() {
        assertEquals("Hello World", message.getMessage());
    }

    @Test
    void testGetMessageNumber() {
        Message1 msg = new Message1(5, "+27831234567", "Test");
        assertEquals(5, msg.getMessageNumber());
    }
}
