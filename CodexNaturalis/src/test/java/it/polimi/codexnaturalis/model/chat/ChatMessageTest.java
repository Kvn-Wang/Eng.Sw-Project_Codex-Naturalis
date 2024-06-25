package it.polimi.codexnaturalis.model.chat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChatMessageTest {
    private ChatMessage chatMessage;

    @BeforeEach
    public void setUp() {
        chatMessage = new ChatMessage("Alice,Bob", "Hello, this is a test message.");
    }

    @Test
    public void testGetRecipients() {

    }

    @Test
    public void testGetTime() {

    }

    @Test
    public void testGetMessage() {

    }
}