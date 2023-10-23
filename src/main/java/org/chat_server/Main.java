package org.chat_server;

import org.chat_server.server.ChatServer;

import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer(9090);
        Logger.getGlobal().info("Server started on port 9090");
        chatServer.handleConnections();

    }
}