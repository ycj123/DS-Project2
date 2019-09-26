//package com.friday430.client;
//
//
//import java.io.*;
//import java.net.Socket;
//import java.net.UnknownHostException;
//import java.util.Scanner;
//
//public class Client_test{
//
//    // IP and port
//
//    public static void main(String[] args) {
//        //
//        try(Socket socket = new Socket("localhost", 5555);)
//        {
//
//            // Input stream
//            InputStreamReader in = new InputStreamReader(socket.getInputStream(),"UTF-8");
//            BufferedReader input = new BufferedReader(in);
//            // Output Stream
//            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(),"UTF-8");
//            BufferedWriter output = new BufferedWriter(out);
////            String sendData ="manager_keychain###board_name###manager_port###manager_ip"; //manager 测试数据
//            String sendData =""; //client测试数据(发送boardname)
//
//            output.write(sendData);
//            output.newLine();
//            System.out.println("Data sent to Server--> " + sendData);
//            output.flush();
//
//            System.out.println("input"+input.readLine());
//            String message = input.readLine();
//
//            System.out.println("Message from server to client："+message);
//
//
//        }
//        catch (UnknownHostException e)
//        {
//            e.printStackTrace();
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//
//    }
//
//}

//
//package com.friday430.client;
////
////import java.io.*;
////import java.net.*;
////import java.io.IOException;
////import java.io.InputStream;
////import java.io.OutputStream;
////
////public class Client {
////    public static void main(String[] args) throws IOException {
////        ClientController cc = null;
////        ManagerController mc = null;
////              mc = new ManagerController();
////        }
////    }