package com.friday430.server.whiteboard;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;

/**
 * Menu for WhiteBoard.
 * TODO: modify for remote control
 */
class WhiteBoardMenu extends MenuBar {

    private Canvas canvas;
    private FileChooser fileChooser;

    WhiteBoardMenu(Canvas canvas) {
		this.canvas = canvas;

		fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

		Menu file = new Menu("file");

		MenuItem save = new MenuItem("Save");
		save.setOnAction(new EventHandler<ActionEvent>() {
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
		file.getItems().add(load);

		getMenus().add(file);
    }

    /**
     * Save a screenshot in a user-specified location.
     */
	private void saveToFile() {
        fileChooser.setTitle("Save");
        WritableImage img = canvas.snapshot(new SnapshotParameters(), null);
		BufferedImage buffImg = SwingFXUtils.fromFXImage(img, null);

		File file = fileChooser.showSaveDialog(null);

		if (file != null) {
			try {
				String extension = file.getPath().substring(file.getPath().lastIndexOf('.') + 1);
				System.out.println(extension);
				ImageIO.write(buffImg, extension, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
    
    /**
     * TODO: Load a screenshot from a user-specified location.
     */
	private void loadFromFile() {
        fileChooser.setTitle("Load");
        fileChooser.showOpenDialog(null);
	}

}
