package com.friday430.client.whiteboard;

import javafx.scene.paint.Color;

class Shape {
   private static String toolName = "PEN";
   private static String lineSize = "7";
   private static int rubberSize = 7;
   private static int fontSize = 12;
   private static String fontFamily = "AIGDT";
   private static Color color = Color.BLACK;
   private static String Text = "";
   static void resetToolName(String name){
       Shape.toolName = name;
   }
   static void resetLineSize(String size){
       Shape.lineSize = size;
   }
   static void resetRubberSize(int size){
       Shape.rubberSize = size;
   }
   static void resetFontSize(int size){
       Shape.fontSize = size;
   }
   static void resetFontFamily(String font){
       Shape.fontFamily = font;
   }
   static void resetColor(Color c){
       Shape.color = c;
   }
   static void resetText(String s){
       Shape.Text = s;
   }
}