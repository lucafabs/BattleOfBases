package System;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Server {
    private ServerSocket serverSocket;
    private ExecutorService pool = Executors.newFixedThreadPool(10);
    private int playerCount = 1;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started on port: " + port);
    }

    public void start() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected");

                Village baseVillage = Engine.getInstance().villages.get(0);
                Village playerVillage = Engine.getInstance().generateNewVillage(baseVillage);

                Player player = new Player(playerCount++, playerVillage);

                pool.execute(new ClientHandler(clientSocket, player));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
