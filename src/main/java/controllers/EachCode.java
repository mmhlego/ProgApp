package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.UsefulCode;

public class EachCode {
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

    UsefulCode usefulCode;

    public void setUsefulCode(UsefulCode uc) {
        usefulCode = uc;
    }
}
