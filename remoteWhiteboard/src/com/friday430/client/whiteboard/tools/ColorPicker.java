package com.friday430.client.whiteboard.tools;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import com.friday430.client.whiteboard.properties.Properties;
import com.friday430.client.whiteboard.Canvas;

/**
* A Tool that will set the color to the color of a clicked pixel.
*/
public class ColorPicker extends Tool {

   /**
    * Create a ColorPicker Tool.
    */
   public ColorPicker(Canvas canvas) {
	    super(canvas);
   }

   @Override
   public void onMouseDown(MouseEvent e) {
       // Convert the Canvas into an image.
       WritableImage img = getCanvas().snapshot(new SnapshotParameters(), null);

       // Set the color to the clicked color.
       Properties.setForeColor(img.getPixelReader().getColor((int) e.getX(), (int) e.getY()));
   }
}
