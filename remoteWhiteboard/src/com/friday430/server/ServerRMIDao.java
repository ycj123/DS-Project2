package com.friday430.server;

import com.friday430.remote.IRemoteBoard;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ServerRMIDao {
    private static ServerRMIDao serverRMIDao = null;

    private ServerRMIDao() {
    }

    public static synchronized ServerRMIDao getInstance() {
        if (serverRMIDao == null) {
            serverRMIDao = new ServerRMIDao();
            return serverRMIDao;
        }
        return null;
    }

    public IRemoteBoard readBoard(String board_id) {
        IRemoteBoard board = null;
        try {
            FileInputStream fi = new FileInputStream(new File(board_id + ".board"));  //fileName-board_id的对应
            ObjectInputStream oi = new ObjectInputStream(fi);
            board = (IRemoteBoard)oi.readObject();
            oi.close();
            fi.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return board;

    }


    //write a board according to board_id
    public void writeBoard(IRemoteBoard board){
        try {
            String board_id = board.getBoard_id();
            FileOutputStream f = new FileOutputStream(new File(board_id + ".board"));  //create a file
            ObjectOutputStream o = new ObjectOutputStream(f);
            // Write objects to file
            o.writeObject(board);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
    }

    public ArrayList<IRemoteBoard> readAll() {
        ArrayList<IRemoteBoard> list = new ArrayList<>();
        File folder = new File("./");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                if (listOfFiles[i].getName().endsWith(".board")) {
                    String board_id = listOfFiles[i].getName().split(".board")[0];
                    list.add(this.readBoard(board_id));
                }
            }
        }
        return list;
    }

    public void writeAll(List<IRemoteBoard> board_list){
        for (IRemoteBoard board : board_list){
            this.writeBoard(board);
        }
    }

}
