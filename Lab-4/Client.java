import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class Client {

    public static void main(String[] args) {
        String serverAddress = "10.70.27.186"; 
        int serverPort = 9090;

        try {
            // Create a client socket and connect to the server
            Socket socket = new Socket(serverAddress, serverPort);
            System.out.println("Connected to server at " + serverAddress + ":" + serverPort);

            // Set socket options on the client side
            socket.setKeepAlive(true);             // Enable SO_KEEPALIVE
            socket.setSoLinger(true, 30);          // Enable SO_LINGER with a 30-second timeout
            socket.setSendBufferSize(8192);        // Set send buffer size
            socket.setReceiveBufferSize(8192);     // Set receive buffer size
            socket.setTcpNoDelay(true);            // Disable Nagle's algorithm

            // Print the values of the options
            System.out.println("SO_KEEPALIVE enabled: " + socket.getKeepAlive());
            System.out.println("SO_LINGER enabled with timeout: " + socket.getSoLinger());
            System.out.println("Send buffer size: " + socket.getSendBufferSize());
            System.out.println("Receive buffer size: " + socket.getReceiveBufferSize());
            System.out.println("TCP_NODELAY enabled: " + socket.getTcpNoDelay());

            // Create input and output streams to communicate with the server
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Send a message to the server
            String message = "Hello, Server!";
            System.out.println("Sending to server: " + message);
            out.println(message);

            socket.close();
            System.out.println("Connection closed.");

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}