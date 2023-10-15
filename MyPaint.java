
import javafx.application.Application;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.layout.HBox;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import javafx.scene.shape.Rectangle;

public class MyPaint extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    GeometryModifications gm = new GeometryModifications();
    PaintCircle paintcircle = new PaintCircle();
    
    PaintRectangle paintRectangle = new PaintRectangle();
    PaintLine paintline = new PaintLine();

    @Override
    public void start(Stage primaryStage) {

        Pane root = new Pane();

        Button selectAllButton = new Button("Select All");
        selectAllButton.setLayoutX(100);
        selectAllButton.setLayoutY(10);
        selectAllButton.setOnAction(e -> selectAllNodes(root));

        Button increaseRadiusButton = new Button("Increase Radius");

        increaseRadiusButton.setOnAction(e -> gm.increase("rectangle"));

        Button decreaseRadiusButton = new Button("Decrease Radius");

        decreaseRadiusButton.setOnAction(e -> gm.decrease("rectangle"));

        Button createCircleButton = new Button("Circle");

        createCircleButton.setOnAction(e
                -> paintcircle.createCircle(root));

        Button createRectangleButton = new Button(" Rectangle");

        createRectangleButton.setOnAction(e
                -> paintRectangle.createRectangle(root));

        Button createLineButton = new Button(" Line");

        createLineButton.setOnAction(e
                -> paintline.createLine(root));

        HBox hb = new HBox();
        hb.setSpacing(5);
        hb.getChildren().addAll(createCircleButton,
                selectAllButton, increaseRadiusButton, decreaseRadiusButton,
                createRectangleButton, createLineButton);

        root.getChildren()
                .add(hb);
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);

        primaryStage.setTitle(
                "Select and Move Circles");
        primaryStage.show();
    }

    private void selectAllNodes(Pane pane) {
        for (Node node : pane.getChildren()) {
            if (node instanceof Circle) {
                ((Circle) node).setFill(Color.RED);

            }
            if (node instanceof Rectangle) {
                ((Rectangle) node).setVisible(true);
            }
        }

    }

}

class GeometryModifications {

    int radius = 50;
    public Circle selectedCircle;
    double width = 100;
    double height = 50;
    public Rectangle selectedRectangle;

    GeometryModifications() {
     
    }

    public void increase(String string) {

        if (string == "circle") {
            if (selectedCircle != null) {
                radius += 10;
                selectedCircle.setRadius(radius);
            }
        };
        if (string == "rectangle") {
           
            if (selectedRectangle != null) {
                width += 10;
                selectedRectangle.setWidth(width);
            }

            if (selectedRectangle != null) {
                height += 10;
                selectedRectangle.setHeight(height);
            }
        }
    }

    public void decrease(String string) {
        if (string == "circle") {
            if (selectedCircle != null) {
                radius -= 10;
                if (radius < 10) {
                    radius = 10;
                }
                selectedCircle.setRadius(radius);
            }
        };
        if (string == "rectangle") {

            if (selectedRectangle != null) {
                width -= 10;
                if (width < 10) {
                    width = 10;
                }
                selectedRectangle.setWidth(width);
            }

            if (selectedRectangle != null) {
                height -= 10;
                if (height < 10) {
                    height = 10;
                }
                selectedRectangle.setHeight(height);
            }
        }

    }
}

class PaintCircle extends GeometryModifications {

    public double offsetX;
    public double offsetY;

    public PaintCircle() {
    }

