package com.friday430.server;
import com.friday430.remote.IRemoteBoard;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerRMIService {

    private Registry registry;
    private ServerRMIDao serverRMIDao;

    public ServerRMIService() {
        this.initRMI();
        this.serverRMIDao = ServerRMIDao.getInstance();
    }

    private void initRMI() {
        try {
            registry = LocateRegistry.createRegistry(1099);
        } catch (Exception var3) {
            var3.printStackTrace();
        }
    }

    public synchronized void createNewBoard(String board_id, String managerKeychain) {
        try {
            RmiObject rmiObject = new RmiObject();
            IRemoteBoard stub = (IRemoteBoard) UnicastRemoteObject.exportObject(rmiObject, 0);
            registry.bind(board_id + managerKeychain, stub);
            System.out.println("White Board server is ready!");
        } catch (Exception var3) {
            var3.printStackTrace();
        }
    }

    public synchronized IRemoteBoard getBoard(String board_id, String managerKeychain){
        IRemoteBoard board = null;
        try{
            registry = LocateRegistry.getRegistry("localhost");
            board = (IRemoteBoard) registry.lookup(board_id + managerKeychain);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        return board;
    }

    public void saveBoard(String board_id){
        serverRMIDao.writeBoard(board_id);
    }


    public void loadBoard(String board_id){
        serverRMIDao.readBoard(board_id);
        }


//    public void saveAll(){
//        serverRMIDao.readAll();
//        }
//
//    public void loadAll(){
//        serverRMIDao.writeAll();
//        }

//    public static void main(String[] args) {
//        ServerRMIService sms = new ServerRMIService();
//        sms.createNewBoard("123", "345");
//        sms.getBoard("123", "345");
//
//
//    }
}
