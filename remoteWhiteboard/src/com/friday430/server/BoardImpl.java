package com.friday430.server;

import com.friday430.remote.IRemoteBoard;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.swing.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BoardImpl extends Application implements IRemoteBoard{

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Collaborative Whiteboard");
        primaryStage.setScene(new Scene(new BorderPane(), 500, 500));
        primaryStage.show();
        // Center it on my primary monitor.
        Rectangle2D screen = Screen.getScreens().get(Screen.getScreens().size() - 1).getBounds();
        primaryStage.setX(screen.getMaxX() / 2.0 - primaryStage.getScene().getWidth() / 2.0);
        primaryStage.setY(screen.getHeight() / 2.0 - primaryStage.getScene().getHeight() / 2.0);
        primaryStage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void geta() {
        System.out.println("hihi");
    }
}
