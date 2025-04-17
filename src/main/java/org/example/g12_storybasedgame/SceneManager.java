package org.example.g12_storybasedgame;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
    private static Stage primaryStage;

    public static void setStage(Stage stage) {
        primaryStage = stage;
        primaryStage.setFullScreenExitHint("");
    }

    public static void switchToScene(Scene scene) {
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
    }
}