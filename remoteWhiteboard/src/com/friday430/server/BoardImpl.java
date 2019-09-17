package com.friday430.server;

import com.friday430.remote.IRemoteBoard;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BoardImpl extends UnicastRemoteObject implements IRemoteBoard {
    protected BoardImpl() throws RemoteException {
    }

    @Override
    public int print(int a) throws RemoteException {
        return 0;
    }
}
