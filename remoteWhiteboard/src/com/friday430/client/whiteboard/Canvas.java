package com.friday430.client.whiteboard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import com.friday430.client.whiteboard.properties.*;
import com.friday430.remote.IRemoteBoard;

public class Canvas extends Pane implements PropertiesListener {

   private List<Shape> erase;
   private double locationX = 0.0;
   private double locationY = 0.0;

   private WhiteBoard whiteBoard = null;

   public Canvas(WhiteBoard whiteBoard) {
       this.whiteBoard = whiteBoard;
       erase = new LinkedList<Shape>();
       Properties.addListener(this);
   }

   public void clear() {
       // erase = new LinkedList<Shape>();
       getChildren().clear();
       whiteBoard.clear();
   }
   public void clearCanvas() {
       getChildren().clear();
   }

   private void setBackground(Color color) {
	    setBackground(new Background(new BackgroundFill(
		    color, new CornerRadii(0), new Insets(0))));
   }

   public void updateTextRMI(String x, String y, String red, String green, String blue, String content){
       HashMap<String, String> text_hash = new HashMap<>();
       text_hash.put("x", x);
       text_hash.put("y", y);
       text_hash.put("red", red);
       text_hash.put("green", green);
       text_hash.put("blue", blue);
       text_hash.put("content", content);
       this.whiteBoard.updateCurrentText(text_hash);


   }

   public void updateShapeRMI(double shape, double start_x, double start_y, double end_x, double end_y,
                              double red, double green, double blue, double width){
       HashMap<String, Double> shape_hash = new HashMap<>();
       shape_hash.put("shape", shape);
       shape_hash.put("start_x", start_x);
       shape_hash.put("start_y", start_y);
       shape_hash.put("end_x", end_x);
       shape_hash.put("end_y", end_y);
       shape_hash.put("red", red);
       shape_hash.put("green", green);
       shape_hash.put("blue", blue);
       shape_hash.put("width", width);
       this.whiteBoard.updateCurrentShape(shape_hash);
   }

   public void addShape(Shape s) {
       getChildren().add(s);
   }

   /**
    * Add an eraser Shape to this Canvas.
    *
    * If the background color is changed, these Shapes will update their color as well.
    */
   public void addErase(Shape s) {
       getChildren().add(s);
       erase.add(s);
   }

   /**
    * Get the zoom of this Canvas.
    */
   public double getZoom() {
	    return getScaleX();
   }

   /**
    * Set the zoom of this Canvas.
    */
   void setZoom(double zoom) {
       setScaleX(zoom);
       setScaleY(zoom);
   }

   /**
    * Get the X distance this Canvas is from the origin
    */
   double getLocationX() {
	    return locationX;
   }

   /**
    * Get the Y distance this Canvas is from the origin
    */
   double getLocationY() {
	    return locationY;
   }

   /**
    * Set the X and Y distance this Canvas is from the origin.
    *
    * Should be done when changing the translation.
    */
   public void setLocation(double x, double y) {
       locationX = x;
       locationY = y;
   }

   /**
    * Changes the background color and erase Shape color.
    */
   @Override
   public void onBackColorChng(Color color) {
       setBackground(color);

       // Set all the erase shape colors.
       for (Shape n : erase) {
           n.setStroke(color);
       }
   }

   // Do not care.
   @Override
   public void onWidthChng(double width) {}
   @Override
   public void onForeColorChng(Color color) {}
}