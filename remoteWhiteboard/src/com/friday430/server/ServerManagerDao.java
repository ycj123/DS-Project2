package com.friday430.server;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

//qjx改了一部分，反正最后功能可以实现了

public class ServerManagerDao {
    private static ServerManagerDao instance = null;

    private ServerManagerDao() {

    }

    public static synchronized ServerManagerDao getInstance() {
        if (instance == null) {
            instance = new ServerManagerDao();
        }
        return instance;
    }

    public HashMap<String, ArrayList<String>> readServerManager() throws IOException {
        HashMap<String, ArrayList<String>> serverManageDict = new HashMap<String, ArrayList<String>>();  //return hashmap
        File f = new File("manager.txt");
        if (!f.exists()){

            f.createNewFile();
            FileOutputStream fos = new FileOutputStream("manager.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(serverManageDict);

            fos.close();
            oos.close();
        }
        try{

            FileInputStream fis = new FileInputStream("manager.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            serverManageDict = (HashMap<String, ArrayList<String>>) ois.readObject();

            fis.close();
            ois.close();
            return serverManageDict;
        } catch(IOException e) {
            System.out.println("Error initializing stream");
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        return serverManageDict;
    }

    public void writeServerManager(HashMap<String, ArrayList<String>> serverManageDict) {
        try {
            FileOutputStream fos = new FileOutputStream("manager.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(serverManageDict);
            System.out.println("serverManageDictKey:"+serverManageDict.keySet());

            fos.close();
            oos.close();

        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
    }


}