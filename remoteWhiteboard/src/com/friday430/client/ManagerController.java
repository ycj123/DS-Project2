package com.friday430.client;

import java.io.*;
import java.net.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class ManagerController {

    private static int hostPORT = 4444;
    public static String host_ip = "localhost";
    private static String sendData;
    private static String board_name = "";
    private static String manager_keychain;

//    private ClientView clientView;


    public ManagerController(String input_board_name, String server_ip, String server_port)  {
//
//        private ClientView clientView =
        //需在视窗里生成board_name
        host_ip = server_ip;
        hostPORT = Integer.parseInt(server_port);
        board_name = input_board_name;
        manager_keychain = initManagerKeychain();
        Thread1 m1 = new Thread1("client");
        m1.start();
        System.out.println("waiting for users");
        Thread2 m2 = new Thread2("server",manager_keychain, board_name);
        m2.start();
    }

    private static String initManagerKeychain(){
        //对每一个manager生成一个manager_keychian
        int random = (int)(Math.random() * 500000 + 1);
        int salt = (int)(Math.random() * 5000 + 1);
        return Integer.toString(random + salt);
    }

    //请求创建管理员面板
    public static void ManagertoServerRequest(String board_name, int toclientPORT,String manager_keychain){

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
            System.out.println(input.readLine());
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
}

//负责client通信
class Thread1 extends Thread {
    private static String name1;
    public  int toclientPORT = 3758;
    private String key;

    public Thread1(String name) {
        this.name1 = name;
    }

    public void run() {
        try {
            ServerSocket clientSocket=new ServerSocket(toclientPORT);
            Socket client = clientSocket.accept();
            System.out.println("waiting for clients' requests!");


            InputStreamReader in = new InputStreamReader(client.getInputStream(), "UTF-8");
            BufferedReader input = new BufferedReader(in);



            String requests = input.readLine();
            while (requests !=null){
                key =ManagerController.handleClientRequest(requests);
                OutputStreamWriter out = new OutputStreamWriter(client.getOutputStream(), "UTF-8");
                BufferedWriter output = new BufferedWriter(out);
                output.write(key);
                output.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

//负责和服务器的线程
class Thread2 extends Thread {
    private String name2;
    private ServerSocket clientSocket = null;
    private String keychain;
    private String board_name;
    private static String host_ip = "localhost";
    private static int hostPORT = 5555;
    private static int toclientPORT = 3758;



    public Thread2(String name,String manager_keychain, String board_name) {
        this.keychain = manager_keychain;
        this.name2 = name;
        this.board_name = board_name;
    }

    public void run() {
        //视窗调用MessagetoServerRequest()函数
        ManagerController.ManagertoServerRequest(board_name,toclientPORT,keychain);        //处理与server端的交互

    }
}
