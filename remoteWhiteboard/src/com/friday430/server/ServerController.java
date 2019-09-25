package com.friday430.server;


import javax.net.ServerSocketFactory;
import java.io.*;
import java.math.BigInteger;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

public class ServerController extends Thread {


    private static int manager_port = 4444;
    private static int client_port= 5555;
    private static int manager_counter = 0;
    private static int client_counter = 0;


    private ServerManagerService serverManagerService = null;
    private ServerRMIService serverRMIService = null;

    // class 得有构造方法！！！！！！！！！！！！！！
//    public ServerController() throws IOException {
//        serverManagerService = new ServerManagerService();
////        this.serverSocket(4444);
//        this.serverSocket(5555);
////        serverRMIService = new ServerRMIService();
//    }
//
    private int port;
    public ServerController(int port){
        this.port = port;
    }


    public ArrayList handleClientRequest(String board_name) {
        String board_id = this.generateBoardID(board_name);
        try{
            if(serverManagerService.isBoard_id(board_id)) {
                return serverManagerService.getManager(board_id);
            }
            else {
                return null;
            }
        }catch (NullPointerException e){
            e.printStackTrace();

        }

        return null;
    }

    // 尽量不要把复杂对象传入方法：socket
    // 尝试在调用前做
    // String[] manager = this.getManagerIPPort(clientManager).split("#+");
    // 再把其结果传入方法
    public String handleManagerReauest(String manager_keychain, String board_name, String manager_ip, String manager_port) {
        try {
            String board_id = this.generateBoardID(board_name);
            if (board_id == null){
                // 这个方法同样最好别用字符串返回 （return null） or （throws exception）
                // 如果必须用字符串返回，记得调用方法时检查返回的字符串是否为错误信息
                return ("Error! Try again!");
            }
            if(board_id.length() == 32) {
                if (serverManagerService.isBoard_id(board_id))
                    return ("The board had been created.");
                else {
//                    String[] manager = this.getManagerIPPort(clientManager).split("#+");
                    serverManagerService.addManager(board_id, manager_ip, manager_port);
                    serverRMIService.createNewBoard(board_id, manager_keychain);
                    return "Create board '" + board_name + "' successfully.";
                }
            }
            serverManagerService.saveServerManagerDict();
        } catch (Exception e) {
            return ("Create board '" + board_name + "' failed.!!!");
        }
        return ("Create board '" + board_name + "' failed.");
    }



    // 返回错误值时尽量不要用字符串
    // 如果使用这个方法出错了 37行 String board_id = this.generateBoardID(board_name);
    // 程序会把错误信息当成board_id执行
    private String generateBoardID(String board_name) {
        byte[] secreteByte = null;
        try {
            secreteByte = MessageDigest.getInstance("md5").digest(board_name.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (secreteByte != null) {
            String board_id = new BigInteger(1, secreteByte).toString(16);
            for (int i = 0; i < 32 - board_id.length(); i++) {
                board_id = "0" + board_id;
            }
            return board_id;
        }
        return null;
    }

//    public void serverManager(int manager_port) throws IOException {
//        ServerSocketFactory factoryManager = ServerSocketFactory.getDefault();
//        try(ServerSocket managerServer = factoryManager.createServerSocket(manager_port)){
//            System.out.println("Waiting for manager connection-");
//            // wait for connection
//            while(true)
//            {
//                Socket manager = managerServer.accept();
//                manager_counter++;
//                System.out.println("Manager "+manager_counter+": Applying for connection!");
//                // Start a new thread for a connection
//                Thread t = new Thread(() -> serveManager(manager));
//                t.start();
//            }
//        }catch (BindException e){
//            System.out.println("Address already in use");
//        }
//    }



    public void run() {
        if (port == manager_port) {
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
                    Thread t = new Thread(() -> this.serveManager(manager));
                    t.start();
                }
            }catch (BindException e){
                System.out.println("Address already in use");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(port == client_port) {
            ServerSocketFactory factoryClient = ServerSocketFactory.getDefault();
            try (ServerSocket clientServer = factoryClient.createServerSocket(client_port)) {
                System.out.println("Waiting for client connection-");

                // wait for connection
                while (true) {
                    Socket client = clientServer.accept();
                    client_counter++;
                    System.out.println("Client " + client_counter + ": Applying for connection!");
                    // Start a new thread for a connection
                    Thread tt = new Thread(() -> this.serveClient(client));
//                    System.out.println("sent");
                    tt.start();
                }
            } catch (BindException e) {
                System.out.println("Address already in use");
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            System.out.println(managerMassage);
            if(managerMassage != null){
                String[] request = managerMassage.split("###");
                System.out.println(Arrays.toString(request));

                String manager_keychain = request[0];
                String board_name = request[1];
                System.out.println("manager_keychain:"+request[0]);
                System.out.println("board_name:"+request[1]);

                InetAddress manager_ip_int = manager.getInetAddress(); //得到manager的IP地址
                String manager_ip = String.valueOf(manager_ip_int);
                System.out.println("manager_ip:"+manager_ip);
                int manager_port_int = manager.getPort();//得到manager的port
                String manager_port = String.valueOf(manager_port_int);
                System.out.println("manager_port:"+manager_port);

                String response = this.handleManagerReauest(manager_keychain, board_name, manager_ip, manager_port);
                System.out.println(response);

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

    private void serveClient(Socket client) {
        try(Socket clientSocket = client){
            // Input stream
            InputStreamReader in = new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8);
            BufferedReader input = new BufferedReader(in);
            // Output Stream
            OutputStreamWriter out = new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8);
            BufferedWriter output = new BufferedWriter(out);
            System.out.println("clientMessage");
            String clientMessage = input.readLine();//从client传过来的请求
            System.out.println(clientMessage);

            if(clientMessage != null){
                String board_name = clientMessage;
                ArrayList response_list = this.handleClientRequest(board_name);
                String manager_ip = (String) response_list.get(0);
                String manager_port = (String) response_list.get(1);
                String managerMessage = manager_ip + "###" + manager_port; //传过去的字符串

                output.write(managerMessage);
                System.out.println("sent");

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

//    public static void main(String[] args) {
//        ServerController serverController = new ServerController();
//        String a = serverController.generateBoardID("345");
//        System.out.println(a);
//    }
}
