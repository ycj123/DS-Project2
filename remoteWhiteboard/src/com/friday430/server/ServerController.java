package com.friday430.server;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class ServerController extends Thread {


    private ServerManagerService serverManagerService = new ServerManagerService();
    private ServerRMIService serverRMIService = new ServerRMIService();


    public ArrayList handleClientRequest(String board_name) {
        String board_id = this.generateBoardID(board_name);
        if(serverManagerService.isBoard_id(board_id)) {
            return serverManagerService.getManager(board_id);
        }
        else {
            return null;
        }
    }

    public String handleManagerReauest(String manager_keychain, String board_name, Socket clientManager) {
        try {
            String board_id = this.generateBoardID(board_name);
            if (serverManagerService.isBoard_id(board_id))
                return ("The board has been created!");
            else {
                String[] manager = this.getManagerIPPort(clientManager).split("#+");
                serverManagerService.addManager(board_id, manager[0], manager[1]);
                serverRMIService.createNewBoard(board_id, manager_keychain);
                return "Create a board successfully!";
            }
        } catch (Exception e) {
            return ("Create a board failed!!");
        }
    }

    private String getManagerIPPort(Socket client) {
        InetAddress addr = client.getInetAddress();
        int port_client = client.getPort();
        return addr + "#+" + port_client;
    }

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
        return "Fail";
    }


//    public static void main(String[] args) {
//        ServerController serverController = new ServerController();
//        String a = serverController.generateBoardID("345");
//        System.out.println(a);
//    }
}
