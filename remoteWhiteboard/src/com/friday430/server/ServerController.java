package com.friday430.server;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ServerController extends Thread{



    private ServerManagerService serverManagerService = new ServerManagerService();
    private ServerRMIService serverRMIService = new ServerRMIService();



    public void handleClientRequest(String board_id){
        String managerIP ;
        String managerPort;

    }

    public String handleManagerReauest(String manager_keychain, String board_name){
        ServerController serverController = new ServerController();
        if (manager_keychain != null){
            return this.generateBoardID(board_name);
        }
        return ("Fail!");
    }

    private String generateBoardID( String board_name){
        byte[] secreteByte = null;
        try {
            secreteByte = MessageDigest.getInstance("md5").digest(board_name.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if(secreteByte != null) {
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
