package com.friday430.remote;

import javafx.scene.image.Image;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface IRemoteBoard extends Remote {

    void setChat(ArrayList<String[]> chat);

    List<String[]> getChatWithNumber(int number);

    ArrayList<String[]> getChat();

    ArrayList<HashMap<String, Double>> getCanvas_objects();

    void updateCanvas_object(HashMap<String, Double> new_object);

    ArrayList<HashMap<String, Double>> getCanvas_object(int start_index);

    String getBoard_id();

    String getRMI_key();

    void clear_object();

    int get_object_length();
}
