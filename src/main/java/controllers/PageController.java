package controllers;

public class PageController {
    public static void OpenProjectManager() {
        tools.Window.setStageSizes(850, 700);
        stage.StageManager.OpenListPage();
        stage.StageManager.SetHeader("resources/headers/ProjectManagerHeader.fxml");
        stage.StageManager.SetTitle("MMH Project Manager");
    }

    public static void OpenUsefulCodeManager() {
        tools.Window.setStageSizes(850, 700);
        stage.StageManager.OpenListPage();
        stage.StageManager.SetHeader("resources/headers/UsefulCodeHeader.fxml");
        stage.StageManager.SetTitle("MMH Useful Code Manager");
    }
}
