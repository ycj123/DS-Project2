package com.friday430.client;

import javax.net.ServerSocketFactory;
import java.io.*;
import java.net.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class ManagerController extends Thread implements ClientControllerInterface {
    private static int toclientPORT = 37581;
    private static int hostPORT = 4444;
    public static String host_ip = "localhost";
    private static String sendData;
    private static String board_name = "";
    private static String manager_keychain;
    private String key;
    private String board_id;
    private int port;

//    private ClientView clientView;


    public ManagerController(String input_board_name, String server_ip, String port)  {
//
        host_ip = server_ip;
        this.port = Integer.parseInt(port);
        board_name = input_board_name;
        try {
            manager_keychain = initManagerKeychain();
        }catch (Exception e){
            e.printStackTrace();
        }
        this.start();
    }

    private static String initManagerKeychain() throws UnknownHostException, SocketException {
        InetAddress ip = InetAddress.getLocalHost();
        NetworkInterface network = NetworkInterface.getByInetAddress(ip);

        byte[] mac = network.getHardwareAddress();

        System.out.print("Current MAC address : ");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "" : ""));
        }
        return sb.toString();
    }

    //请求创建管理员面板
    public void ManagertoServerRequest(String board_name, int toclientPORT, String manager_keychain){

        try(Socket socket = new Socket(host_ip, hostPORT);)
        {
            // Output Stream
            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(),"UTF-8");
            BufferedWriter output = new BufferedWriter(out);
            String str = toclientPORT+"";
            InetAddress localip = socket.getInetAddress();
            String manager_ip = localip.getHostAddress();
            String sendData =manager_keychain+"###"+board_name+"###"+str+"###"+manager_ip;
            output.write(sendData);
            output.newLine();
            System.out.println("Data sent to Server--> " + sendData);
            output.flush();
            InputStreamReader in = new InputStreamReader(socket.getInputStream(), "UTF-8");
            BufferedReader input = new BufferedReader(in);
            String managerkey = input.readLine();
            String substr1 = managerkey.substring(0,5);
            if (substr1.equals("Error")){
                System.out.println(managerkey);
            }else{
                String[] message = managerkey.split("###");
                String board_id = message[0];
                String substr2 = message[1];
                this.board_id = board_id;
                System.out.println(substr2);
            }
        }
        catch (NoRouteToHostException e) {
            System.out.println("Can't assign requested address (Address not available)!");
        }
        catch (ConnectException e) {
            System.out.println("Connection refused (no server at the port)");
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
//        return null;
    }

    public static String handleClientRequest(String request){
        System.out.println(request);
        String[] data = request.split("###");
        String m_keychain = data[0];
        String name = data[1];
        if (name.equals(board_name)){
            return m_keychain;
        }else{return null;}

    }
//    public String saveboard_id(){}

    @Override
    public String getRMIKey() {
        return this.board_id + manager_keychain;
    }

    public void run() {
        if (port == toclientPORT) {
            try {
                ServerSocket clientSocket = new ServerSocket(toclientPORT);
                Socket client = clientSocket.accept();
                System.out.println("waiting for clients' requests!");


                InputStreamReader in = new InputStreamReader(client.getInputStream(), "UTF-8");
                BufferedReader input = new BufferedReader(in);


                String requests = input.readLine();
                while (requests != null) {
                    key = ManagerController.handleClientRequest(requests);
                    OutputStreamWriter out = new OutputStreamWriter(client.getOutputStream(), "UTF-8");
                    BufferedWriter output = new BufferedWriter(out);
                    output.write(key);
                    output.flush();
                    System.out.println("waiting for users");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (port == hostPORT) {
            this.ManagertoServerRequest(board_name, toclientPORT, this.manager_keychain);        //处理与server端的交互
        }

    }
}



////负责client通信
//class Thread1 extends Thread {
//    private static String name1;
//
//    private String key;
//
//    public Thread1(String name) {
//        this.name1 = name;
//    }
//
//    public void run() {
//        try {
//            ServerSocket clientSocket=new ServerSocket(toclientPORT);
//            Socket client = clientSocket.accept();
//            System.out.println("waiting for clients' requests!");
//
//
//            InputStreamReader in = new InputStreamReader(client.getInputStream(), "UTF-8");
//            BufferedReader input = new BufferedReader(in);
//
//
//
//            String requests = input.readLine();
//            while (requests !=null){
//                key =ManagerController.handleClientRequest(requests);
//                OutputStreamWriter out = new OutputStreamWriter(client.getOutputStream(), "UTF-8");
//                BufferedWriter output = new BufferedWriter(out);
//                output.write(key);
//                output.flush();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//}
//
//负责和服务器的线程
//class Thread2 extends Thread {
//    private String name2;
//    private String keychain;
//    private String board_name;
//    private static String host_ip = "localhost";
//    private static int hostPORT = 5555;
//    private static int toclientPORT = 3758;
//
//
//
//    public Thread2(String name,String manager_keychain, String board_name) {
//        this.keychain = manager_keychain;
//        this.name2 = name;
//        this.board_name = board_name;
//    }
//
//    public void run() {
//        //视窗调用MessagetoServerRequest()函数
//
//        ManagerController.ManagertoServerRequest(board_name,toclientPORT,keychain);        //处理与server端的交互
//
//    }
//}
