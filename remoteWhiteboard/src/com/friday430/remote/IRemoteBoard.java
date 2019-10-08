package com.friday430.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IRemoteBoard extends Remote {

    void updateChat(String username, String words);

    List<String[]> getChatWithNumber(int number);

    void setImage();

    void getImage();
}
