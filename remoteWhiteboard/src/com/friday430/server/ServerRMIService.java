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

    public void createNewBoard(String board_id, String managerKeychain) {
        try {
            BoardImpl boardImpl = new BoardImpl();
            IRemoteBoard stub = (IRemoteBoard) UnicastRemoteObject.exportObject(boardImpl, 0);
            registry.bind(board_id + managerKeychain, stub);
            System.out.println("White Board server is ready!");
        } catch (Exception var3) {
            var3.printStackTrace();
        }
    }

    public void getBoard(String board_id, String managerKeychain){
        try{
            registry = LocateRegistry.getRegistry("localhost");
            IRemoteBoard board = (IRemoteBoard) registry.lookup(board_id + managerKeychain);
            board.geta();
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

//    public saveBoard(board_id){
//
//    }
//
//
//    public loadBoard(board_id){
//
//        }
//
//
//    public saveAll(){
//
//        }
//
//    public loadAll(){
//
//        }

    public static void main(String[] args) {
        ServerRMIService sms = new ServerRMIService();
        sms.createNewBoard("123", "345");
        sms.getBoard("123", "345");


    }
}
