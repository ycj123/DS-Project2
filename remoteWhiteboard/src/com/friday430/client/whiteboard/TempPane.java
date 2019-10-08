package com.friday430.client.whiteboard;


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
