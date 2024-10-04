import java.io.*;
import java.net.*;
import java.util.*;

public class NotificationServer {
    private static final int PORT = 9999;
    private static final Map<String, List<PrintWriter>> subscribers = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Notification Server is running on port " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Get subscription preferences
                String category = in.readLine();
                subscribeClient(category, out);

                // Listen for notifications to send to this client
                String message;
                while ((message = in.readLine()) != null) {
                    sendNotification(category, message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void subscribeClient(String category, PrintWriter writer) {
            subscribers.computeIfAbsent(category, k -> new ArrayList<>()).add(writer);
            System.out.println("Client subscribed to category: " + category);
        }

        private void sendNotification(String category, String message) {
            List<PrintWriter> clients = subscribers.get(category);
            if (clients != null) {
                for (PrintWriter writer : clients) {
                    writer.println("Notification: " + message);
                }
            }
        }
    }
}
