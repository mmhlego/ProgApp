package model;

import java.io.File;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class StageManager {
    static Stage CurrentStage;

    public static void setCurrentStage(Stage stage) {
        CurrentStage = stage;
    }

    public static Object OpenPage(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(new File(path).toURI().toURL());
            Parent parent = loader.load();
            OpenPage(parent);
            return loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void OpenPage(Parent parent) {
        Scene scene = new Scene(parent);
        CurrentStage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
    }
}
