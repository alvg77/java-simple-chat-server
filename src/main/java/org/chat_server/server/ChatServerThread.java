package org.chat_server.server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

public class ChatServerThread implements Runnable {
    private Socket socket;
    private Set<ChatServerThread> connectionsThreadList;
    private DataOutputStream outputStream;
    private BufferedReader bufferedReader;
    ChatServerThread(Socket socket, Set<ChatServerThread> connectionsThreadList) {
        this.socket = socket;
        this.connectionsThreadList = connectionsThreadList;
        try {
            this.outputStream = new DataOutputStream(socket.getOutputStream());
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    private void sendMessage(String data) throws IOException {
        outputStream.writeBytes(data);
    }

    private void close() {
        try {
            socket.close();
            outputStream.close();
            bufferedReader.close();
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    @Override
    public void run() {
        Timestamp timestamp = new Timestamp((new Date()).getTime());
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (socket.isConnected() && !socket.isClosed()) {
                String data = bufferedReader.readLine();
                for (var ct : connectionsThreadList) {
                    ct.sendMessage("[" + timestamp + "] " + data + '\n');
                }
            }
        } catch (IOException ioe) {
            if(ioe instanceof SocketException) {
                System.out.println("[" + timestamp + "] " + socket.getInetAddress().toString() + ":" + socket.getPort() + " has lost connection to the server");
            } else {
                throw new RuntimeException(ioe);
            }
        } finally {
            close();
            synchronized (connectionsThreadList) {
                connectionsThreadList.remove(this);
            }
        }
    }
}
