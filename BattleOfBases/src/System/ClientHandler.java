package System;

import ModelViewController.Controller;

import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private Player player;
    private Controller controller;

    public ClientHandler(Socket socket, Player player) throws IOException {
        this.socket = socket;
        this.player = player;

        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        controller = new Controller(player.getVillage());
    }

    @Override
    public void run() {
        try {
            send("Welcome Player " + player.getPlayerID());

            String input;
            String response;

            while (true) {
                input = receive();
                response = controller.handleCommand(input);
                send(response);
            }

        } catch (IOException e) {
            System.out.println("Player " + player.getPlayerID() + " disconnected.");
        } finally {
            cleanup();
        }
    }

    private void send(String msg) throws IOException {
        byte[] data = msg.getBytes();
        out.writeInt(data.length);
        out.write(data);
        out.flush();
    }

    private String receive() throws IOException {
        int length = in.readInt();
        byte[] data = new byte[length];
        in.readFully(data);
        return new String(data);
    }

    private void cleanup() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Error closing socket.");
        }
    }
}