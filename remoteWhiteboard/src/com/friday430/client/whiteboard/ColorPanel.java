package com.friday430.client.whiteboard;

import java.util.ArrayList;
import java.util.List;
import com.friday430.client.whiteboard.properties.*;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ColorPicker;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class ColorPanel {
   private VBox content;
   private  Color presentColor = Color.BLACK;
   private static ColorPicker colorPicker = new ColorPicker();
   private List<Button> colorButton = new ArrayList<Button>();
   private String[] color = {"#ffffff", "#000000", "#848683", "#c4c3be", "#cdbedb", "#b97b56",
           "#ffadd6", "#f01e1f", "#89010d", "#fef007", "#ffc80c", "#ff7c26",
           "#efe2ab", "#b6e51d", "#24b04f", "#93dceb", "#6997bb", "#07a0e6",
           "#d9a2dc", "#9c4ca1","#3b46e0"};
   ColorPanel(){
       initUI();
   }
   private void initUI(){

       content = new VBox();
       content.setAlignment(Pos.CENTER);

       colorPicker = new ColorPicker();
       colorPicker.setPrefWidth(Size.COLOR_PICKER_WIDTH);
       colorPicker.setStyle("-fx-background-color:orange;-fx-color-label-visible:false;");
       colorPicker.setValue(presentColor);
       colorPicker.setOnAction((ActionEvent t) -> {
           presentColor = colorPicker.getValue();
           Shape.resetColor(presentColor);
           Properties.setForeColor(presentColor);
       });

       TilePane tilePane = new TilePane();
       tilePane.setAlignment(Pos.CENTER);
       tilePane.setPadding(new Insets(10,10,10,10));
       tilePane.setPrefColumns(2);
       tilePane.setHgap(5);
       tilePane.setVgap(5);

       DropShadow shadow = new DropShadow();
       for(int i=0; i<color.length; i++){
           MyButton cButton = new MyButton(color[i]);
           cButton.setStyle("-fx-base: " + color[i] + ";");
           cButton.setPrefSize(Size.COLOR_BUTTON_WIDTH, Size.COLOR_BUTTON_HEIGHT);
           cButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
               cButton.setEffect(shadow);
           });

           cButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
               cButton.setEffect(null);
           });

           cButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
               presentColor = Color.web( ((MyButton) e.getSource()).getName() );
               colorPicker.setValue(presentColor);
               Shape.resetColor(presentColor);
               Properties.setForeColor(presentColor);
           });
           colorButton.add(cButton);
       }
       tilePane.getChildren().addAll(colorButton);
       content.getChildren().add(colorPicker);
       content.getChildren().add(tilePane);
   }
   VBox getColorPanel(){
       return content;
   }
   public static void setShapeColor(Color color) {
       Shape.resetColor(color);
       colorPicker.setValue(color);
   }
}
