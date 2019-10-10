package com.friday430.client;

import java.io.IOException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args){

        if (args.length == 0){
            ManagerController mc = new ManagerController("default_board", "127.0.0.1", "4444");
        }
        if (args.length == 2) {
            if (args[0].equals("client")) {
                ClientController cc = new ClientController(args[1], "127.0.0.1", "5555");
            } else if (args[0].equals("manager")) {
                ManagerController mc = new ManagerController(args[1], "127.0.0.1", "4444");
            } else {
                System.exit(1);
            }
        }
        if (args.length == 4) {
            if (args[0].equals("client")) {
                ClientController cc = new ClientController(args[1], args[2], args[3]);
            } else if (args[0].equals("manager")) {
                ManagerController mc = new ManagerController(args[1], args[2], args[3]);
            } else {
                System.exit(1);
            }
        }
    }
}
