package com.friday430.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteBoard extends Remote {
    public int print(int a) throws RemoteException;
}
