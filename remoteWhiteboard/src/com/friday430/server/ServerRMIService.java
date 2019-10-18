package com.friday430.server;
import com.friday430.remote.IRemoteBoard;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerRMIService {

    private Registry registry;
    private ServerRMIDao serverRMIDao;
    private ArrayList<IRemoteBoard> board_list = null;

    public ServerRMIService() {
        this.initRMI();
        this.serverRMIDao = ServerRMIDao.getInstance();
        try {
            this.read_on_start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initRMI() {
        try {
            registry = LocateRegistry.createRegistry(1099);
        } catch (Exception var3) {
            var3.printStackTrace();
        }
    }

    public void read_on_start() throws RemoteException, AlreadyBoundException {
        ArrayList<IRemoteBoard> board_list = serverRMIDao.readAll();
        for (IRemoteBoard board : board_list) {
            String rmi_key = board.getRMI_key();
            IRemoteBoard stub = (IRemoteBoard) UnicastRemoteObject.exportObject(board, 0);
            registry.bind(rmi_key, stub);
            this.board_list.add(stub);
        }
    }

    public synchronized boolean createNewBoard(String board_id, String managerKeychain) {
        try {
            String rmi_key = board_id + managerKeychain;
            RmiObject rmiObject = new RmiObject(board_id, rmi_key);
            IRemoteBoard stub = (IRemoteBoard) UnicastRemoteObject.exportObject(rmiObject, 0);
            registry.bind(rmi_key, stub);
            this.saveBoard(stub);
            System.out.println("White Board server is ready!");
            return true;
        } catch (Exception var3) {
            var3.printStackTrace();
            return false;
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

    public void saveBoard(IRemoteBoard board){
        serverRMIDao.writeBoard(board);
    }

    public IRemoteBoard loadBoard(String board_id){
        return serverRMIDao.readBoard(board_id);
    }


    public void saveAll(){
        serverRMIDao.writeAll(board_list);
    }

    public void loadAll(){
        this.board_list = serverRMIDao.readAll();
    }

//    public static void main(String[] args) {
//        ServerRMIService sms = new ServerRMIService();
//        sms.createNewBoard("123", "345");
//        sms.getBoard("123", "345");
//
//
//    }
}
