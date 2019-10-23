package com.friday430.client;

import javax.net.ServerSocketFactory;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

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

    private static String initManagerKeychain() throws IOException {
        //InetAddress ip = InetAddress.getLocalHost();
        //NetworkInterface network = NetworkInterface.getByInetAddress(ip);
        //byte[] mac = network.getHardwareAddress();
        //System.out.print("Current MAC address : ");
        //StringBuilder sb = new StringBuilder();
        //for (int i = 0; i < mac.length; i++) {
        //    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "" : ""));
        //}
        //return sb.toString();
        String key = "";
        try {
            FileInputStream fis = new FileInputStream("manager_key");
            DataInputStream dis = new DataInputStream(fis);
            key = dis.readUTF();
        }catch (FileNotFoundException e){
            int random = (int)(Math.random() * 500000 + 1);
            int salt = (int)(Math.random() * 5000 + 1);
            key = Integer.toString(random + salt);
            File f = new File("manager_key");
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream("manager_key");
            DataOutputStream dos = new DataOutputStream(fos);
            dos.writeUTF(key);
        }
        return key;
    }

    //请求创建管理员面板
    public void ManagertoServerRequest(String board_name, int toclientPORT, String manager_keychain){

        try(Socket socket = new Socket(host_ip, hostPORT);)
        {
            // Output Stream
            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(),"UTF-8");
            BufferedWriter output = new BufferedWriter(out);
            String str = toclientPORT+"";
            InetAddress localip = socket.getLocalAddress();
            String manager_ip = localip.getHostAddress();
            String sendData =manager_keychain+"###"+board_name+"###"+str+"###"+manager_ip;
            output.write(sendData);
            output.newLine();
            System.out.println("Data sent to Server--> " + sendData);
            output.flush();
            InputStreamReader in = new InputStreamReader(socket.getInputStream(), "UTF-8");
            BufferedReader input = new BufferedReader(in);
            String managerkey = input.readLine();
            System.out.println(managerkey);
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
        String username = request.split("###")[0];
        String board_name = request.split("###")[1];

        JPanel panel = new JPanel();
        panel.add(new JLabel(username + " wants to join board " + board_name));
        int result = JOptionPane.showConfirmDialog(null, panel, "Client asking for permission", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION){
            return manager_keychain + "\n";
        }else{
            return null;
        }
    }
//    public String saveboard_id(){}

    @Override
    public String getRMIKey() {
        return board_id + manager_keychain;
    }

    @Override
    public String getUserName() {
        return "Manager";
    }

    public void run() {
        if (port == toclientPORT) {
            try {
                ServerSocket clientSocket = new ServerSocket(toclientPORT);
                Socket client = clientSocket.accept();
                System.out.println("waiting for clients' requests!");


                InputStreamReader in = new InputStreamReader(client.getInputStream(), "UTF-8");
                BufferedReader input = new BufferedReader(in);

                while(true) {
                    Thread.sleep(100);
                    String requests = input.readLine();
                    if (requests != null) {
                        key = ManagerController.handleClientRequest(requests);
                        OutputStreamWriter out = new OutputStreamWriter(client.getOutputStream(), "UTF-8");
                        BufferedWriter output = new BufferedWriter(out);
                        output.write(key);
                        output.flush();
                        System.out.println("waiting for users");
                    }
                }

            } catch (IOException | InterruptedException e) {
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
