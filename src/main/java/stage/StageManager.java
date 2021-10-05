package stage;

import java.io.File;
import java.io.FileInputStream;

import com.kieferlam.javafxblur.Blur;

import controllers.FirstPage;
import controllers.ListPage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StageManager {
    static Stage CurrentStage;
    static Label Title;
    static HBox Header;
    static VBox ListBox;

    public static void setCurrentStage(Stage stage, String title) {
        CurrentStage = stage;
        CurrentStage.setTitle(title);
        CurrentStage.initStyle(StageStyle.TRANSPARENT);
        CurrentStage.show();
        Blur.applyBlur(CurrentStage, Blur.BLUR_BEHIND);
        try {
            CurrentStage.getIcons().add(new Image(new FileInputStream(new File("resources/icons/Logo.png"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Stage getCurrentStage() {
        return CurrentStage;
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
        // CurrentStage.show();
    }

    public static void OpenFirstPage() {
        FirstPage controller = (FirstPage) StageManager.OpenPage("resources/pages/FirstPage.fxml");
        Title = controller.getHeaderLBL();
        tools.Window.setStageSizes(725, 550);
        tools.Window.NormalizeStage(CurrentStage, true);
    }

    public static void OpenListPage() {
        ListPage controller = (ListPage) StageManager.OpenPage("resources/pages/ListPage.fxml");
        Title = controller.getTitleLBL();
        Header = controller.getHeaderBox();
        ListBox = controller.getScrollBox();
    }

    public static void SetTitle(String title) {
        Title.setText(title);
    }

    public static void SetHeader(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(new File(path).toURI().toURL());
            HBox header = loader.load();

            VBox parent = (VBox) Header.getParent();
            parent.getChildren().remove(2);
            parent.getChildren().add(2, header);
            Header = header;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static VBox getListBox() {
        return ListBox;
    }

    public static HBox getNoResults() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("resources/components/NoResults.fxml").toURI().toURL());
            return (HBox) loader.load();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
