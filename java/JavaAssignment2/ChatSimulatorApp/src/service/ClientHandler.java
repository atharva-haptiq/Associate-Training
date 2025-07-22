package service;

import sockets.Server;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final List<ClientHandler> clients;
    private PrintWriter out;
    private BufferedReader in;
    private String username;

    public ClientHandler(Socket socket, List<ClientHandler> clients) {
        this.socket = socket;
        this.clients = clients;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Enter your name:");
            username = in.readLine();
            System.out.println(username + " joined.");
            Server.broadcast( username + " joined the chat!", this);

            String msg;
            while ((msg = in.readLine()) != null) {
                if (msg.equalsIgnoreCase("/exit")) break;
                String fullMsg = username + ": " + msg;
                Server.broadcast(fullMsg, this);
            }
        } catch (IOException e) {
            System.err.println(" Error with client " + username);
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {}

            Server.removeClient(this);
            Server.broadcast(username + " left the chat.", this);
        }
    }

    public void sendMessage(String msg) {
        out.println(msg);
    }
}
