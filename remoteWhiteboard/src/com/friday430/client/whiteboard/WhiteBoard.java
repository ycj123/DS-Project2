package com.friday430.client.whiteboard;

import com.friday430.client.whiteboard.properties.Defaults;
import com.friday430.client.whiteboard.properties.Properties;
import com.friday430.client.whiteboard.tools.Pen;
import com.friday430.remote.IRemoteBoard;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
* WhiteBoard
*/
public class WhiteBoard extends BorderPane {

    private Canvas canvas;
	private  Pen pen;
	private WhiteBoardMenu whiteBoardMenu;
	private TextArea ta;
	private TextField tf;
	private IRemoteBoard iRemoteBoard;
	private int index_shown = 0;
	private  String userName;


	//private ArrayList<String[]> chat_history;


	//private static WhiteBoard whiteBoard = null;

//	public static WhiteBoard getInstance(IRemoteBoard iRemoteBoard){
//		if (whiteBoard == null) {
//			if (iRemoteBoard == null){
//				System.out.println("!!!get instance whiteboard, both whiteboard and rmi null");
//			}
//			whiteBoard = new WhiteBoard(iRemoteBoard);
//		}
//		return whiteBoard;
//	}

	private void draw_existing_canvas() throws RemoteException {
		this.canvas.clearCanvas();
		ArrayList<HashMap<String, Double>> canvas_list = null;
		 try {
		 	canvas_list = this.iRemoteBoard.getCanvas_object(this.index_shown);
		 } catch (IllegalArgumentException e) {
		 	this.index_shown  = 0;
		 	this.canvas.clearCanvas();
		 	canvas_list = this.iRemoteBoard.getCanvas_object(this.index_shown);
		 }

		for (HashMap<String, Double> canvas_object_map : canvas_list){
			int shape_type = (int) Math.floor(canvas_object_map.get("shape"));
			double sX = canvas_object_map.get("start_x");
			double sY = canvas_object_map.get("start_y");
			double eX = canvas_object_map.get("end_x");
			double eY = canvas_object_map.get("end_y");
			double red = canvas_object_map.get("red");
			double green = canvas_object_map.get("green");
			double blue = canvas_object_map.get("blue");
			Color color = Color.color(red,green,blue);
			double width = canvas_object_map.get("width");
			switch (shape_type){
				case 0: //line
					Line line = new Line();
					line.setStartX(sX);
					line.setStartY(sY);
					line.setEndX(eX);
					line.setEndY(eY);
					line.setStroke(color);
					line.setStrokeWidth(width);
					line.setStrokeLineCap(StrokeLineCap.ROUND);
					line.setStrokeLineJoin(StrokeLineJoin.BEVEL);
					canvas.addShape(line);
					break;
				case 1: //circle
					Circle circle = new Circle();
					circle.setCenterX(sX + (eX - sX)/2);
					circle.setCenterY(sY + (eY - sY)/2);
					double radius = Math.sqrt(Math.pow(eX - sX, 2) + Math.pow(eY - sY, 2)) / 2;
					circle.setRadius(radius);
					circle.setFill(Color.TRANSPARENT);
					circle.setStroke(color);
					circle.setStrokeWidth(width);
					canvas.addShape(circle);
					break;
				case 2: //ellipse
					Ellipse ellipse = new Ellipse();
					double dx = Math.abs(eX -sX);
					double dy = Math.abs(eY -sY);
					double tx = eX;
					double ty = eY;
					if (eX < sX) {// end x < start x
						eX = sX;
						sX = tx;
					}
					if (eY < sY) {
						eY = sY;
						sY = ty;
					}
					ellipse.setCenterX(sX + (eX - sX)/2);
					ellipse.setCenterY(sY + (eY - sY)/2);
					// radius = Math.sqrt(Math.pow(eX - sX, 2) + Math.pow(eY - sY, 2) )/ 2;
					// circle.setRadius(radius);
					ellipse.setRadiusX(dx);
					ellipse.setRadiusY(dy);
					ellipse.setFill(Color.TRANSPARENT);
					ellipse.setStroke(color);
					ellipse.setStrokeWidth(width);
					canvas.addShape(ellipse);
					break;
				case 3: //rectangle
					Rectangle rectangle = new Rectangle();
					rectangle.setX(sX);
					rectangle.setY(sY);
					double dx_r = Math.abs(eX -sX);
					double dy_r = Math.abs(eY -sY);
					rectangle.setWidth(dx_r);
					rectangle.setHeight(dy_r);
					rectangle.setFill(Color.TRANSPARENT);
					rectangle.setStroke(color);
					rectangle.setStrokeWidth(width);
					canvas.addShape(rectangle);
					break;
				case 4: //square
					Rectangle square = new Rectangle();
					square.setX(sX);
					square.setY(sY);
					double dx_s = Math.abs(eX -sX);
					double dy_s = Math.abs(eY -sY);
					double radius_s = Math.max(dx_s,dy_s);
					square.setWidth(radius_s);
					square.setHeight(radius_s);
					square.setFill(Color.TRANSPARENT);
					square.setStroke(color);
					square.setStrokeWidth(width);
					canvas.addShape(square);
			}
		}
	}

