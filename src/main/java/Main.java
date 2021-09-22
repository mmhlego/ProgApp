import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.StageManager;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
        System.exit(0);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        StageManager.setCurrentStage(primaryStage);
        StageManager.OpenPage("resources/pages/FirstPage.fxml");
        primaryStage.show();
    }
}
