package com.friday430.client;

import com.friday430.client.whiteboard.Canvas;
import com.friday430.client.whiteboard.WhiteBoard;
import com.friday430.remote.IRemoteBoard;
import com.friday430.server.RmiObject;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.zip.DeflaterOutputStream;

public class ClientView extends Application{

    private IRemoteBoard iRemoteBoard;
    private WhiteBoard wb = null;
    private ClientControllerInterface client_controller = null;
    private ManagerController mc= null;
    private boolean is_manager = true;
    private IRemoteBoard rmiObject;

    private String rmiServerIP = "10.12.189.114";
    private String rmiServerPort = "11112";
    //private WhiteBoard temp_pane = new WhiteBoard();

    //public ClientView(IRemoteBoard iRemoteBoard){
    //   temp_pane = WhiteBoard.getInstance(iRemoteBoard);
    //}

    @Override
    public void init() throws Exception{
        super.init();
        Parameters parameters = getParameters();
        //parameters.getNamed();
        List<String> raw_para_list = parameters.getRaw();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Would you like to create a board? (y/n, n for join)");
        String c_or_j = scanner.next();
        if (c_or_j.equals("y")) {
            is_manager = true;
        }
        if (c_or_j.equals("n")){
            is_manager = false;
        }
        System.out.println("Please input board name: ");
        String board_name = scanner.next();
        System.out.println("Please input RMI server IP: ");
        rmiServerIP = scanner.next();
        if (board_name == null || board_name.equals("")){
            board_name = "default_board";
        }

        if (is_manager) {
            client_controller = new ManagerController(board_name, rmiServerIP, "4444");
            mc = new ManagerController(board_name, rmiServerIP, "37581");
        }else{
            client_controller = new ClientController(board_name, rmiServerIP, "5555");
        }

        String rmi_key = "";
        while (rmi_key.equals("")){
            Thread.sleep(100);
            rmi_key = this.client_controller.getRMIKey();
        }
        //String rmi_key = this.client_controller.getRMIKey();
        Registry registry = LocateRegistry.getRegistry(this.rmiServerIP, Integer.parseInt(this.rmiServerPort));
        System.out.println(this.rmiServerIP + ", " + this.rmiServerPort);
        System.out.println(registry);
        System.out.println(rmi_key);
        rmiObject = (IRemoteBoard) registry.lookup(rmi_key);

        //rmiObject = new RmiObject("1", "2");
//        HashMap<String, Double> tmp = new HashMap<>(){};
//        tmp.put("shape", 0.0);
//        tmp.put("start_x", 100.0);
//        tmp.put("start_y", 100.0);
//        tmp.put("end_x", 200.0);
//        tmp.put("end_y", 200.0);
//        tmp.put("red", 1.0);
//        tmp.put("green", 0.0);
//        tmp.put("blue", 0.0);
//        tmp.put("width", 1.0);
//        rmiObject.updateCanvas_object(tmp);}
        String userName = client_controller.getUserName();
        this.wb = new WhiteBoard(rmiObject, is_manager, userName);
        //parameters.getUnnamed();


    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Collaborative WhiteBoard");
        //primaryStage.setScene(new Scene(this.temp_pane, 900, 830));
        primaryStage.setScene(new Scene(this.wb, 900, 830));
        primaryStage.show();
        //set position
        Rectangle2D screen = Screen.getScreens().get(Screen.getScreens().size() - 1).getBounds();
        primaryStage.setX(screen.getMaxX() / 2.0 - primaryStage.getScene().getWidth() / 2.0);
        primaryStage.setY(screen.getHeight() / 2.0 - primaryStage.getScene().getHeight() / 2.0);
        primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    //public void update_chat(ArrayList<String[]> chat_history){
    //    this.temp_pane.setTa(chat_history);
    //}

    //public ArrayList<String[]> get_chat() {
    //    return this.temp_pane.getTa();
    //}

    //public static void on_canvas_update(HashMap<String, Integer> new_object){
    //    iRemoteBoard.updateCanvas_object(new_object);
    //}

    public static void main(String[] args) {
        launch();
    }
}
