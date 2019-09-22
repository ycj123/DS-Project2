package com.friday430.server;

import javax.net.ServerSocketFactory;
import java.io.*;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class SocketServer {

    private static int client_port = 4444;
    private static int client_counter = 0;

    int manager_port = 5555;
    private static int manager_counter = 0;
    
    private ServerController serverController = null;

    public SocketServer(){
        ServerManagerService serverManagerService = new ServerManagerService();
        ServerRMIService serverRMIService = new ServerRMIService();
        ServerController serverController = new ServerController();
    }

    public void serverManager() throws IOException {
        ServerSocketFactory factoryManager = ServerSocketFactory.getDefault();
        try(ServerSocket managerServer = factoryManager.createServerSocket(manager_port)){
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

    public void serverClient() throws IOException {
        ServerSocketFactory factoryClient = ServerSocketFactory.getDefault();
        try(ServerSocket clientServer = factoryClient.createServerSocket(client_port)){
            System.out.println("Waiting for client connection-");

            // wait for connection
            while(true)
            {
                Socket client = clientServer.accept();
                client_counter++;
                System.out.println("Client "+client_counter+": Applying for connection!");
                // Start a new thread for a connection
                Thread tt = new Thread(() -> serveClient(client));
                tt.start();
            }
        }catch (BindException e){
            System.out.println("Address already in use");
        }
    }

    private void serveClient(Socket client) {
        try(Socket clientSocket = client){
            // Input stream
            InputStreamReader in = new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8);
            BufferedReader input = new BufferedReader(in);
            // Output Stream
            OutputStreamWriter out = new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8);
            BufferedWriter output = new BufferedWriter(out);

            String clientMessage = input.readLine();//从client传过来的请求

            if(clientMessage != null){
                String board_name = clientMessage;
                ArrayList response_list = serverController.handleClientRequest(board_name);
                String manager_ip = (String) response_list.get(0);
                String manager_port = (String) response_list.get(1);
                String managerMessage = manager_ip + "#+" + manager_port; //传过去的字符串

                output.write(managerMessage);

                output.newLine();
                output.flush();

            }

            in.close();
            out.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
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
