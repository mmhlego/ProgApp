import java.io.File;
import java.io.IOException;

import com.kieferlam.javafxblur.Blur;

import javafx.application.Application;
import javafx.stage.Stage;
import stage.StageManager;

public class Main extends Application {
    public static void main(String[] args) {
        Blur.loadBlurLibrary();
        launch(args);

        // Connect();
        // Todo t = new Todo("Sample", TodoLabels.Todo);
        // t.InsertRecord();

        // System.out.println(Arrays.toString(database.Selector.Select(Tables.Todos).ToArrayList().toArray()));

        // System.out.println(Web.IsGitRepository("https://github.com/mmhlego/HATTube.git"));
        // System.out.println(Web.IsGitRepository("http://github.com/mmhlego/HATTube.git"));

        System.exit(0);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StageManager.setCurrentStage(primaryStage, "ProgApp - Home");
        StageManager.OpenFirstPage();
        if (!Connect())
            System.out.println("no");
    }

    private static boolean Connect() {
        try {
            File DBFile = new File("resources/database/Database.db");
            if (!DBFile.exists()) {
                DBFile.createNewFile();
                //TODO create all tables
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return database.Connector.Connect();
    }
}