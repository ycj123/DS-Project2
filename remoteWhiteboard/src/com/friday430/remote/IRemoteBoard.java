package com.friday430.remote;

import javafx.scene.image.Image;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface IRemoteBoard extends Remote {

    void setChat(ArrayList<String[]> chat) throws RemoteException;

    List<String[]> getChatWithNumber(int number) throws RemoteException;

    ArrayList<String[]> getChat() throws RemoteException;

    ArrayList<HashMap<String, Double>> getCanvas_objects() throws RemoteException;

    void updateCanvas_object(HashMap<String, Double> new_object) throws RemoteException;

    ArrayList<HashMap<String, Double>> getCanvas_object(int start_index) throws RemoteException;

    String getBoard_id() throws RemoteException;

    String getRMI_key() throws RemoteException;

    void clear_object() throws RemoteException;

    int get_object_length() throws RemoteException;
}
