package com.friday430.server;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerManagerService {


    private static HashMap<String, ArrayList<String>> serverManageDict;
    private ServerManagerDao serverManagerDao = null;

    // class 得有构造方法！！！！！！！！！！！！！！
    public ServerManagerService(){
        serverManagerDao = ServerManagerDao.getInstance();
        this.loadServerManangerDict();
    }

    public void loadServerManangerDict(){
        try {
            serverManageDict = (HashMap<String, ArrayList<String>>)serverManagerDao.readServerManager();
        }catch(Exception e){
            serverManageDict = new HashMap<String, ArrayList<String>>();
        }
    }

    // 表示方法是否正常运行别用String 用boolean
    public boolean saveServerManagerDict(){
        try {
            serverManagerDao.writeServerManager();
        }catch (Exception e){
            return false;
        }
        return true;
    }


    public boolean isBoard_id(String board_id){
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
