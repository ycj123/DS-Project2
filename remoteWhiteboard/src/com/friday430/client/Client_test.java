package com.friday430.client;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

//package com.friday430.client;
//
//
//import java.io.*;
//import java.net.Socket;
//import java.net.UnknownHostException;
//import java.util.Scanner;
//
public class Client_test {

    // IP and port

    public static void main(String[] args) throws SocketException, UnknownHostException {
        //
        InetAddress ip = InetAddress.getLocalHost();
        NetworkInterface network = NetworkInterface.getByInetAddress(ip);

        byte[] mac = network.getHardwareAddress();

        System.out.print("Current MAC address : ");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "" : ""));
        }
        System.out.println(sb.toString());

    }
}
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