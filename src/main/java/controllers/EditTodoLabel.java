package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import model.Todo;
import model.TodoLabels;
import stage.AlertType;
import stage.Overlay;

public class EditTodoLabel implements Initializable {
    @FXML
    private JFXComboBox<TodoLabels> LabelsCombo;
    @FXML
    private Button CancelBTN;
    @FXML
    private Button SaveBTN;

    static Todo todo;

    public static void setTodo(Todo t) {
        todo = t;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LabelsCombo.getItems().addAll(TodoLabels.values());
        LabelsCombo.getSelectionModel().select(todo.getLabel());

        CancelBTN.setOnMouseClicked(e -> Overlay.getOverlayStage().close());

        SaveBTN.setOnMouseClicked(e -> {
            todo.setLabel(LabelsCombo.getSelectionModel().getSelectedItem());
            todo.UpdateRecord();
            Overlay.getOverlayStage().close();
            Overlay.ShowAlert("Label updated successfully.", AlertType.Success);
            TodoListHeader.Search("");
        });
    }
}
