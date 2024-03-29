package com.friday430.client;

import com.friday430.remote.IRemoteBoard;
import javafx.scene.control.TextInputDialog;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

class ClientController implements ClientControllerInterface{
    private static String host_ip = "localhost" ;//与连的网有关
    private static String manager_ip = "localhost";//与连的网有关
    private static int managerPORT = 37581;
    private static int serverPORT = 5555;

    private String rmiServerIP;
    private String rmiServerPort;
    private String board_name;
    private String managerkey;
    private String board_id ;
    private  String manager_keychain;
    private  String userName;

    private ClientView clientView;
    private IRemoteBoard rmiObject = null;

    public ClientController(String board_name, String server_ip, String server_port, String userName){
//        private ClientView clientView = new ClientView();
        //视窗调用sendMessagetoServer()函数
        host_ip = server_ip;
        serverPORT = Integer.parseInt(server_port);
        this.board_name = board_name;
        this.userName = userName;
        this.sendMessagetoServer(board_name, host_ip,serverPORT);
    }


    private String sendMessagetoServer(String board_name,String host_ip, int serverPORT) {
        try (Socket socket = new Socket(host_ip, serverPORT);) {
            //Input Stream
            InputStreamReader in = new InputStreamReader(socket.getInputStream(), "UTF-8");
            BufferedReader input = new BufferedReader(in);
            // Output Stream
            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            BufferedWriter output = new BufferedWriter(out);
            output.write(board_name);
            output.newLine();
            System.out.println("Data sent to Server--> " + board_name);
            output.flush();

            String serverResponse = input.readLine();
            System.out.println("response: "+serverResponse);
            output.flush();
            while (serverResponse != null) {
                if (serverResponse.contains("###")){
                    this.handleServerResponse(serverResponse);
                    return "successful";
                }
            }
        } catch (NoRouteToHostException e) {
            System.out.println("Can't assign requested address (Address not available)!");
        } catch (ConnectException e) {
            System.out.println("Connection refused (no server at the port)");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }return null;
    }

    public void handleServerResponse(String serverResponse) {
        String substr1 = serverResponse.substring(0,5);
        if(substr1.equals("Error")){
            System.out.println(substr1);
        }else{
            String[] request = serverResponse.split("###");
            String manager_IP = request[0];
            String managerPORT = request[1];
            this.board_id = request[2];
            System.out.println("received");

            int toManagerPORT = Integer.parseInt(managerPORT);
            System.out.println(manager_IP + " " + toManagerPORT + " " + board_name);
            this.askPermission(manager_IP, toManagerPORT, board_name);
        }
    }

    //给manager发请求
    public String askPermission(String manager_ip, int toManagerPORT,String board_name){
        try
        {
            System.out.println(manager_ip+" " + toManagerPORT);
//            TextInputDialog dialog = new TextInputDialog("Jack");
//            dialog.setTitle("Client");
//            dialog.setHeaderText("What should we call you?");
//            dialog.setContentText("Please Enter Your Name:");
//            Optional<String> result = dialog.showAndWait();
//            result.ifPresent(name -> this.userName = name);
            //this.userName = JOptionPane.showInputDialog("Please input your name: ");

            Socket socket = new Socket(manager_ip, toManagerPORT);
//            socket.setTcpNoDelay(true);
            // Output Stream
            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(),"UTF-8");
            BufferedWriter output = new BufferedWriter(out);
            // Input stream
            InputStreamReader in = new InputStreamReader(socket.getInputStream(),"UTF-8");
            BufferedReader input = new BufferedReader(in);
            output.write(userName + "###" +board_name + "\n");
            System.out.println(userName + "###" +board_name + "\n");
//            output.write("Test");
            output.flush();
//            out.flush();
//            output.close();
            System.out.println("Flush Complete");


            this.manager_keychain = input.readLine();
            if (manager_keychain.equals("FAIL")){
                System.out.println("Manager refused to accept you.");
                System.exit(0);
            }
            return this.manager_keychain;

        }catch (ConnectException e) {
            System.out.println("Board manager offline.");
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public IRemoteBoard askForBoard(String board_id, String key_chain) {
        try {
            String identity_data = board_id + "###" + key_chain;
            Registry registry = LocateRegistry.getRegistry(this.rmiServerIP, Integer.parseInt(this.rmiServerPort));
            rmiObject = (IRemoteBoard) registry.lookup(identity_data);

            //lientView.setiRemoteBoard(rmiObject);
            ClientView.launch();

            //this.clientView = new ClientView();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rmiObject;
    }

    @Override
    public String getRMIKey() {
        this.managerkey = this.board_id+this.manager_keychain;
        return this.managerkey;
    }

    @Override
    public String getUserName() {
        return this.userName;
    }
}

