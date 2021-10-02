package controllers;

import stage.StageManager;

public class PageController {
    public static void OpenFirstPage() {
        StageManager.OpenFirstPage();
    }

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

    public static void OpenTodoList() {
        tools.Window.setStageSizes(850, 700);
        stage.StageManager.OpenListPage();
        stage.StageManager.SetHeader("resources/headers/TodoListHeader.fxml");
        stage.StageManager.SetTitle("MMH Todo List");
    }
}
