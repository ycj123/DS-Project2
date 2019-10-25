package com.friday430.client.whiteboard.tools;

import com.friday430.client.whiteboard.Canvas;
import com.friday430.client.whiteboard.properties.Properties;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.util.Optional;

public class Text extends Tool {
    /**
     * Create a Tool. This should be implemented further by subclasses.
     *
     * @param canvas
     */
    public Text(Canvas canvas) {
        super(canvas);
    }

    public void new_text(double x, double y){
        javafx.scene.text.Text text = new javafx.scene.text.Text();
        text.setX(x);
        text.setY(y);
        text.setFill(Properties.getForeColor());
        // This is the start of our line.
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("text tool");
        dialog.setContentText("input text: ");
        Optional<String> result = dialog.showAndWait();
        String content = result.get();
        if (result.isPresent()){
            text.setText(content);
            getCanvas().updateTextRMI(Double.toString(x), Double.toString(y), Double.toString(Properties.getForeColor().getRed()), Double.toString(Properties.getForeColor().getGreen()), Double.toString(Properties.getForeColor().getBlue()), content);
            getCanvas().getChildren().add(text);
        }
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        // End the line (usually this makes a dot if there is no drag).
        new_text(e.getX(), e.getY());
    }
}
