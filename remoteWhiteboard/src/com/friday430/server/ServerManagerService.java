package com.friday430.server;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerManagerService {


    private static HashMap<String, ArrayList<String>> serverManageDict = new HashMap<String, ArrayList<String>>();

    private ServerManagerDao serverManagerDao = ServerManagerDao.getInstance();

    public void loadServerManangerDict(){
        try {
            serverManageDict = (HashMap<String, ArrayList<String>>) serverManagerDao.readServerManager();
        }catch(Exception e){
            serverManageDict = new HashMap<String, ArrayList<String>>();
        }
    }

    public saveServerManagerDict(){

    }

    public void addManager(String board_id, String manager_ip, String manager_port){
        try{
            ArrayList<String> boardList = new ArrayList<String>();
            boardList.add(manager_ip);
            boardList.add(manager_port);
            serverManageDict.put(board_id, boardList);
        }catch (Exception e){
            serverManageDict = new HashMap<String, ArrayList<String>>();
        }
    }

    public ArrayList<String> getManager(String board_id){
            if(serverManageDict.containsKey(board_id)) {
                return serverManageDict.get(board_id);
            }
        return null;
    }
}
