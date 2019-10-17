package com.friday430.client;

import com.friday430.client.whiteboard.Canvas;
import com.friday430.client.whiteboard.WhiteBoard;
import com.friday430.remote.IRemoteBoard;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ClientView extends Application{

    private IRemoteBoard iRemoteBoard;
    //private WhiteBoard temp_pane = new WhiteBoard();

    //public ClientView(IRemoteBoard iRemoteBoard){
    //   temp_pane = WhiteBoard.getInstance(iRemoteBoard);
    //}

    public ClientView(){
        //temp_pane = new WhiteBoard();
    }

    public void init_a(IRemoteBoard rb){
        this.iRemoteBoard = rb;
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Collaborative WhiteBoard");
        //primaryStage.setScene(new Scene(this.temp_pane, 900, 830));
        primaryStage.setScene(new Scene(new WhiteBoard(), 900, 830));
        primaryStage.show();
        //set position
        Rectangle2D screen = Screen.getScreens().get(Screen.getScreens().size() - 1).getBounds();
        primaryStage.setX(screen.getMaxX() / 2.0 - primaryStage.getScene().getWidth() / 2.0);
        primaryStage.setY(screen.getHeight() / 2.0 - primaryStage.getScene().getHeight() / 2.0);
        primaryStage.centerOnScreen();
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
