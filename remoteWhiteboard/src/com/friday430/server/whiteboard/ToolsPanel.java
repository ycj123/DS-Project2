//package com.friday430.server.whiteboard;
//
//import com.friday430.server.whiteboard.properties.Defaults;
//import com.friday430.server.whiteboard.tools.*;
//import javafx.geometry.Pos;
//import javafx.scene.Node;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.control.Button;
//import javafx.scene.control.TextInputDialog;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.Background;
//import javafx.scene.layout.BackgroundFill;
//import javafx.scene.layout.Border;
//import javafx.scene.layout.BorderStroke;
//import javafx.scene.layout.BorderStrokeStyle;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//
//import java.awt.*;
//
///**
// * Panel shows all the optional tools.
// */
//class ToolsPanel extends VBox {
//
//    private Button penBtn;
//    private Button eraserBtn;
//    private Button colorPickerBtn;
//    private Button sLineBtn;
//    private Button circleBtn;
//    private Button squareBtn;
//    private Button rectAngleBtn;
//    private Button ellipseBtn;
//    private Button clearBtn;
////    private Button triAngleBtn;
//
//    private Pen tools;
//    private Canvas canvas;
//
//    ToolsPanel(Pen tools, Canvas canvas) {
//        super();
//        setBackground(new Background(new BackgroundFill(Defaults.PANE_COLOR, null, null)));
//        setAlignment(Pos.CENTER);
//        this.tools = tools;
//        this.canvas = canvas;
//
//        penBtn = new Button();
//        eraserBtn = new Button();
//        colorPickerBtn = new Button();
//        sLineBtn = new Button();
//        circleBtn = new Button();
//        squareBtn = new Button();
//        rectAngleBtn = new Button();
//        ellipseBtn = new Button();
//        clearBtn = new Button();
////        triAngleBtn = new Button();
//
//        String PEN_ICON = "./Icons/pen.png";
//        createBtn(penBtn, PEN_ICON);
//        String ERASER_ICON = "./Icons/eraser.png";
//        createBtn(eraserBtn, ERASER_ICON);
//        String COLOR_PICKER_ICON = "./Icons/color-picker.png";
//        createBtn(colorPickerBtn, COLOR_PICKER_ICON);
//        String LINE_ICON = "./Icons/line.png";
//        createBtn(sLineBtn, LINE_ICON);
//        String CIRCLE_ICON = "./Icons/circle.png";
//        createBtn(circleBtn, CIRCLE_ICON);
//        String ELLIPSE_ICON = "./Icons/ellipse.png";
//        createBtn(ellipseBtn, ELLIPSE_ICON);
//        String SQUARE_ICON = "./Icons/square.png";
//        createBtn(squareBtn, SQUARE_ICON);
//        String RECTANGLE_ICON = "./Icons/rectangle.png";
//        createBtn(rectAngleBtn, RECTANGLE_ICON);
//        String CLEAR_ICON = "./Icons/clear.png";
//        createBtn(clearBtn, CLEAR_ICON);
////        createBtn(triAngleBtn, TRIANGLE_ICON);
//        setupButtons();
//
//        setActive(penBtn);
//    }
//
//    /**
//     * Create button with respect to location.
//     */
//    private void createBtn(Button btn, String imgLocation) {
//        // public static String TRIANGLE_ICON = "./Icons/triangle.png";
//        int BTN_SIZE = 25;
//        Image img = new Image(getClass().getResourceAsStream(imgLocation), BTN_SIZE, BTN_SIZE, true, true);
//        ImageView imgView = new ImageView(img);
//        btn.setGraphic(imgView);
//        btn.setPrefSize(BTN_SIZE, BTN_SIZE);
//        getChildren().add(btn);
//    }
//
//    /**
//     * Setup the button actions.
//     * Change the tool to the aimed tool when clicked.
//     */
//    private void setupButtons() {
//        penBtn.setOnAction(arg0 -> {
//        tools.setTool(new Pen(canvas));
//        setActive(penBtn);
//        });
//
//        eraserBtn.setOnAction(arg0 -> {
//            tools.setTool(new Eraser(canvas));
//            setActive(eraserBtn);
//        });
//
//        colorPickerBtn.setOnAction(arg0 -> {
//            tools.setTool(new ColorPicker(canvas));
//            setActive(colorPickerBtn);
//        });
//
//        sLineBtn.setOnAction(arg0 -> {
//            tools.setTool(new TLine(canvas));
//            setActive(sLineBtn);
//        });
//        circleBtn.setOnAction(arg0 -> {
//            tools.setTool(new TCircle(canvas));
//            setActive(circleBtn);
//        });
//        squareBtn.setOnAction(arg0 -> {
//            tools.setTool(new Square(canvas));
//            setActive(squareBtn);
//        });
//        rectAngleBtn.setOnAction(arg0 -> {
//            tools.setTool(new TRectangle(canvas));
//            setActive(rectAngleBtn);
//        });
//        ellipseBtn.setOnAction(arg0 -> {
//            tools.setTool(new TEllipse(canvas));
//            setActive(ellipseBtn);
//        });
//        clearBtn.setOnAction(arg0 -> {
//            new Clear(canvas);
//            // canvas = new Canvas();
//            setActive(clearBtn);
//        });
//        //Add text button
////        textBtn.setOnAction(arg0 -> {
////            TextInputDialog dialog = new TextInputDialog("");
////            dialog.setTitle("文本输入框");
////            dialog.setContentText("请输入");
////            dialog.setHeaderText("修改字体后，直接在画布点击");
////            Optional<String> result = dialog.showAndWait();
////            if (result.isPresent()){
////                Shape.resetText(result.get());
////                GraphicsContext gc = canvas.getGraphicsContext2D();
////                gc.setLineWidth(1);
////                gc.setFont(Font.font(Shape.fontFamily, Shape.fontSize));
////                gc.setStroke(Shape.color);
////                gc.strokeText(Shape.Text, event.getX(), event.getY());
////            }
////        });
////        triAngleBtn.setOnAction(new EventHandler<ActionEvent>() {
////            @Override
////            public void handle(ActionEvent arg0) {
////                tools.setTool(new TriAngle(canvas));
////                setActive(triAngleBtn);
////            }
////        });
//    }
//
//    /**
//     * Set a button to look active. Also sets the other buttons inactive.
//     */
//    private void setActive(Button btn) {
//        // Set the active button's border
//        btn.setBorder(new Border(new BorderStroke(Color.CORNFLOWERBLUE, BorderStrokeStyle.SOLID, null, null)));
//
//        // Set the rest buttons' border
//        for (Node n : getChildren()) {
//            if (!n.equals(btn)) {
//            ((Button) n).setBorder(null);
//            }
//        }
//    }
//
//}
