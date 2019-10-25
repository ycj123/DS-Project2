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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.zip.DeflaterOutputStream;

public class ManagerView extends Application{

    private IRemoteBoard iRemoteBoard;
    private WhiteBoard wb = null;
    private ClientControllerInterface client_controller = null;
    private ManagerController mc= null;
    private boolean is_manager = true;
    private IRemoteBoard rmiObject;

    private String rmiServerIP = "";
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
        //Scanner scanner = new Scanner(System.in);
        //System.out.println("Would you like to create a board? (y/n, n for join)");
        //String c_or_j = scanner.next();
        //if (c_or_j.equals("y")) {
        //    is_manager = true;
        //}
        //if (c_or_j.equals("n")){
        //    is_manager = false;
        //}
        //System.out.println("Please input board name: ");
        //String board_name = scanner.next();
        //System.out.println("Please input RMI server IP: ");
        //rmiServerIP = scanner.next();
        //if (board_name == null || board_name.equals("")){
        //    board_name = "default_board";
        //}
        String username = "";
        String board_name = "";
        JRadioButton managerRadio = new JRadioButton("manager");
        JRadioButton userRadio = new JRadioButton("user");
        ButtonGroup group = new ButtonGroup();
        group.add(managerRadio);
        group.add(userRadio);
        JTextField serveripTextField = new JTextField();
        JTextField boardnameTextField = new JTextField();
        JTextField usernameTextField = new JTextField();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(new JLabel("Select your role"));
        panel.add(managerRadio);
        panel.add(userRadio);
        panel.add(new JLabel("Your name"));
        panel.add(usernameTextField);
        panel.add(new JLabel("RMI server IP"));
        panel.add(serveripTextField);
        panel.add(new JLabel("Whiteboard name"));
        panel.add(boardnameTextField);
        int result = JOptionPane.showConfirmDialog(null, panel, "Settings", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            username = usernameTextField.getText();
            rmiServerIP = serveripTextField.getText();
            board_name = boardnameTextField.getText();
            is_manager = managerRadio.isSelected();
        }else{
            System.exit(0);
        }
        System.out.println("settings: ");
        System.out.println("username: " + username);
        System.out.println("server IP: " + rmiServerIP);
        System.out.println("boardname: "+ board_name);
        System.out.println("is manager: " + is_manager);


        if (is_manager) {
            client_controller = new ManagerController(board_name, rmiServerIP, "4444", username);
            mc = new ManagerController(board_name, rmiServerIP, "37581", username);
        }else{
            client_controller = new ClientController(board_name, rmiServerIP, "5555", username);
        }

        String rmi_key = "";
        while (rmi_key.equals("")){
            Thread.sleep(500);
            rmi_key = this.client_controller.getRMIKey();
        }
        //String rmi_key = this.client_controller.getRMIKey();
        Registry registry = LocateRegistry.getRegistry(this.rmiServerIP, Integer.parseInt(this.rmiServerPort));
        System.out.println(this.rmiServerIP + ", " + this.rmiServerPort);
        System.out.println(registry);
        System.out.println(rmi_key);
        try {
            rmiObject = (IRemoteBoard) registry.lookup(rmi_key);
        }catch (Exception e){
            System.out.println("You are not the rightful manager for this whiteboard.");
            System.exit(0);
        }

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
        //String userName = client_controller.getUserName();
        rmiObject.addNewName(username);
        this.wb = new WhiteBoard(rmiObject, is_manager, username);
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
                try {
                    if (is_manager) {
                        rmiObject.removeAll();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
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
