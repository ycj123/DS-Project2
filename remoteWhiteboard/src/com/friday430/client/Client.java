//package com.friday430.client;
//
//import java.io.IOException;
//import java.util.Scanner;
//
//public class Client {
//
////    public static void main(String[] args) throws IOException, InterruptedException {
////
////        System.out.println("Join as a manager/client:");
////        Scanner sc = new Scanner(System.in);
////        String insert = sc.next();
////        System.out.println(insert);
////        //用户面板输入来执行条件体
////        if (insert.equals("client") ){
////            ClientController cc = new ClientController();
////        }else if (insert.equals("manager")){
////            ManagerController mc = new ManagerController();
////        }else{
////            System.out.println("please choose between  'manager' and 'client'");
////        }
//
//
//    public static void main(String[] args){
//
//        if (args.length == 0){
//            ManagerController mc1 = new ManagerController("default_board", "127.0.0.1", 4444);
//            ManagerController mc2 = new ManagerController("default_board", "127.0.0.1", 3758);
//
//        }
//        if (args.length == 2) {
//            if (args[0].equals("client")) {
//                ClientController cc = new ClientController(args[1], "127.0.0.1", "5555");
//            } else if (args[0].equals("manager")) {
//                ManagerController mc1 = new ManagerController(args[1], "127.0.0.1", 4444);
//                ManagerController mc2 = new ManagerController(args[1], "127.0.0.1", 3758);
//            } else {
//                System.exit(1);
//            }
//        }
//        if (args.length == 4) {
//            if (args[0].equals("client")) {
//                ClientController cc = new ClientController(args[1], args[2], args[3]);
//            } else if (args[0].equals("manager")) {
//                ManagerController mc1 = new ManagerController(args[1], args[2], 4444);
//                ManagerController mc2 = new ManagerController(args[1], args[2], 3758);
//            } else {
//                System.exit(1);
//            }
//        }
//    }
//
//}
