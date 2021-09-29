package tools;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Window {
    static double StageWidth = 725, StageHeight = 550;
    static double dx = 0, dy = 0;

    public static void MakeStageMovable(Stage stage, Node handle) {
        handle.setOnMousePressed(e -> {
            dx = stage.getX() - e.getScreenX();
            dy = stage.getY() - e.getScreenY();
            handle.setCursor(Cursor.CLOSED_HAND);
        });
        handle.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() + dx);
            stage.setY(e.getScreenY() + dy);
        });

        handle.setOnMouseReleased(e -> handle.setCursor(Cursor.OPEN_HAND));

        NormalizeStage(stage, true);
    }

    static double MaxWidth = Screen.getPrimary().getBounds().getWidth();
    static double MaxHeight = Screen.getPrimary().getBounds().getHeight();

    public static void ToggleMaximizeStage(Stage stage) {
        boolean BigSize = stage.getWidth() == MaxWidth && stage.getHeight() == MaxHeight;

        if (BigSize)
            NormalizeStage(stage, true);
        else
            MaximizeStage(stage);
    }

    public static void NormalizeStage(Stage stage, boolean middle) {
        if (middle) {
            stage.setX((MaxWidth - StageWidth) / 2);
            stage.setY((MaxHeight - StageHeight) / 2);
        }
        stage.setWidth(StageWidth);
        stage.setHeight(StageHeight);
    }

    private static void MaximizeStage(Stage stage) {
        stage.setX(0);
        stage.setY(0);
        stage.setWidth(MaxWidth);
        stage.setHeight(MaxHeight);
    }

    public static void setStageSizes(double width, double height) {
        StageWidth = width;
        StageHeight = height;
    }
}
