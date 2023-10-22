package org.chat_server;

import org.chat_server.server.ChatServer;

public class Main {
    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer(9090);
        try {
            chatServer.handleConnections();
        } catch (Exception e) {
            System.err.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }
    }
}