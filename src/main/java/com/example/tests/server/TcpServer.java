package com.example.tests.server;

import com.example.tests.server.controller.ClientController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {
    private final ServerSocket serverSocket;

    public TcpServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public void start() {
        System.out.println("Сервер запущен.");
        try {
            while (!serverSocket.isClosed()) {
                System.out.println("Сервер ожидает подключения...");
                Socket clientSocket = serverSocket.accept();
                startThreadForClient(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
            System.out.println("Сервер остановлен.");
        }
    }

    private void startThreadForClient(Socket clientSocket) {
        ClientController client = new ClientController(clientSocket);
        Thread clientThread = new Thread(client);
        clientThread.start();
    }

    private void close() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        TcpServer tcpServer = new TcpServer(5555);
        tcpServer.start();
    }
}
