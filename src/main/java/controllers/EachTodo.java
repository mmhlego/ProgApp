package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import model.Todo;
import model.TodoLabels;
import stage.AlertType;
import stage.Overlay;

public class EachTodo {
    @FXML
    private HBox RootHB;
    @FXML
    private Label TextLBL;
    @FXML
    private Label LabelLBL;
    @FXML
    private Button ChangeLabelBTN;
    @FXML
    private Button FinishBTN;
    @FXML
    private Button DeleteBTN;

    Todo todo;

    public void setTodo(Todo todo) {
        this.todo = todo;
        TextLBL.setText(todo.getText());
        LabelLBL.setText(todo.getLabel().getText());

        FinishBTN.setOnMouseClicked(e -> {
            todo.setLabel(TodoLabels.Finished);
            todo.UpdateRecord();
            TodoListHeader.Search("");
        });

        DeleteBTN.setOnMouseClicked(e -> {
            todo.DeleteRecord();
            Overlay.ShowAlert("Task removed successfully.", AlertType.Success);
            TodoListHeader.Search("");
        });

        RootHB.setStyle(RootHB.getStyle().replaceAll("#ffffff", todo.getLabel().getColor()));
        RootHB.setStyle(RootHB.getStyle().replaceAll("#cccccc", todo.getLabel().getColor()));

        if (todo.getLabel().equals(TodoLabels.Finished)) {
            FinishBTN.setVisible(false);
        }
        TextLBL.prefWidthProperty().bind(RootHB.widthProperty().subtract(325));

        ChangeLabelBTN.setOnMouseClicked(e -> {
            EditTodoLabel.setTodo(todo);
            Overlay.OpenOverlay("resources/components/EditTodoLabel.fxml");
        });

        FinishBTN.setTooltip(new Tooltip("Mark task as finished"));
        ChangeLabelBTN.setTooltip(new Tooltip("Change task label"));
        DeleteBTN.setTooltip(new Tooltip("Delete task"));
    }
}
