package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import stage.AlertType;
import stage.StageManager;

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
    private Label TitleLBL;
    @FXML
    private Button CloseBTN;
    @FXML
    private Button MaximizeBTN;
    @FXML
    private Button MinimizeBTN;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AddAnimation(ProjectManagerBTN, "#00A3FFd0", "resources/icons/start/code-blue.png",
                "resources/icons/start/code-white.png", () -> PageController.OpenProjectManager());

        AddAnimation(UsefulCodesBTN, "#FF8A00d0", "resources/icons/start/useful-orange.png",
                "resources/icons/start/useful-white.png", () -> PageController.OpenUsefulCodeManager());

        AddAnimation(TodoBTN, "#FF0000d0", "resources/icons/start/todo-red.png", "resources/icons/start/todo-white.png",
                () -> PageController.OpenTodoList());

        AddAnimation(SqlBTN, "#00DC16d0", "resources/icons/start/database-green.png",
                "resources/icons/start/database-white.png",
                () -> stage.Overlay.ShowAlert("Under Developement ...  :)", AlertType.Developing));

        AddWindowControllers();
    }

    void AddAnimation(VBox button, String color, String ColoredImage, String TransImage, Runnable RunLater) {
        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-background-color:transparent; -fx-border-color:" + color
                    + "; -fx-border-radius:10; -fx-border-width:2.5;");
            try {
                ((ImageView) button.getChildren().get(0))
                        .setImage(new Image(new FileInputStream(new File(ColoredImage))));
            } catch (FileNotFoundException e1) {
            }
            ((Label) button.getChildren().get(1)).setTextFill(Paint.valueOf(color));
        });

        button.setOnMouseExited(e -> {
            button.setStyle(
                    "-fx-background-color:" + color + "; -fx-border-color:transparent; -fx-background-radius:10;");
            try {
                ((ImageView) button.getChildren().get(0))
                        .setImage(new Image(new FileInputStream(new File(TransImage))));
            } catch (FileNotFoundException e1) {
            }
            ((Label) button.getChildren().get(1)).setTextFill(Paint.valueOf("#fff"));
        });

        button.setOnMouseClicked(e -> RunLater.run());
    }

    private void AddWindowControllers() {
        tools.Window.MakeStageMovable(StageManager.getCurrentStage(), TitleLBL);

        MinimizeBTN.setOnMouseClicked(e -> StageManager.getCurrentStage().setIconified(true));
        MinimizeBTN.setTooltip(new Tooltip("Minimize"));

        MaximizeBTN.setStyle("-fx-background-radius:10; -fx-background-color:#aaa;");
        MaximizeBTN.setCursor(Cursor.OPEN_HAND);

        CloseBTN.setOnMouseClicked(e -> System.exit(0));
        CloseBTN.setTooltip(new Tooltip("Close"));
    }

    public Label getHeaderLBL() {
        return TitleLBL;
    }
}
