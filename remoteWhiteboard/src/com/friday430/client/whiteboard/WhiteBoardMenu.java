package com.friday430.client.whiteboard;

import java.awt.image.BufferedImage;
import java.io.*;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

// import javax.imageio.ImageIO;

import com.friday430.remote.IRemoteBoard;

/**
* Menu for WhiteBoard.
* TODO: modify for remote control
*/
class WhiteBoardMenu extends MenuBar {

   private Canvas canvas;
   private FileChooser fileChooser;
   private IRemoteBoard iRemoteBoard;
   private File prevFile = null;

   WhiteBoardMenu(Canvas canvas, IRemoteBoard iRemoteBoard) {
		this.canvas = canvas;
		this.iRemoteBoard = iRemoteBoard;

		fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

		Menu file = new Menu("file");

		MenuItem save = new MenuItem("Save");
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
			saveOfSave();
			}
		});

		MenuItem saveas = new MenuItem("SaveAs");
		saveas.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
			saveToFile();
			}
		});

		MenuItem load = new MenuItem("Load");
		load.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
			loadFromFile();
			}
		});

		file.getItems().add(save);
		file.getItems().add(saveas);
		file.getItems().add(load);

		getMenus().add(file);
   }

   /**
    * Save a screenshot in a user-specified location.
    */
	private void saveToFile() {
       fileChooser.setTitle("Save");
    //    WritableImage img = canvas.snapshot(new SnapshotParameters(), null);
		// BufferedImage buffImg = SwingFXUtils.fromFXImage(img, null);
		ArrayList<HashMap<String, Double>> map = null;
		try {
			map = iRemoteBoard.getCanvas_objects();
		}catch (Exception e){
			e.printStackTrace();
		}
		// FileOutputStream os = new FileOutputStream("D:\\student.data");
		// ObjectOutputStream oos = new ObjectOutputStream(os);
		// oos.writeObject(o);
		// oos.flush();
		// oos.close();
		File file = fileChooser.showSaveDialog(null);

		if (file != null) {
			this.prevFile = file;
			try {
				FileOutputStream os = new FileOutputStream(file);
				ObjectOutputStream oos = new ObjectOutputStream(os);
				oos.writeObject(map);
				oos.flush();
				oos.close();
				// ImageIO.write(rmi, extension, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void saveOfSave() {
		if(this.prevFile != null) {
			File file = this.prevFile;
			try {
				FileOutputStream os = new FileOutputStream(file);
				ObjectOutputStream oos = new ObjectOutputStream(os);
				ArrayList<HashMap<String, Double>> map = iRemoteBoard.getCanvas_objects();
				oos.writeObject(map);
				oos.flush();
				oos.close();
				// ImageIO.write(rmi, extension, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			saveToFile();
		}
	}
   /**
    * TODO: Load a screenshot from a user-specified location.
    */
	private void loadFromFile() {
       fileChooser.setTitle("Load");
		File file = fileChooser.showOpenDialog(null);

		if (file != null) {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
				ArrayList<HashMap<String, Double>> map = (ArrayList<HashMap<String, Double>>)ois.readObject();
				ois.close();
				iRemoteBoard.clear_object();
				for (HashMap<String, Double> i : map) {
					iRemoteBoard.updateCanvas_object(i);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

//			System.out.println(file.toURI().toString());

//			BackgroundImage myBI = new BackgroundImage(new Image(file.toURI().toString() ,canvas.getWidth(),canvas.getHeight(),false,true),
//						BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
//						BackgroundSize.DEFAULT);
//			canvas.setBackground(new Background(myBI));

		}
	}

	public void loadFromImage(Image image) {
		BackgroundImage myBI = new BackgroundImage(image,
				BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
		canvas.setBackground(new Background(myBI));
	}

	public Image saveToImage() {
		fileChooser.setTitle("Save");
		Image img = canvas.snapshot(new SnapshotParameters(), null);
		return img;
	}

}
