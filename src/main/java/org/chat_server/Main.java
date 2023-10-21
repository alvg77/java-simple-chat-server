package org.chat_server;

import org.chat_server.server.ChatServer;

public class Main {
    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer(9090);
        chatServer.handleConnections();
    }
}