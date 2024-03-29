package com.friday430.client.whiteboard.tools;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import com.friday430.client.whiteboard.properties.Properties;

import com.friday430.client.whiteboard.Canvas;

/**
* A Line tool to draw on a Canvas.
*/
public class TLine extends Tool {

   private Line line;

    private double sX, sY, red, green, blue, width;

   /**
    * Create a Line tool.
    */
   public TLine(Canvas canvas) {
	    super(canvas);
//	    tool = new TLine(canvas);
   }

   private void startLine(double x, double y) {
       line = new Line();
       line.setStartX(x);
       line.setStartY(y);
       line.setStroke(Properties.getForeColor());
       line.setStrokeWidth(Properties.getWidth());
       line.setStrokeLineCap(StrokeLineCap.ROUND);
       line.setStrokeLineJoin(StrokeLineJoin.BEVEL);
       this.sX = x;
       this.sY = y;
       this.red = Properties.getForeColor().getRed();
       this.blue = Properties.getForeColor().getBlue();
       this.green = Properties.getForeColor().getGreen();
       this.width = Properties.getWidth();
   }

   private void endLine(double x, double y) {
	// End the line and add it to the Canvas.
       line.setEndX(x);
       line.setEndY(y);
       EventHandler<MouseEvent> p = shapeOnMousePressedEventHandler();
       EventHandler<MouseEvent> d = shapeOnMouseDraggedEventHandler();
       line.setOnMousePressed(p);
       line.setOnMouseDragged(d);
       getCanvas().updateShapeRMI(0.0, sX, sY, x, y, red, green, blue, width);
       getCanvas().addShape(line);
   }

   @Override
   public void onMouseDown(MouseEvent e) {
       super.onMouseDown(e);
       // This is the start of our line.
       startLine(e.getX(), e.getY());
   }

   @Override
   public void onMouseReleased(MouseEvent e) {
       // End the line (usually this makes a dot if there is no drag).
       endLine(e.getX(), e.getY());
   }
}
