package com.friday430.client.whiteboard.tools;

import com.friday430.client.whiteboard.properties.Properties;

import com.friday430.client.whiteboard.Canvas;

/**
* An Eraser to erase your mistakes on a Canvas.
*/
public class Eraser extends Pen {

   /**
    * Create an eraser object.
    */

   public Eraser(Canvas canvas) {
	    super(canvas);
   }

   @Override
   protected void startLine(double x, double y) {
       // Let the Pen class deal with most of this.
       super.startLine(x, y);
       line.setStroke(Properties.getBackColor());
       red = Properties.getBackColor().getRed();
       green = Properties.getBackColor().getGreen();
       blue = Properties.getBackColor().getBlue();
   }

   @Override
   protected void endLine(double x, double y) {
       line.setEndX(x);
       line.setEndY(y);
       getCanvas().updateShapeRMI(0.0, sX, sY, x, y, red, green, blue, width);
       getCanvas().addErase(line);
   }

}
