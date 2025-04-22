package org.example.g12_storybasedgame.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        // Ändra texten eller ladda ny scen
        welcomeText.setText("Loading...");
        // Här behöver vi logik för att byta till inloggningssidan
    }
}