package org.example.g12_storybasedgame.view.homescreen.visit;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class VisitButton extends StackPane {
    private final Stage primaryStage;

    public VisitButton(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showCharacterSelection();
    }

    private void showCharacterSelection() {
        CharacterSelectionScreen selectionScreen = new CharacterSelectionScreen(primaryStage);
        StackPane root = (StackPane) primaryStage.getScene().getRoot();
        root.getChildren().add(selectionScreen);
    }
}