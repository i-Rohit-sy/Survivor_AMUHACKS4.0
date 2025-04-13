# Chat Application

A simple Java-based chat application that allows multiple clients to connect to a server and exchange messages in real-time.

## Features

- Real-time messaging between multiple clients
- Custom nickname support
- Simple command system
- Multi-threaded server architecture
- Easy-to-use client interface

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Basic understanding of command-line operations

## Project Structure

```
src/
├── Server.java      # Server implementation
├── Client.java      # Client implementation
└── ...             # Other supporting files
```

## How to Run

### Starting the Server

1. Open a terminal/command prompt
2. Navigate to the project directory
3. Compile the server:
   ```bash
   javac src/Server.java
   ```
4. Run the server:
   ```bash
   java -cp src Server
   ```
   The server will start on port 9999.

### Starting a Client

1. Open a new terminal/command prompt
2. Navigate to the project directory
3. Compile the client:
   ```bash
   javac src/Client.java
   ```
4. Run the client:
   ```bash
   java -cp src Client
   ```

## Usage

### Client Commands

- `/nick <new_name>` - Change your nickname
- `/quit` - Disconnect from the chat server

### Basic Usage

1. When you start the client, you'll be prompted to enter your name
2. After entering your name, you can start sending messages
3. All connected clients will receive your messages
4. Use the commands above to change your nickname or quit

## Technical Details

- The server uses a thread pool to handle multiple client connections
- Each client connection is handled in a separate thread
- The application uses Java's Socket programming for network communication
- Messages are broadcast to all connected clients

## Notes

- The server runs on localhost (127.0.0.1) port 9999
- Make sure to start the server before connecting any clients
- The server will continue running until manually stopped
- Clients can connect and disconnect at any time

## Contributing

Feel free to submit issues and enhancement requests!
