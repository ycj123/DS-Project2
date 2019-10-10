package com.friday430.remote;

import javafx.scene.image.Image;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IRemoteBoard extends Remote {

    void setChat(ArrayList<String[]> chat);

    List<String[]> getChatWithNumber(int number);

    ArrayList<String[]> getChat();

    void setImage(Image image);

    Image getImage();
}
