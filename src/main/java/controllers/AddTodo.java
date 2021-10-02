package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import model.Todo;
import model.TodoLabels;
import stage.AlertType;
import stage.Overlay;

public class AddTodo implements Initializable {
    @FXML
    private JFXTextField TaskTextTF;
    @FXML
    private JFXComboBox<TodoLabels> LabelsCombo;
    @FXML
    private Button CancelBTN;
    @FXML
    private Button AddBTN;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LabelsCombo.getItems().addAll(TodoLabels.values());
        LabelsCombo.getSelectionModel().select(TodoLabels.Todo);
        CancelBTN.setOnMouseClicked(e -> Overlay.getOverlayStage().close());
        AddBTN.setOnMouseClicked(e -> Check());
    }

    private void Check() {
        if (TaskTextTF.getText().length() == 0) {
            Overlay.ShowAlert("Task text not specified.", AlertType.Error);
        } else {
            Todo todo = new Todo(TaskTextTF.getText(), LabelsCombo.getSelectionModel().getSelectedItem());
            if (todo.InsertRecord()) {
                TodoListHeader.Search("");
                Overlay.ShowAlert("Task added successfully", AlertType.Success);
                Overlay.getOverlayStage().close();
            } else {
                Overlay.ShowAlert("An error accured.", AlertType.Error);
            }
        }
    }
}
