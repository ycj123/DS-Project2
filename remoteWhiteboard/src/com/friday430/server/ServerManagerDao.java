package com.friday430.server;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerManagerDao {
    private static HashMap<String, ArrayList<String>> serverManageDict;
    private static ServerManagerDao instance = null;

    private ServerManagerDao() {
    }

    ;
    private static String fileName;

    public static synchronized ServerManagerDao getInstance() {
        if (instance == null) {
            instance = new ServerManagerDao();
        }
        return instance;
    }

    public void readServerManager() throws IOException {
        File f = new File("manager.csv");
        if (!f.exists()){

            f.createNewFile();
            FileOutputStream fos = new FileOutputStream("manager.csv");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(serverManageDict);

            fos.close();
            oos.close();
        }
        try{

            FileInputStream fis = new FileInputStream("manager.csv");
            ObjectInputStream ois = new ObjectInputStream(fis);
            serverManageDict = (HashMap<String, ArrayList<String>>) ois.readObject();

            fis.close();
            ois.close();
        } catch(
                IOException e)

        {
            System.out.println("Error initializing stream");
        } catch(
                ClassNotFoundException e)

        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void writeServerManager() {
        try {
            FileOutputStream fos = new FileOutputStream("manager.csv");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(serverManageDict);

            fos.close();
            oos.close();

        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
    }


}