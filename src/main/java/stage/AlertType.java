package stage;

import java.io.File;
import java.io.FileInputStream;

import javafx.scene.image.Image;

public enum AlertType {
    Success("Success.gif"), Warning("Warning.gif"), Error("Error.png"), Developing("Developing.gif");

    String ImagePath;

    AlertType(String name) {
        ImagePath = "resources/icons/alert/" + name;
    }

    public Image getImage() {
        try {
            return new Image(new FileInputStream(new File(ImagePath)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
