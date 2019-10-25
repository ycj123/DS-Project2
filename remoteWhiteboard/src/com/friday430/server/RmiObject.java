package com.friday430.server;

import com.friday430.remote.IRemoteBoard;
import javafx.scene.image.Image;

import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RmiObject extends UnicastRemoteObject implements IRemoteBoard {
    private ArrayList<String[]> chat_history;
    private ArrayList<HashMap<String, Double>> canvas_objects;
    private String board_id;
    private String RMI_key;
    private ArrayList<String> client_list;

    public boolean isMyNameHere(String name){
        return client_list.contains(name);
    }

    public void addNewName(String name){
        client_list.add(name);
    }

    public void removeName(String name){
        client_list.remove(name);
    }

    public void removeAll(){
        client_list.clear();
    }

    @Override
    public ArrayList<String> get_namelist() throws RemoteException {
        return client_list;
    }

    public RmiObject(String board_id, String RMI_key) throws RemoteException{
        super();
        this.chat_history = new ArrayList<>();
        this.canvas_objects = new ArrayList<>();
        this.board_id = board_id;
        this.RMI_key = RMI_key;
        this.client_list = new ArrayList<>();
    }

    public String getRMI_key() throws RemoteException {
        return this.RMI_key;
    }

    public String getBoard_id() throws RemoteException{
        return this.board_id;
    }

    public void clear_object() throws RemoteException{
        this.canvas_objects = null;
        this.canvas_objects = new ArrayList<>();
    }

    public synchronized ArrayList<HashMap<String, Double>> getCanvas_objects() throws RemoteException{
        return this.canvas_objects;
    }

    public synchronized void updateCanvas_object(HashMap<String, Double> new_object) throws RemoteException{
        this.canvas_objects.add(new_object);
    }

    public ArrayList<HashMap<String, Double>> getCanvas_object(int start_index) throws RemoteException, IllegalArgumentException{
        List l = canvas_objects.subList(start_index, canvas_objects.size());
        return new ArrayList<HashMap<String, Double>>(l);
        //return (ArrayList<HashMap<String, Double>>)
    }

    public synchronized void updateChat(String username, String words) throws RemoteException{
        this.chat_history.add(new String[]{username, words});
    }

    public List<String[]> getChatWithNumber(int number) throws RemoteException{
        int array_length = this.chat_history.size();
        return chat_history.subList(array_length - number, array_length);
    }

    public ArrayList<String[]> getChat() throws RemoteException {
        return this.chat_history;
    }

    public void setChat(ArrayList<String[]> chat) throws RemoteException{
        this.chat_history = chat;
    }

    public int get_object_length() throws RemoteException{
        return this.canvas_objects.size();
    }
}
