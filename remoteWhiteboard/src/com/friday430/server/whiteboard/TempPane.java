package com.friday430.server.whiteboard;


import com.friday430.server.whiteboard.Canvas;
import com.friday430.server.whiteboard.WhiteBoard;
import com.friday430.server.whiteboard.tools.Pen;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

public class TempPane extends BorderPane {

   private TextArea textArea = new TextArea();

   public TempPane() {
       setCenter(new WhiteBoard());
       setRight(this.textArea);
   }

   public void setText(String s){
       this.textArea.setText(s);
   }

}
