package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import model.CodeType;
import model.Language;
import model.UsefulCode;
import stage.AlertType;
import stage.Overlay;

public class CreateCode implements Initializable {
    @FXML
    private JFXComboBox<Language> LanguageCombo;
    @FXML
    private JFXComboBox<CodeType> CodeTypeCombo;
    @FXML
    private TextArea CodeTA;
    @FXML
    private TextArea DescriptionTA;
    @FXML
    private TextField TagTF;
    @FXML
    private JFXButton AddTagBTN;
    @FXML
    private FlowPane TagsPane;
    @FXML
    private TextArea InputTA;
    @FXML
    private TextArea OutputTA;
    @FXML
    private Button CancelBTN;
    @FXML
    private Button CreateBTN;

    ArrayList<String> tags = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LanguageCombo.getItems().addAll(Language.values());
        CodeTypeCombo.getItems().addAll(CodeType.values());

        AddTagBTN.setOnMouseClicked(e -> {
            if (TagTF.getText().length() > 0) {
                tags.add(TagTF.getText());
                TagTF.setText("");
                ShowTags();
            }
        });

        CreateBTN.setOnMouseClicked(e -> Check());
        CancelBTN.setOnMouseClicked(e -> Overlay.getOverlayStage().close());
    }

    private void ShowTags() {
        TagsPane.getChildren().clear();
        for (String tag : tags) {
            Label label = new Label(tag);
            label.setMinHeight(22.0);
            label.setMaxHeight(22.0);
            label.setPadding(new Insets(0, 5, 0, 5));
            label.setAlignment(Pos.CENTER);
            label.setStyle("-fx-background-color:#4F5D75; -fx-background-radius:5; -fx-text-fill:white;");
            label.setCursor(Cursor.HAND);
            label.setOnMouseClicked(e -> UsefulCodeHeader.Search(tag));
            TagsPane.getChildren().add(label);
        }
    }

    private void Check() {
        Language language = LanguageCombo.getSelectionModel().getSelectedItem();
        CodeType type = CodeTypeCombo.getSelectionModel().getSelectedItem();
        String code = CodeTA.getText();
        String description = DescriptionTA.getText();
        String input = InputTA.getText();
        String output = OutputTA.getText();

        if (language == null) {
            Overlay.ShowAlert("Language not selected.", AlertType.Error);
        } else if (type == null) {
            Overlay.ShowAlert("Code type not selected.", AlertType.Error);
        } else if (code.length() == 0) {
            Overlay.ShowAlert("Please provide a code to be saved.", AlertType.Error);
        } else if (description.length() == 0) {
            Overlay.ShowAlert("Please provide a description.", AlertType.Error);
        } else if (tags.size() == 0) {
            Overlay.ShowAlert("Please add at least one tag for this code.", AlertType.Error);
        } else {
            UsefulCode usefulCode = new UsefulCode(code, description, input, output, language, type, tags);
            if (usefulCode.InsertRecord()) {
                UsefulCodeHeader.ShowAll();
                Overlay.ShowAlert("Code added successfully.", AlertType.Success);
                Overlay.getOverlayStage().close();
            } else {
                Overlay.ShowAlert("An error accured.", AlertType.Error);
            }
        }
    }
}
