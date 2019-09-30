package com.friday430.server.whiteboard;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import com.friday430.server.whiteboard.properties.Defaults;

class ZoomPanel extends HBox {

   ZoomPanel(final Canvas canvas) {
       super();

       // Create the slider/label.
       final Slider zoomSlider = new Slider(80, 400, 100);
       final Label zoomLabel = new Label("Zoom: 100%");
       zoomLabel.setTextFill(Defaults.TEXT_COLOR);

       // Setup the listener for the slider.
       zoomSlider.valueProperty().addListener(new ChangeListener<Number>() {
           @Override
           public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
               double zoom = newVal.doubleValue();
               canvas.setZoom(zoom / 100.0);

               // Display zoom as an int because it looks nicer.
               zoomLabel.setText("Zoom: " + (int) zoom + "%");
           }

       });

       // Zoom in/out when scrolling.
       canvas.setOnScroll(new EventHandler<ScrollEvent>() {
           @Override
           public void handle(ScrollEvent e) {
               zoomSlider.setValue(zoomSlider.getValue() + e.getDeltaY() * 1.5);
           }
       });

       // Add the slider/label.
       getChildren().add(zoomSlider);
       getChildren().add(zoomLabel);
   }

}
