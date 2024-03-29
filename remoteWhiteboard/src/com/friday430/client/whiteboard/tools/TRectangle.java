package com.friday430.client.whiteboard.tools;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import com.friday430.client.whiteboard.properties.Properties;

import com.friday430.client.whiteboard.Canvas;

public class TRectangle extends Tool {

   private Rectangle rectangle;
   private double sX;
   private double sY;

   public TRectangle(Canvas canvas) {
       super(canvas);
   }

   private void startRectangle(double x, double y) {
       rectangle = new Rectangle();
       sX = x;
       sY = y;
       rectangle.setX(sX);
       rectangle.setY(sY);
   }

   private void endRectangle(double x, double y) {
       // End the line and add it to the Canvas.
       double dx = Math.abs(x -sX);
       double dy = Math.abs(y -sY);
       rectangle.setWidth(dx);
       rectangle.setHeight(dy);
       rectangle.setFill(Color.TRANSPARENT);
       rectangle.setStroke(Properties.getForeColor());
       rectangle.setStrokeWidth(Properties.getWidth());
       EventHandler<MouseEvent> p = shapeOnMousePressedEventHandler();
       EventHandler<MouseEvent> d = shapeOnMouseDraggedEventHandler();
       rectangle.setOnMousePressed(p);
       rectangle.setOnMouseDragged(d);
       getCanvas().updateShapeRMI(3.0, sX, sY, x, y, Properties.getForeColor().getRed(), Properties.getForeColor().getGreen(), Properties.getForeColor().getBlue(), Properties.getWidth());
       getCanvas().addShape(rectangle);
   }

   @Override
   public void onMouseDown(MouseEvent e) {
       super.onMouseDown(e);
       // This is the start of our line.
       startRectangle(e.getX(), e.getY());
   }

   @Override
   public void onMouseReleased(MouseEvent e) {
       // End the line (usually this makes a dot if there is no drag).
       endRectangle(e.getX(), e.getY());
   }


}
