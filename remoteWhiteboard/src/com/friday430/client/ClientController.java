package com.friday430.client;

import java.io.*;
import java.net.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class ClientController{
    private static String host_ip = "localhost" ;//与连的网有关
    private static String manager_ip = "localhost";//与连的网有关
    private static int managerPORT = 3758;
    private static int serverPORT= 5555;
    //    private ClientView clientView;
    private String board_name;

    public ClientController(){
//        private ClientView clientView = new ClientView();
        //视窗调用sendMessagetoServer()函数
        board_name = "asdasd";
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
            String[] request = serverResponse.split("###");
            String board_id = request[0];
            String manager_IP = request[1];
            String managerPORT = request[2];
            System.out.println("received");

            int toManagerPORT = Integer.parseInt(managerPORT);
            this.askPermission(manager_ip, toManagerPORT, board_name);
    }

    //给manager发请求
    public String askPermission(String manager_ip, int toManagerPORT,String board_name){
        try(Socket socket = new Socket(manager_ip, toManagerPORT);)
        {


            // Output Stream
            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(),"UTF-8");
            BufferedWriter output = new BufferedWriter(out);
            output.write(board_name);
            System.out.println("Request sent to Manager: join" + board_name);
            output.flush();

            // Input stream
            InputStreamReader in = new InputStreamReader(socket.getInputStream(),"UTF-8");
            BufferedReader input = new BufferedReader(in);

            String message = input.readLine();
            return message;

        }catch (IOException e) {
            e.printStackTrace();
        }return null;
    }

//    public void askForBoard(String board_id,String key_chain,int toManagerPORT) throws IOException {
//        try {
//            Socket socket = new Socket(manager_ip, toManagerPORT);
//            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
//            BufferedWriter output = new BufferedWriter(out);
//            String identity_data = board_id+"###"+key_chain;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}

