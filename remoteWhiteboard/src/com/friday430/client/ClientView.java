package com.friday430.client;

import com.friday430.client.whiteboard.TempPane;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ClientView extends Application {

    private BorderPane temp_pane;

    public ClientView(){
        temp_pane = new TempPane();
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

    private void update_canvas(){

    }

    private void update_chat(){

    }

    public static void main(String[] args) {
        launch();
    }
}
