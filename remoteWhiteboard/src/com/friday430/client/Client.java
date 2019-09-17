package com.friday430.client;

public class Client {
    public static void main(String[] args) {
        ClientController cc = null;
        if (args[0] == "true") {
            cc = new ClientController();
        }else{
            cc = new ManagerController();
        }
    }
}
