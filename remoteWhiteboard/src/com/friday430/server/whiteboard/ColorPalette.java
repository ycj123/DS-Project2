//package com.friday430.server.whiteboard;
//
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.HPos;
//import javafx.scene.control.ColorPicker;
//import javafx.scene.control.Label;
//import javafx.scene.layout.GridPane;
//import javafx.scene.paint.Color;
//import com.friday430.server.whiteboard.properties.*;
//
//public class ColorPalette extends GridPane implements PropertiesListener {
//
//    private ColorPicker backColorPicker;
//
//	ColorPalette() {
//	// Create background color picker.
//	Label backLabel = new Label("Background");
//	backLabel.setTextFill(Defaults.TEXT_COLOR);
//	backColorPicker = new ColorPicker(Color.WHITE);
//	backColorPicker.setPrefWidth(103.0);
//	backColorPicker.setOnAction(new EventHandler<ActionEvent>() {
//	    @Override
//	    public void handle(ActionEvent e) {
//		setBackground(backColorPicker.getValue());
//	    }
//	});
//
//	// Add the components.
//	add(backLabel, 0, 0);
//	add(backColorPicker, 0, 1);
//
//	// Center the labels.
//	setHalignment(backLabel, HPos.CENTER);
//	Properties.addListener(this);
//    }
//
//    /**
//     * Tells the properties that the background color has been changed.
//     */
//	private void setBackground(Color color) {
//	Properties.setBackColor(color);
//    }
//
//    /**
//     * If the foreground is changed, this will tell the foreground color picker to change colors.
//     */
//     @Override
//     public void onForeColorChng(Color color) {}
//
//    /**
//     * If the background is changed, this will tell the background color picker to change colors.
//     */
//    @Override
//    public void onBackColorChng(Color color) {
//	backColorPicker.setValue(color);
//    }
//
//    @Override
//    public void onWidthChng(double width) {}
//}