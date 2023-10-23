package org.chat_server.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class ChatServer {
    private Set<ChatServerThread> connectionThreads;
    private ServerSocket serverSocket;

    public ChatServer(int portNum) {
        this.connectionThreads = new HashSet<>();
        try {
            this.serverSocket = new ServerSocket(portNum);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    public void handleConnections() {
        while(true) {
            Socket socket;
            try {
                socket = serverSocket.accept();
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }

            Logger.getGlobal().info(socket.getInetAddress().toString() + ":" + socket.getPort() + " has connected to the server");

            ChatServerThread thread = new ChatServerThread(socket, connectionThreads);

            synchronized (connectionThreads) {
                connectionThreads.add(thread);
            }

            new Thread(thread).start();
        }
    }
}
