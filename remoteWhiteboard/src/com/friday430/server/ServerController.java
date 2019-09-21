package com.friday430.server;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class ServerController extends Thread {

    private ServerManagerService serverManagerService = null;
    private ServerRMIService serverRMIService = null;

    // class 得有构造方法！！！！！！！！！！！！！！
    public ServerController(){
        serverManagerService = new ServerManagerService();
        serverRMIService = new ServerRMIService();
    }


    public ArrayList handleClientRequest(String board_name) {
        String board_id = this.generateBoardID(board_name);
        if(serverManagerService.isBoard_id(board_id)) {
            return serverManagerService.getManager(board_id);
        }
        else {
            return null;
        }
    }

    // 尽量不要把复杂对象传入方法：socket
    // 尝试在调用前做
    // String[] manager = this.getManagerIPPort(clientManager).split("#+");
    // 再把其结果传入方法
    public String handleManagerReauest(String manager_keychain, String board_name, String manager_ip, String manager_port) {
        try {
            String board_id = this.generateBoardID(board_name);
            if (board_id == null){
                // TODO: handle board id convert exception
                // 这个方法同样最好别用字符串返回 （return null） or （throws exception）
                // 如果必须用字符串返回，记得调用方法时检查返回的字符串是否为错误信息
                return ("Error! Try again!");
            }
            if(board_id.length() == 32) {
                if (serverManagerService.isBoard_id(board_id))
                    return ("The board had been created.");
                else {
                    //String[] manager = this.getManagerIPPort(clientManager).split("#+");
                    serverManagerService.addManager(board_id, manager_ip, manager_port);
                    serverRMIService.createNewBoard(board_id, manager_keychain);
                    return "Create board '" + board_name + "' successfully.";
                }
            }
        } catch (Exception e) {
            return ("Create board '" + board_name + "' failed.");
        }
        return ("Create board '" + board_name + "' failed.");
    }

    private String getManagerIPPort(Socket client) {
        InetAddress addr = client.getInetAddress();
        int port_client = client.getPort();
        return addr + "#+" + port_client;
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


//    public static void main(String[] args) {
//        ServerController serverController = new ServerController();
//        String a = serverController.generateBoardID("345");
//        System.out.println(a);
//    }
}
