package com.friday430.server;

import com.friday430.remote.IRemoteBoard;

import java.util.ArrayList;
import java.util.List;

public class RmiObject implements IRemoteBoard {
    private ArrayList<String[]> chat_history;
    //private Image

    public RmiObject() {
        this.chat_history = new ArrayList<>();
    }

    public void updateChat(String username, String words){
        this.chat_history.add(new String[]{username, words});
    }

    public List<String[]> getChatWithNumber(int number){
        int array_length = this.chat_history.size();
        return chat_history.subList(array_length - number, array_length);
    }

    public void setImage(){

    }

    public void getImage(){

    }
}
