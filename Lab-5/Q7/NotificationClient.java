import java.io.*;
import java.net.*;
import java.util.Scanner;

public class NotificationClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 9999;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter category to subscribe to: ");
            String category = scanner.nextLine();
            out.println(category);

            // Start a thread to listen for notifications
            new Thread(() -> {
                String notification;
                try {
                    while ((notification = in.readLine()) != null) {
                        System.out.println(notification);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Send notifications to the server
            String message;
            while (true) {
                System.out.print("Enter notification message (type 'exit' to quit): ");
                message = scanner.nextLine();
                if ("exit".equalsIgnoreCase(message)) {
                    break;
                }
                out.println(message);
                System.out.println("Sent: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
