package com.friday430.client;

import com.friday430.remote.IRemoteBoard;
import com.friday430.server.RmiObject;

import java.util.ArrayList;

public class cv_main {
    public static void main(String[] args) {
        //RmiObject rmio = new RmiObject();
        //ArrayList<String[]> l = new ArrayList<String[]>();
        //l.add(new String[]{"a", "b"});
        //rmio.setChat(l);

        //ClientView cv = new ClientView();
        ClientView.launch();
    }
}
