import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static int countsClient = 0;

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(1024);
            System.out.println("Server started");

            while (true) {
                Socket socket = serverSocket.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                countsClient++;
                System.out.println("Client " + countsClient + " connected");

                new Thread(() -> {
                    try{
                        while(true) {
                            String request = reader.readLine();
                            System.out.println("Request: " + request);
                            String response = "Не принадлежит ни одной четверти";

                            String[] str = request.split(" ");
                            int pointX = Integer.parseInt(str[0]);
                            int pointY = Integer.parseInt(str[1]);

                            if (pointX > 0 && pointY > 0) {
                                response = "I четверть";
                            }
                            if (pointX < 0 && pointY > 0) {
                                response = "II четверть";
                            }
                            if (pointX < 0 && pointY < 0) {
                                response = "III четверть";
                            }
                            if (pointX > 0 && pointY < 0) {
                                response = "IV четверть";
                            }

                            writer.write(response);
                            writer.newLine();
                            writer.flush();
                            System.out.println("Response: " + response);
                        }
                    } catch(IOException e) {
                        try {
                            countsClient--;
                            System.out.println("Client disconnected");
                            socket.close();
                            reader.close();
                            writer.close();
                        } catch (IOException ex) { }
                    }

                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
