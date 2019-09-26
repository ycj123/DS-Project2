package com.friday430.client;

        import java.io.IOException;
        import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("Join as a manager/client:");
        Scanner sc = new Scanner(System.in);
        String insert = sc.next();
        System.out.println(insert);
        //用户面板输入来执行条件体
        if (insert.equals("client") ){
            ClientController cc = new ClientController();
        }else if (insert.equals("manager")){
            ManagerController mc = new ManagerController();
        }else{
            System.out.println("please choose between  'manager' and 'client'");
        }

    }
}
