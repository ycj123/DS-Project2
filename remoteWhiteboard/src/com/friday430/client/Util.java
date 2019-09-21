package com.friday430.client;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Util {

    public static String checkInputPort(String port[]){
        String return_msg = "OK";
        if (port.length == 1) {
            if (!port[0].matches("(6553[0-5]|655[0-2][0-9]\\d|65[0-4](\\d){2}|6[0-4](\\d){3}|[1-5](\\d){4}|[1-9](\\d){0,3})")) {
                return_msg = "Invalid port number.";
            }
        }else if(port.length != 0) {
            return_msg = "Invalid arguments. Usage: Server <server_port>\nEmpty arguments lead to default setting: port 9000";
        }
        return return_msg;
    }


    public static String checkArgs(String[] args) {
        String ret_string = "OK";
        if (args.length == 2) {
            if (!args[0].matches("((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.|$)){4}")) {
                ret_string = "Invalid IP address.";
            }
            if (!args[1].matches("(6553[0-5]|655[0-2][0-9]\\d|65[0-4](\\d){2}|6[0-4](\\d){3}|[1-5](\\d){4}|[1-9](\\d){0,3})")) {
                ret_string = "Invalid port number.";
            }
        }else if (args.length != 0){
            ret_string = "Invalid arguments. Usage: ClientGUI <server_ip> <server_port>\nEmpty arguments lead to default setting: 127.0.0.1:9000";
        }
        return ret_string;
    }

    public static String getTimeString(){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return myDateObj.format(myFormatObj);
    }
}
