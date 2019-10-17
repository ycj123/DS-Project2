package com.friday430.server;

import com.friday430.remote.IRemoteBoard;
import javafx.scene.image.Image;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RmiObject implements IRemoteBoard {
    private ArrayList<String[]> chat_history;
    private Image image;

    private ArrayList<HashMap<String, Double>> canvas_objects = new ArrayList<HashMap<String, Double>>();

    public synchronized ArrayList<HashMap<String, Double>> getCanvas_objects(){
        return this.canvas_objects;
    }

    public synchronized void updateCanvas_object(HashMap<String, Double> new_object){
        this.canvas_objects.add(new_object);
    }

    @Override
    public ArrayList<HashMap<String, Double>> getCanvas_object(int start_index) {
        List l = canvas_objects.subList(start_index, canvas_objects.size());

        return new ArrayList<HashMap<String, Double>>(l);
        //return (ArrayList<HashMap<String, Double>>)
    }


    public RmiObject() {
        this.chat_history = new ArrayList<>();
    }

    public synchronized void updateChat(String username, String words){
        this.chat_history.add(new String[]{username, words});
    }

    public List<String[]> getChatWithNumber(int number){
        int array_length = this.chat_history.size();
        return chat_history.subList(array_length - number, array_length);
    }

    @Override
    public ArrayList<String[]> getChat() {
        return this.chat_history;
    }

    public void setChat(ArrayList<String[]> chat){
        this.chat_history = chat;
    }

    public void setImage(Image image){
        this.image = image;
    }

    public Image getImage(){
        return this.image;
    }
}