	public void reload_existing_chat(){
//		this.chat_history;
//		this.iRemoteBoard.getChat();
		try {
			setTa(this.iRemoteBoard.getChat());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void check_if_alive(){
		try {
			ArrayList<String> client_list = iRemoteBoard.get_namelist();
			if (!client_list.contains(this.userName)){
				JOptionPane.showMessageDialog(null, "Your have been removed from whiteboard.");
				System.exit(0);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}


	public WhiteBoard(IRemoteBoard iRemoteBoard, boolean isManager, String userName) throws RemoteException {
		this.iRemoteBoard = iRemoteBoard;
		this.userName = userName;
		//System.out.println("in white board");
		//System.out.println(Arrays.toString(this.iRemoteBoard.getChat().get(0)));
       	canvas = new Canvas(this);
       	pen = new Pen(canvas);
       	whiteBoardMenu = new WhiteBoardMenu(canvas, iRemoteBoard);
		pen.setTool(pen);

        setCenter(canvas);
        setupLeft();
        setupRight();
		setupDown();
		setupUp(isManager);
		draw_existing_canvas();// init
		// this.index_shown = this.iRemoteBoard.get_object_length();
		TimerTask timerTask = new TimerTask(){
		
			@Override
			public void run() {
				// TODO Auto-generated method stub
				// int current_len = iRemoteBoard.get_object_length();
				// if (current_len != index_shown) {
				// 	draw_existing_canvas(index_shown);
				// 	index_shown = iRemoteBoard.get_object_length();
				// }
				Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    // if (totalTime != 59) {
                    //     totalTime++;
                    //     timePrint();
                    // } else {
                    //     totalTime = 0;
                    //     minutes++;
                    //     timePrint();
                    // }
					// checkTime();
//					int current_len = 0;
//					try {
//						current_len = iRemoteBoard.get_object_length();
//					} catch (RemoteException e) {
//						e.printStackTrace();
//					}
//					if (current_len != index_shown) {
						try {
							check_if_alive();
							draw_existing_canvas();
							reload_existing_chat();
						} catch (RemoteException e) {
							e.printStackTrace();
						}
//						try {
//							index_shown = iRemoteBoard.get_object_length();
//						} catch (RemoteException e) {
//							e.printStackTrace();
//						}
//					}
                }
            });
			}
		};
		Timer timer = new Timer();
		timer.schedule(timerTask, 100, 2000);
	}

	//public ArrayList<String[]> getTa(){
	//	return this.chat_history;
	//}

	public void updateCurrentShape(HashMap<String, Double> shape_map){
		try{
		this.iRemoteBoard.updateCanvas_object(shape_map);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	//public Image getCanvas(){
//		return this.whiteBoardMenu.saveToImage();
//	}

	public void setTa(ArrayList<String[]> chat_history){

		String chat_formatted = "";
		for (String[] chat : chat_history) {
			String user_name = chat[0];
			String user_words = chat[1];
			chat_formatted = chat_formatted.concat(user_name + "\n");
			chat_formatted = chat_formatted.concat(user_words + "\n");
			chat_formatted = chat_formatted.concat("\n");
		}
		ta.setText(chat_formatted);
	}

	public void clear() {
		try {
			this.iRemoteBoard.clear_object();
			this.index_shown = 0;
		} catch (Exception e) {
			e.printStackTrace();
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
		ta.setPrefSize(300,1300);
		HBox send_box = new HBox();
		tf = new TextField();
		tf.setPrefWidth(250);
		Button send_button = new Button("Send");
		send_button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					String tfText = tf.getText();
					iRemoteBoard.updateChat(userName, tfText);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});

		send_box.getChildren().add(tf);
		send_box.getChildren().add(send_button);

		right.getChildren().add(ta);
		right.getChildren().add(send_box);
		setRight(right);
	}

   private void setupUp(boolean isManager) {
		VBox up = new VBox();
		up.setBackground(new Background(new BackgroundFill(Defaults.PANE_COLOR, null, null)));
		if (isManager) {
			up.getChildren().add(this.whiteBoardMenu);
		}
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
