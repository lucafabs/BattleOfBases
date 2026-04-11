package System;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8080);

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            Scanner scanner = new Scanner(System.in);
            System.out.println(receive(in));

            while (true) {
                String input = scanner.nextLine();
                send(out, input);

                String response = receive(in);
                System.out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void send(DataOutputStream out, String msg) throws IOException {
        byte[] data = msg.getBytes();
        out.writeInt(data.length);
        out.write(data);
        out.flush();
    }

    private static String receive(DataInputStream in) throws IOException {
        int length = in.readInt();
        byte[] data = new byte[length];
        in.readFully(data);
        return new String(data);
    }
}
