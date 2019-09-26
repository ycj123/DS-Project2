//package com.friday430.server;
//
//import com.friday430.remote.IRemoteBoard;
//import com.friday430.server.whiteboard.TempPane;
//import javafx.application.Application;
//import javafx.geometry.Rectangle2D;
//import javafx.scene.Scene;
//import javafx.stage.Screen;
//import javafx.stage.Stage;
//
///**
// * White Board Implement Class using JavaFx
// */
//public class BoardImpl extends Application implements IRemoteBoard{
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        primaryStage.setTitle("Collaborative WhiteBoard");
//        primaryStage.setScene(new Scene(new TempPane(), 900, 830));
//        primaryStage.show();
//        //set position
//        Rectangle2D screen = Screen.getScreens().get(Screen.getScreens().size() - 1).getBounds();
//        primaryStage.setX(screen.getMaxX() / 2.0 - primaryStage.getScene().getWidth() / 2.0);
//        primaryStage.setY(screen.getHeight() / 2.0 - primaryStage.getScene().getHeight() / 2.0);
//        primaryStage.centerOnScreen();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    /**
//     * Test
//     */
//    @Override
//    public void geta()
//    {
//        launch();
//        System.out.println("hihi");
//    }
//}
