package com.friday430.server;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerManagerService {


    private static HashMap<String, ArrayList<String>> serverManageDict = new HashMap<String, ArrayList<String>>();
    private ServerManagerDao serverManagerDao;

    // class 得有构造方法！！！！！！！！！！！！！！
    public ServerManagerService(){
        serverManagerDao = ServerManagerDao.getInstance();
        this.loadServerManangerDict();
    }


    public void loadServerManangerDict(){
        try {
            serverManageDict = serverManagerDao.readServerManager();
            System.out.println("loadServerManagerDict"+serverManageDict.keySet());
            System.out.println("Read hashmap successfully");
        }catch(Exception e){
            serverManageDict = new HashMap<String, ArrayList<String>>();
            System.out.println("Create a hashmap successfully");
        }
    }

    // 表示方法是否正常运行别用String 用boolean
    public boolean saveServerManagerDict(){
        try {
            serverManagerDao.writeServerManager(serverManageDict);//传参数
            System.out.println("The hashmap has been saved！");
        }catch (Exception e){
            return false;
        }
        return true;
    }


    public boolean isBoard_id(String board_id){
//        System.out.println("isBoard test!");
//        System.out.println("isBoard test board_id:"+board_id);
//
//        System.out.println("isBoard_return:"+serverManageDict.containsKey(board_id));
        return serverManageDict.containsKey(board_id);
    }

    public boolean addManager(String board_id, String manager_ip, String manager_port){
        try{
            ArrayList<String> boardList = new ArrayList<String>();
            boardList.add(manager_ip);
            boardList.add(manager_port);
            serverManageDict.put(board_id, boardList);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public ArrayList<String> getManager(String board_id){
        if(serverManageDict.containsKey(board_id)) {
            return serverManageDict.get(board_id);
        }
        return null;
    }
}
