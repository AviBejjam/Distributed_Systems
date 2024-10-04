import socket
import threading

def handle_client(client_socket):
    while True:
        # Receive message from the client
        message = client_socket.recv(1024).decode('utf-8')
        if not message:
            print("Client disconnected.")
            break
        print(f"Client: {message}")
        
        # Send a response back to the client
        response = input("Server: ")
        client_socket.send(response.encode('utf-8'))

    client_socket.close()

def start_server():
    # Create a socket object
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    
    # Get local machine name
    host = socket.gethostname()
    port = 8081
    
    # Bind to the port
    server_socket.bind((host, port))
    
    # Queue up to 5 requests
    server_socket.listen(5)
    print(f"Server started! Listening on {port}")
    
    while True:
        # Establish a connection
        client_socket, addr = server_socket.accept()
        print(f"Got a connection from {addr}")
        
        # Handle the client in a new thread
        client_handler = threading.Thread(target=handle_client, args=(client_socket,))
        client_handler.start()

if __name__ == "__main__":
    start_server()