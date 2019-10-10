package com.friday430.client.whiteboard;

import com.friday430.client.whiteboard.properties.Defaults;
import com.friday430.client.whiteboard.tools.Pen;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
* WhiteBoard
*/
public class WhiteBoard extends BorderPane {

    private Canvas canvas;
	private  Pen pen;
	private WhiteBoardMenu whiteBoardMenu;
	private TextArea ta;

	private ArrayList<String[]> chat_history;

	public WhiteBoard() {
       canvas = new Canvas();
       pen = new Pen(canvas);
       whiteBoardMenu = new WhiteBoardMenu(canvas);
		pen.setTool(pen);

       setCenter(canvas);
       setupLeft();
       setupRight();
		setupDown();
		setupUp();
	}

	public ArrayList<String[]> getTa(){
		return this.chat_history;
	}

	public Image getCanvas(){
		return this.whiteBoardMenu.saveToImage();
	}

	public void setTa(ArrayList<String[]> chat_history){
		this.chat_history = chat_history;

		String chat_formatted = "";
		for (String[] chat : chat_history) {
			String user_name = chat[0];
			String user_words = chat[1];
			chat_formatted = chat_formatted.concat(user_name + "\n");
			chat_formatted = chat_formatted.concat(user_words + "\n");
		}
	}

	public void setCanvas(Image image){
		this.whiteBoardMenu.loadFromImage(image);
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

	private void setupRight() {
		VBox right = new VBox();
		ta = new TextArea();
		HBox send_box = new HBox();
		TextField tf = new TextField();
		Button send_button = new Button("Send");

		send_box.getChildren().add(tf);
		send_box.getChildren().add(send_button);

		right.getChildren().add(ta);
		right.getChildren().add(send_box);
		setRight(right);
	}

   private void setupUp() {
		VBox up = new VBox();
		up.setBackground(new Background(new BackgroundFill(Defaults.PANE_COLOR, null, null)));
		up.getChildren().add(this.whiteBoardMenu);
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
