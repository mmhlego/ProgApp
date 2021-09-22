package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class FirstPage implements Initializable {
    @FXML
    private VBox ProjectManagerBTN;
    @FXML
    private VBox TodoBTN;
    @FXML
    private VBox UsefulCodesBTN;
    @FXML
    private VBox SqlBTN;
    @FXML
    private Label NameLBL;
    @FXML
    private Circle CloseBTN;
    @FXML
    private Circle MaximizeBTN;
    @FXML
    private Circle MinimizeBTN;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TodoBTN.setOnMouseEntered(e -> {
            KeyValue key = new KeyValue(TodoBTN.styleProperty(), "-fx-background:transparent;");
            Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.millis(1000), key));
            timeline.play();
        });
    }
}
