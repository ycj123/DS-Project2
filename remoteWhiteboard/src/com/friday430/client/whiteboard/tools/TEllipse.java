package com.friday430.client.whiteboard.tools;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import com.friday430.client.whiteboard.properties.Properties;

import com.friday430.client.whiteboard.Canvas;

/**
* A Ellipse tool to draw on a Canvas.
*/
public class TEllipse extends Tool {

   private Ellipse ellipse;
   private double sX;
   private double sY;

   /**
    * Create a Ellipse tool.
    */
   public TEllipse(Canvas canvas) {
	    super(canvas);
   }

   private void startCircle(double x, double y) {
       ellipse = new Ellipse();
       sX = x;
       sY = y;
   }

   private void endCircle(double x, double y) {
       double eX = x;
       double eY = y;
       double dx = Math.abs(eX -sX);
       double dy = Math.abs(eY -sY);
       if (x < sX) {// end x < start x
           eX = sX;
           sX = x;
       }
       if (y < sY) {
           eY = sY;
           sY = y;
       }

       ellipse.setCenterX(sX + (eX - sX)/2);
       ellipse.setCenterY(sY + (eY - sY)/2);
       // radius = Math.sqrt(Math.pow(eX - sX, 2) + Math.pow(eY - sY, 2) )/ 2;
       // circle.setRadius(radius);
       ellipse.setRadiusX(dx);
       ellipse.setRadiusY(dy);
       ellipse.setFill(Color.TRANSPARENT);
       ellipse.setStroke(Properties.getForeColor());
       ellipse.setStrokeWidth(Properties.getWidth());
       EventHandler<MouseEvent> p = shapeOnMousePressedEventHandler();
       EventHandler<MouseEvent> d = shapeOnMouseDraggedEventHandler();
       ellipse.setOnMousePressed(p);
       ellipse.setOnMouseDragged(d);
       getCanvas().addShape(ellipse);
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
