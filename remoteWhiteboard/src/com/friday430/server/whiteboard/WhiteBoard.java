package com.friday430.server.whiteboard;

import com.friday430.remote.IRemoteBoard;
import com.friday430.server.whiteboard.properties.Defaults;
import com.friday430.server.whiteboard.tools.Pen;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.rmi.RemoteException;

/**
* WhiteBoard
*/
class WhiteBoard extends BorderPane {

   private Canvas canvas;
	private  Pen pen;

	WhiteBoard() {
       canvas = new Canvas();
       pen = new Pen(canvas);
		pen.setTool(pen);

       setCenter(canvas);
       setupLeft();
		setupDown();
		setupUp();
	}

   private void setupLeft() {
		ToolsPanel tp = new ToolsPanel(pen, canvas);
		Separator separator = new Separator();
		separator.setOrientation(Orientation.VERTICAL);
		VBox left = new VBox();
		left.setBackground(new Background(new BackgroundFill(Defaults.PANE_COLOR, null, null)));
		left.setAlignment(Pos.CENTER);
		left.setSpacing(10);
	   ColorPanel colorPanel = new ColorPanel();
       left.getChildren().add(colorPanel.getColorPanel());
		left.getChildren().add(tp);
		left.getChildren().add(separator);
		setLeft(left);
	}

   private void setupUp() {
		VBox up = new VBox();
		up.setBackground(new Background(new BackgroundFill(Defaults.PANE_COLOR, null, null)));
		up.getChildren().add(new WhiteBoardMenu(canvas));
		setTop(up);
	}

   private void setupDown() {
		VBox down = new VBox();
       WidthPanel widthPanel = new WidthPanel();
       ColorPalette colorPalette = new ColorPalette();
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);

		HBox bottomUp = new HBox();
		bottomUp.setSpacing(10);
		bottomUp.setPadding(new Insets(5));
		bottomUp.getChildren().add(widthPanel);
		bottomUp.getChildren().add(colorPalette);

		HBox bottomDown = new HBox();
		bottomDown.setSpacing(10);
		bottomDown.setPadding(new Insets(5));
		bottomDown.getChildren().add(new ZoomPanel(canvas));
		bottomDown.getChildren().add(spacer);
		bottomDown.getChildren().add(new LocationPanel(canvas));

		Separator separator = new Separator();
		down.setBackground(new Background(new BackgroundFill(Defaults.PANE_COLOR, null, null)));
		down.getChildren().add(separator);
		down.getChildren().add(bottomUp);
		down.getChildren().add(bottomDown);
		setBottom(down);
	}

}
