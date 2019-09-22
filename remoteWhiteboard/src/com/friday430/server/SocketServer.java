package com.friday430.server;

import javax.net.ServerSocketFactory;
import java.io.*;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    private static int client_port = 4444;
    private static int client_counter = 0;

    private static int manager_counter = 0;

    private ServerManagerService serverManagerService = null;
    private ServerRMIService serverRMIService = null;
    private ServerController serverController = null;

    public SocketServer(){
        ServerManagerService serverManagerService = new ServerManagerService();
        ServerRMIService serverRMIService = new ServerRMIService();
        ServerController serverController = new ServerController();
    }

    public void serverManager() throws IOException {
        ServerSocketFactory factory = ServerSocketFactory.getDefault();
        int manager_port = 5555;
        try(ServerSocket managerServer = factory.createServerSocket(manager_port)){
            System.out.println("Waiting for manager connection-");

            // wait for connection
            while(true)
            {
                Socket manager = managerServer.accept();

                manager_counter++;
                System.out.println("Manager "+manager_counter+": Applying for connection!");

                // Start a new thread for a connection
                Thread t = new Thread(() -> serveManager(manager));
                t.start();
            }
        }catch (BindException e){
            System.out.println("Address already in use");
        }
    }

    private void serveManager(Socket manager) {
        try(Socket managerSocket = manager) {
            // Input stream
            InputStreamReader in = new InputStreamReader(managerSocket.getInputStream(),"UTF-8");
            BufferedReader input = new BufferedReader(in);
            // Output Stream
            OutputStreamWriter out = new OutputStreamWriter(managerSocket.getOutputStream(),"UTF-8");
            BufferedWriter output = new BufferedWriter(out);

            String managerMassage = input.readLine();//从manager传输过来的消息；

            if(managerMassage != null){
                String[] request = managerMassage.split("#+");
                String manager_keychain = request[0];
                String board_name = request[1];

                InetAddress manager_ip_int = manager.getInetAddress(); //得到manager的IP地址
                String manager_ip = String.valueOf(manager_ip_int);
                int manager_port_int = manager.getPort();//得到manager的port
                String manager_port = String.valueOf(manager_port_int);

                String response = serverController.handleManagerReauest(manager_keychain, board_name, manager_ip, manager_port);

                output.write(response);

                output.newLine();
                output.flush();
            }
            in.close();
            out.close();
            managerSocket.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}
