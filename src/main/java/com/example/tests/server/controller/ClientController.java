package com.example.tests.server.controller;

import com.example.tests.server.command.Command;
import com.example.tests.server.command.CommandDefiner;
import com.example.tests.server.controller.protocol.TcpRequest;
import com.example.tests.server.controller.protocol.TcpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientController implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(ClientController.class);

    private final Socket clientSocket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String clientIpAddress;
    private int clientPort;

    public ClientController(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        clientIpAddress = getClientIpAddress(clientSocket);
        clientPort = getClientPort(clientSocket);
        System.out.printf("Клиент %s: %d подключился.\n", clientIpAddress, clientPort);
        try {
            configureInputAndOutputStreams();
            processRequests();
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        } finally {
            close();
            System.out.printf("Клиент %s: %d отключился.\n", clientIpAddress, clientPort);
        }
    }

    private void configureInputAndOutputStreams() throws IOException {
        OutputStream outputStream = clientSocket.getOutputStream();
        output = new ObjectOutputStream(outputStream);
        InputStream inputStream = clientSocket.getInputStream();
        input = new ObjectInputStream(inputStream);
    }

    private void processRequests() throws IOException, ClassNotFoundException {
        try {
            do {
                TcpRequest request = (TcpRequest) input.readObject();
                String commandName = request.getCommand();
                System.out.printf("Клиент: %s: %d - %s\n", clientIpAddress, clientPort, commandName);
                Command command = CommandDefiner.define(commandName);
                TcpResponse response = command.execute(request);
                output.writeObject(response);
            }
            while (!clientSocket.isClosed());
        } catch (EOFException ignored) {

        }
    }

    private void closeInputAndOutputStreams() throws IOException {
        output.close();
        input.close();
    }

    private void close() {
        try {
            closeInputAndOutputStreams();
            clientSocket.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public String getClientIpAddress(Socket clientSocket) {
        InetSocketAddress socketAddress = (InetSocketAddress) clientSocket.getRemoteSocketAddress();
        return socketAddress.getAddress().getHostAddress();
    }

    public int getClientPort(Socket clientSocket) {
        InetSocketAddress socketAddress = (InetSocketAddress) clientSocket.getRemoteSocketAddress();
        return socketAddress.getPort();
    }

}
