package com.friday430.server.whiteboard.tools;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import com.friday430.server.whiteboard.properties.Properties;

import com.friday430.server.whiteboard.Canvas;

public class Square extends Tool {

    private Rectangle square;
    private double sX;
    private double sY;

    public Square(Canvas canvas) {
        super(canvas);
    }
          
    private void startSquare(double x, double y) {
        square = new Rectangle();
        sX = x;
        sY = y;
        square.setX(sX);
        square.setY(sY);
    }
        
    private void endSquare(double x, double y) {
        // End the line and add it to the Canvas.
        double dx = Math.abs(x -sX);
        double dy = Math.abs(y -sY);
        double radius = Math.max(dx, dy);
        square.setWidth(radius);
        square.setHeight(radius);
        square.setFill(Color.TRANSPARENT);
        square.setStroke(Properties.getForeColor());
        square.setStrokeWidth(Properties.getWidth());
        getCanvas().addShape(square);
    }
            
    @Override
    public void onMouseDown(MouseEvent e) {
        super.onMouseDown(e);
        // This is the start of our line.
        startSquare(e.getX(), e.getY());
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        // End the line (usually this makes a dot if there is no drag).
        endSquare(e.getX(), e.getY());
    }
}
