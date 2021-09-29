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

    public static void ShowAlert(String message, AlertType type) {
        ShowAlert(message, type, () -> {
        });
    }

    public static void ShowAlert(String message, AlertType type, Runnable runLater) {
        try {
            FXMLLoader loader = new FXMLLoader(new File("resources/components/Alert.fxml").toURI().toURL());
            VBox box = loader.load();
            OpenOverlay(box);
            ((ImageView) box.getChildren().get(0)).setImage(type.getImage());
            ((Label) box.getChildren().get(1)).setText(message);
            ((Button) box.getChildren().get(2)).setOnMouseClicked(e -> {
                runLater.run();
                OverlayStage.close();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void OpenOverlay(Parent parent) {
        VBox box = new VBox(parent);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color:rgba(255,255,255,0.2);");
        Scene scene = new Scene(box);
        scene.setFill(Color.TRANSPARENT);
        OverlayStage = new Stage();
        OverlayStage.setScene(scene);
        OverlayStage.initStyle(StageStyle.TRANSPARENT);
        OverlayStage.setAlwaysOnTop(true);
        OverlayStage.setX(StageManager.CurrentStage.getX());
        OverlayStage.setY(StageManager.CurrentStage.getY());
        OverlayStage.setWidth(StageManager.CurrentStage.getWidth());
        OverlayStage.setHeight(StageManager.CurrentStage.getHeight());
        OverlayStage.show();
        Blur.applyBlur(OverlayStage, Blur.BLUR_BEHIND);
    }
}
