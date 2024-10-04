import socket

SERVER_HOST = 'localhost'  # Server's IP address
SERVER_PORT = 8089  # Server's port number

client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client_socket.connect((SERVER_HOST, SERVER_PORT))
print(f"Connected to server at {SERVER_HOST}:{SERVER_PORT}")

while True:
    message = input("Client: ")
    client_socket.sendall(message.encode())

    if message.lower() == 'exit':
        break
    data = client_socket.recv(1024).decode()
    print(f"Received from server: {data}")
client_socket.close()