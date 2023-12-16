package com.example.tests.client;

import com.example.tests.server.controller.protocol.TcpRequest;
import com.example.tests.server.controller.protocol.TcpResponse;

import java.io.*;
import java.net.Socket;

public class TcpClient {
    private static TcpClient instance;

    private String serverIPAddress;
    private int serverPort;

    private Socket serverSocket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    private TcpClient() {

    }

    public static TcpClient getInstance() {
        if (instance == null) {
            instance = new TcpClient();
        }
        return instance;
    }

    public void initialize(String serverIPAddress, int serverPort) {
        this.serverIPAddress = serverIPAddress;
        this.serverPort = serverPort;
    }

    public void connect() throws IOException {
        serverSocket = new Socket(serverIPAddress, serverPort);
        configureInputAndOutputStreams(serverSocket);
    }

    private void configureInputAndOutputStreams(Socket serverSocket) throws IOException {
        OutputStream outputStream = serverSocket.getOutputStream();
        output = new ObjectOutputStream(outputStream);
        InputStream inputStream = serverSocket.getInputStream();
        input = new ObjectInputStream(inputStream);
    }

    public TcpResponse sendRequest(TcpRequest request) throws IOException, ClassNotFoundException {
        output.writeObject(request);
        return (TcpResponse) input.readObject();
    }

    public void disconnect() throws IOException {
        input.close();
        output.close();
        serverSocket.close();
    }
}
