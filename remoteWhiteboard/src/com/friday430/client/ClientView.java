package com.friday430.client;

import com.friday430.client.whiteboard.TempPane;
import com.friday430.client.whiteboard.WhiteBoard;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ClientView extends Application {

    private WhiteBoard temp_pane;

    public ClientView(){
        temp_pane = new WhiteBoard();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Collaborative WhiteBoard");
        primaryStage.setScene(new Scene(this.temp_pane, 900, 830));
        primaryStage.show();
        //set position
        Rectangle2D screen = Screen.getScreens().get(Screen.getScreens().size() - 1).getBounds();
        primaryStage.setX(screen.getMaxX() / 2.0 - primaryStage.getScene().getWidth() / 2.0);
        primaryStage.setY(screen.getHeight() / 2.0 - primaryStage.getScene().getHeight() / 2.0);
        primaryStage.centerOnScreen();
    }

    public void update_canvas(Image image){
        this.temp_pane.setCanvas(image);
    }

    public void update_chat(ArrayList<String[]> chat_history){
        this.temp_pane.setTa(chat_history);
    }

    public Image get_canvas(){
        return this.temp_pane.getCanvas();
    }

    public ArrayList<String[]> get_chat() {
        return this.temp_pane.getTa();
    }

    public static void main(String[] args) {
        launch();
    }
}
