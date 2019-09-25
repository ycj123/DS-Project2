package com.friday430.server;

import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException {
//        SocketServer socketServer = new SocketServer();
        ServerController manager = new ServerController(4444);
        ServerController client = new ServerController(5555);
        manager.start();
        client.start();
    }
}
