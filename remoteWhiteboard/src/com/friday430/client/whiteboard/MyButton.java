package com.friday430.client.whiteboard;

import javafx.scene.control.Button;

class MyButton extends Button{
   private String name;
   MyButton(String n){
       super();
       this.name = n;
   }

   String getName(){
       return name;
   }
}
