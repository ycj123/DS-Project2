package com.friday430.server;

import java.io.*;

public class ServerRMIDao {
    private static ServerRMIDao serverRMIDao = null;

    private ServerRMIDao() {
    }

    ;

    public static synchronized ServerRMIDao getInstance() {
        if (serverRMIDao == null) {
            serverRMIDao = new ServerRMIDao();
            return serverRMIDao;
        }
        return null;
    }

    public ServerRMIDao readBoard(String board_id) {
        try {
            FileInputStream fi = new FileInputStream(new File("fileName-board_id"));  //fileName-board_id的对应
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects
            ServerRMIDao object1 = ServerRMIDao.getInstance();

            oi.close();
            fi.close();
            return object1;

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
        return null;

    }


        //write a board according to board_id
    public void writeBoard(String board_id){
        try {
            //根据board_id在哈希表中寻找fileName
            ServerRMIDao s1 = ServerRMIDao.getInstance();
            FileOutputStream f = new FileOutputStream(new File("fileName"));  //create a file
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file
            o.writeObject(s1);
            o.close();
            f.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
    }
}

//    public ServerRMIDao readAll(){
//
//    }
//    public void writeAll(){
//
//    }
//}
