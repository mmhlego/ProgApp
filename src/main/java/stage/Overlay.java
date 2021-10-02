package stage;

import java.io.File;

import com.kieferlam.javafxblur.Blur;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Overlay {
    static Stage OverlayStage;
    static Stage AlertStage;

    public static void ShowAlert(String message, AlertType type) {
        ShowAlert(message, type, () -> {
        });
    }

    public static void ShowAlert(String message, AlertType type, Runnable runLater) {
        try {
            FXMLLoader loader = new FXMLLoader(new File("resources/components/Alert.fxml").toURI().toURL());
            VBox box = loader.load();
            OpenOverlay(true, box);
            ((ImageView) box.getChildren().get(0)).setImage(type.getImage());
            ((Label) box.getChildren().get(1)).setText(message);
            ((Button) box.getChildren().get(2)).setOnMouseClicked(e -> {
                runLater.run();
                AlertStage.close();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void OpenOverlay(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(new File(path).toURI().toURL());
            OpenOverlay(false, (Parent) loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void OpenOverlay(boolean IsAlert, Parent parent) {
        VBox box = new VBox(parent);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color:rgba(255,255,255,0.2);");
        Scene scene = new Scene(box);
        scene.setFill(Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setAlwaysOnTop(true);
        stage.setX(StageManager.CurrentStage.getX());
        stage.setY(StageManager.CurrentStage.getY());
        stage.setWidth(StageManager.CurrentStage.getWidth());
        stage.setHeight(StageManager.CurrentStage.getHeight());
        stage.show();
        Blur.applyBlur(stage, Blur.BLUR_BEHIND);

        if (IsAlert) {
            AlertStage = stage;
        } else {
            OverlayStage = stage;
        }
    }

    public static Stage getOverlayStage() {
        return OverlayStage;
    }

    public static Stage getAlertStage() {
        return AlertStage;
    }
}
