package com.friday430.client.whiteboard.tools;

import com.friday430.client.whiteboard.Canvas;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;

/**
* A arrow tool.
*/
public class Arrow extends Tool {

   /**
    * Create an arrow tool.
    */
   public Arrow(Canvas canvas) {
	    super(canvas);
   }

   @Override
   EventHandler<MouseEvent> shapeOnMouseDraggedEventHandler() {
           return t -> {
                orgSceneX = t.getSceneX();
                orgSceneY = t.getSceneY();
                orgTranslateX = ((Shape)(t.getSource())).getTranslateX();
                orgTranslateY = ((Shape)(t.getSource())).getTranslateY();
            };
   }

   @Override
   EventHandler<MouseEvent> shapeOnMousePressedEventHandler() {
           return t -> {
           double offsetX = t.getSceneX() - orgSceneX;
           double offsetY = t.getSceneY() - orgSceneY;
           double newTranslateX = orgTranslateX + offsetX;
           double newTranslateY = orgTranslateY + offsetY;

           ((Shape)(t.getSource())).setTranslateX(newTranslateX);
           ((Shape)(t.getSource())).setTranslateY(newTranslateY);
       };
   }
}
