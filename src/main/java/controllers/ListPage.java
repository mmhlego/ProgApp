package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import stage.StageManager;

public class ListPage implements Initializable {
    @FXML
    private VBox root;
    @FXML
    private Label TitleLBL;
    @FXML
    private HBox TitleBar;
    @FXML
    private Button HomeBTN;
    @FXML
    private Button MinimizeBTN;
    @FXML
    private Button MaximizeBTN;
    @FXML
    private Button CloseBTN;
    @FXML
    private HBox HeaderBox;
    @FXML
    private ScrollPane ScrollPane;
    @FXML
    private VBox ScrollBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tools.Window.MakeStageMovable(StageManager.getCurrentStage(), TitleBar);
        HomeBTN.setOnMouseClicked(e -> StageManager.OpenFirstPage());
        HomeBTN.setTooltip(new Tooltip("Home"));

        MinimizeBTN.setOnMouseClicked(e -> StageManager.getCurrentStage().setIconified(true));
        MinimizeBTN.setTooltip(new Tooltip("Minimize"));

        MaximizeBTN.setOnMouseClicked(e -> tools.Window.ToggleMaximizeStage(StageManager.getCurrentStage()));
        MaximizeBTN.setTooltip(new Tooltip("Maximize"));

        CloseBTN.setOnMouseClicked(e -> System.exit(0));
        CloseBTN.setTooltip(new Tooltip("Close"));

        ScrollPane.prefHeightProperty().bind(root.heightProperty().subtract(120));
        ScrollBox.prefWidthProperty().bind(ScrollPane.widthProperty());
    }

    public void setHeaderBox(HBox headerBox) {
        HeaderBox = headerBox;
    }

    public HBox getHeaderBox() {
        return HeaderBox;
    }

    public Label getTitleLBL() {
        return TitleLBL;
    }

    public VBox getScrollBox() {
        return ScrollBox;
    }
}