    public void createCircle(Pane root) {
        Circle newCircle = new Circle(100, 100, radius);
        newCircle.setFill(Color.BLUE);
        newCircle.setStroke(Color.BLACK);

        newCircle.setOnMousePressed(event -> {
            selectedCircle = newCircle;
            selectedCircle.setFill(Color.YELLOW);
            offsetX = event.getSceneX() - newCircle.getCenterX();
            offsetY = event.getSceneY() - newCircle.getCenterY();
            root.setCursor(Cursor.CLOSED_HAND);
            newCircle.setCursor(Cursor.H_RESIZE);
        });

        newCircle.setOnMouseEntered(event -> {
            newCircle.setCursor(Cursor.H_RESIZE); // Change cursor to "resize" when hovering over the line's stroke
        });

        newCircle.setOnMouseExited(event -> {
            newCircle.setCursor(Cursor.DEFAULT); // Reset cursor when leaving the line
        });

        newCircle.setOnMouseDragged(event -> {
            if (selectedCircle != null) {
                double newX = event.getSceneX() - offsetX;
                double newY = event.getSceneY() - offsetY;
                selectedCircle.setCenterX(newX);
                selectedCircle.setCenterY(newY);
            }
            newCircle.setCursor(Cursor.MOVE);
        });

        newCircle.setOnMouseReleased(event -> {
            if (selectedCircle != null) {
                root.setCursor(Cursor.DEFAULT);
            }
        });

        newCircle.setOnMouseClicked(event -> {
            selectedCircle = newCircle;
            selectedCircle.setFill(Color.GREEN);
            event.consume();
        });

        root.getChildren().addAll(newCircle);
    }

}

class PaintRectangle extends GeometryModifications {

    public boolean resizing = false;

    public double offsetX;
    public double offsetY;

    public PaintRectangle() {
    }

    public void createRectangle(Pane root) {

       
        Rectangle newRectangle = new Rectangle(100, 100, width, height);
        newRectangle.setFill(Color.BLUE);
        newRectangle.setStroke(Color.BLACK);

        newRectangle.setOnMousePressed(event -> {
            selectedRectangle = newRectangle;
            selectedRectangle.setFill(Color.YELLOW);
            offsetX = event.getSceneX() - newRectangle.getX();
            offsetY = event.getSceneY() - newRectangle.getY();
            root.setCursor(Cursor.CLOSED_HAND);
            newRectangle.setCursor(Cursor.H_RESIZE);
        });

        newRectangle.setOnMouseDragged(event -> {
            if (selectedRectangle != null) {
                double newX = event.getSceneX() - offsetX;
                double newY = event.getSceneY() - offsetY;
                selectedRectangle.setX(newX);
                selectedRectangle.setY(newY);
            }
            newRectangle.setCursor(Cursor.MOVE);
        });

        newRectangle.setOnMouseReleased(event -> {
            if (selectedRectangle != null) {
                root.setCursor(Cursor.DEFAULT);
            }
        });

        newRectangle.setOnMouseClicked(event -> {
            selectedRectangle = newRectangle;
            selectedRectangle.setFill(Color.GREEN);
            event.consume();
        });

        root.getChildren().addAll(newRectangle);
    }

}

class PaintLine extends GeometryModifications {

    private Line selectedLine;
    private double offsetX;
    private double offsetY;
    private boolean dragging = false;

    public PaintLine() {
    }

    public void createLine(Pane root) {
        Line newLine = new Line(50, 50, 150, 150);
        newLine.setStrokeWidth(10);

        newLine.setOnMousePressed(event -> {
            selectedLine = newLine;
            offsetX = event.getSceneX() - newLine.getStartX();
            offsetY = event.getSceneY() - newLine.getStartY();
            dragging = true;
            root.setCursor(Cursor.CLOSED_HAND);

            newLine.setCursor(Cursor.H_RESIZE); // Change the cursor to hand when hovering over the line.
        });

        newLine.setOnMouseDragged(event -> {
            if (dragging && selectedLine != null) {
                double newX = event.getSceneX() - offsetX;
                double newY = event.getSceneY() - offsetY;
                double deltaX = newX - selectedLine.getStartX();
                double deltaY = newY - selectedLine.getStartY();
                selectedLine.setStartX(newX);
                selectedLine.setStartY(newY);
                selectedLine.setEndX(selectedLine.getEndX() + deltaX);
                selectedLine.setEndY(selectedLine.getEndY() + deltaY);
            }
            newLine.setCursor(Cursor.MOVE);
        });

        newLine.setOnMouseReleased(event -> {
            dragging = false;
            root.setCursor(Cursor.DEFAULT);
        });

        root.getChildren().add(newLine);
    }

}
