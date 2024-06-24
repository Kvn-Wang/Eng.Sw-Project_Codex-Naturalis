package it.polimi.codexnaturalis.model.chat;

import it.polimi.codexnaturalis.model.player.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Chat manager for logs
 */
public class ChatManager {
    private HashMap log;

    /**
     * Instantiates a new Chat manager.
     */
    public ChatManager(){
        log = new HashMap<String,String>();
    }

    /**
     * writes a comment in the log
     *
     * @param recipient the recipient
     * @param Sender    the name of the sender
     * @param msg       the message
     */
    public void writeComment(String recipient, String Sender,String msg){

    }

    /**
     * writes a comment in the log
     *
     * @param Sender the name of the sender
     * @param msg    the message
     */
    public void writeComment(String Sender,String msg){

    }

    private void filterMessage(Player receiver){

    }
}
