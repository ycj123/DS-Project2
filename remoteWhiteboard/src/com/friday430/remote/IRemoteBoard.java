package com.friday430.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteBoard extends Remote {

    void geta() throws RemoteException;
}
