package controllers;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.UsefulCode;
import stage.AlertType;
import stage.Overlay;
import stage.StageManager;
import tools.Beautifier;

public class EachCode {
    @FXML
    private HBox MainHB;
    @FXML
    private ImageView LanguageIMG;
    @FXML
    private Label CodeTypeLBL;
    @FXML
    private TextArea DescriptionArea;
    @FXML
    private FlowPane TagsBox;
    @FXML
    private ImageView MoreBTN;
    @FXML
    private VBox MoreBox;
    @FXML
    private HBox IOBox;
    @FXML
    private VBox InBox;
    @FXML
    private VBox OutBox;
    @FXML
    private Button DeleteCodeBTN;

    public static final int duration = 400;
    UsefulCode usefulCode;
    boolean Open = false;
    double MoreBoxHeight = -1;

    public void setUsefulCode(UsefulCode uc) {
        usefulCode = uc;

        LanguageIMG.setImage(usefulCode.getLanguage().getImage());
        CodeTypeLBL.setText(usefulCode.getType().toString());
        DescriptionArea.setText(usefulCode.getDescription());

        DeleteCodeBTN.setOnMouseClicked(e -> {
            usefulCode.DeleteRecord();
            Overlay.ShowAlert("Code deleted successfully", AlertType.Success);
            UsefulCodeHeader.ShowAll();
        });

        for (String tag : usefulCode.getTags()) {
            Label label = new Label(tag);
            label.setMinHeight(22.0);
            label.setMaxHeight(22.0);
            label.setPadding(new Insets(0, 5, 0, 5));
            label.setAlignment(Pos.CENTER);
            label.setStyle("-fx-background-color:#4F5D75; -fx-background-radius:5; -fx-text-fill:white;");
            label.setCursor(Cursor.HAND);
            label.setOnMouseClicked(e -> UsefulCodeHeader.Search(tag));
            TagsBox.getChildren().add(label);
        }

        MoreBTN.setOnMouseClicked(e -> ToggleAnimation());

        BindSizes();
        Details();

        MoreBoxHeight = MoreBox.prefHeight(-1);

        MoreBox.setMinHeight(0);
        MoreBox.setMaxHeight(0);
        MoreBox.setScaleY(0);
    }

    private void ToggleAnimation() {
        new Timeline(new KeyFrame(Duration.millis(duration),
                new KeyValue(MoreBTN.rotateProperty(), (Open ? 0 : 3 * 180), Interpolator.EASE_BOTH))).play();

        new Timeline(new KeyFrame(Duration.millis(duration),
                new KeyValue(MoreBox.scaleYProperty(), (Open ? 0 : 1), Interpolator.EASE_BOTH))).play();

        new Timeline(new KeyFrame(Duration.millis(duration),
                new KeyValue(MoreBox.minHeightProperty(), (Open ? 0 : MoreBoxHeight), Interpolator.EASE_BOTH))).play();

        new Timeline(new KeyFrame(Duration.millis(duration),
                new KeyValue(MoreBox.maxHeightProperty(), (Open ? 0 : MoreBoxHeight), Interpolator.EASE_BOTH))).play();

        Open = !Open;
    }

    private void BindSizes() {
        MainHB.prefWidthProperty().bind(StageManager.getCurrentStage().widthProperty().subtract(50));
        DescriptionArea.prefWidthProperty().bind(MainHB.widthProperty().subtract(450).multiply(0.375));
        TagsBox.prefWidthProperty().bind(MainHB.widthProperty().subtract(450).multiply(0.625));
        MoreBox.prefWidthProperty().bind(MainHB.widthProperty().subtract(50));
        InBox.prefWidthProperty().bind(MoreBox.widthProperty().subtract(75).divide(2));
        OutBox.prefWidthProperty().bind(MoreBox.widthProperty().subtract(75).divide(2));
    }

    private void Details() {
        Beautifier code = new Beautifier(usefulCode.getCode());
        code.Beautify();
        MoreBox.getChildren().add(0, code);
        code.prefWidthProperty().bind(MoreBox.widthProperty().subtract(50));

        if (usefulCode.getInput().equals("") && usefulCode.getOutput().equals("")) {
            MoreBox.getChildren().remove(1);
        } else {
            if (!usefulCode.getInput().equals("")) {
                Beautifier in = new Beautifier(usefulCode.getInput());
                in.Simple("> Input:\n");
                InBox.getChildren().add(in);
                in.prefWidthProperty().bind(MoreBox.widthProperty().subtract(75).divide(2));
            }

            if (!usefulCode.getInput().equals("")) {
                Beautifier out = new Beautifier(usefulCode.getOutput());
                out.Simple("> Output:\n");
                OutBox.getChildren().add(out);
                out.prefWidthProperty().bind(MoreBox.widthProperty().subtract(75).divide(2));
            }
        }
    }
}
