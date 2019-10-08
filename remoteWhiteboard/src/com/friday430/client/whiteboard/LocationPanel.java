package com.friday430.client.whiteboard;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import com.friday430.client.whiteboard.properties.Defaults;

class LocationPanel extends HBox {

   LocationPanel(final Canvas canvas) {
       super();

       final Label locationLbl = new Label("(X: 0, Y: 0)");
       locationLbl.setTextFill(Defaults.TEXT_COLOR);

       canvas.setOnMouseMoved(e -> {
       int x = (int) (canvas.getLocationX() - e.getX()) * -1;
       int y = (int) (canvas.getLocationY() - e.getY()) * -1;
       locationLbl.setText("(X: " + x + ", Y: " + y + ")");
       });
       canvas.setOnMouseDragged(e -> {
           int x = (int) (canvas.getLocationX() - e.getX()) * -1;
           int y = (int) (canvas.getLocationY() - e.getY()) * -1;
           locationLbl.setText("(X: " + x + ", Y: " + y + ")");
       });
       getChildren().add(locationLbl);
   }

}
