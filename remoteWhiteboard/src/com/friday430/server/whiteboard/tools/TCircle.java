package com.friday430.server.whiteboard.tools;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import com.friday430.server.whiteboard.properties.Properties;

import com.friday430.server.whiteboard.Canvas;

/**
* A Circle tool to draw on a Canvas.
*/
public class TCircle extends Tool {

   private Circle circle;
   private double sX;
   private double sY;

   /**
    * Create a Circle tool.
    */
   public TCircle(Canvas canvas) {
	    super(canvas);
   }

   private void startCircle(double x, double y) {
       circle = new Circle();
       sX = x;
       sY = y;
   }

   private void endCircle(double x, double y) {
       double eX = x;
       double eY = y;
       if (x < sX) {// end x < start x
           eX = sX;
           sX = x;
       }
       if (y < sY) {
           eY = sY;
           sY = y;
       }

       circle.setCenterX(sX + (eX - sX)/2);
       circle.setCenterY(sY + (eY - sY)/2);
       double radius = Math.sqrt(Math.pow(eX - sX, 2) + Math.pow(eY - sY, 2)) / 2;
       circle.setRadius(radius);
       circle.setFill(Color.TRANSPARENT);
       circle.setStroke(Properties.getForeColor());
       circle.setStrokeWidth(Properties.getWidth());
       getCanvas().addShape(circle);
   }

   @Override
   public void onMouseDown(MouseEvent e) {
       super.onMouseDown(e);
       startCircle(e.getX(), e.getY());
   }

   @Override
   public void onMouseReleased(MouseEvent e) {
       endCircle(e.getX(), e.getY());
   }
}
