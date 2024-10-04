import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {

    public static void main(String[] args) {
        int port = 9090; // Changed port to 8080 to match your expected output

        try {
            // Create a server socket on port 8080
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server socket created on port " + port);

            while (true) {
                // Accept incoming client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Set and print the socket options
                clientSocket.setKeepAlive(true);           // Enable SO_KEEPALIVE
                clientSocket.setSoLinger(true, 30);        // Enable SO_LINGER with a 30-second timeout
                clientSocket.setSendBufferSize(8192);      // Set send buffer size
                clientSocket.setReceiveBufferSize(8192);   // Set receive buffer size
                clientSocket.setTcpNoDelay(true);          // Disable Nagle's algorithm

                // Print the Send and Receive buffer sizes
                System.out.println("Send Buffer Size: " + clientSocket.getSendBufferSize());
                System.out.println("Receive Buffer Size: " + clientSocket.getReceiveBufferSize());

                // Use BufferedReader and PrintWriter for I/O
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

                // Read message from the client
                String receivedMessage = in.readLine();
                System.out.println("Received from client: " + receivedMessage);
                System.out.println();
                // Close the client socket
                clientSocket.close();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
