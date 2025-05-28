package org.example.g12_storybasedgame.view.homescreen.visit;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.g12_storybasedgame.view.homescreen.Homescreen;

import java.net.URL;

public class LoveInterestPage extends BorderPane {
    private String loveInterestName;
    private Stage primaryStage;

    public LoveInterestPage(Stage primaryStage, String loveInterestName) {
        this.primaryStage = primaryStage;
        this.loveInterestName = loveInterestName;

        System.out.println("Attempting to load: /" + loveInterestName + "_main.png");
        URL resourceUrl = getClass().getResource("/" + loveInterestName + "_main.png");
        System.out.println("Resource URL: " + (resourceUrl != null ? resourceUrl.toString() : "NULL"));

        setupUI();
    }

    private void setupUI() {
        // Top bar with buttons
        HBox topBar = new HBox(20);
        topBar.setPadding(new Insets(15));
        topBar.setAlignment(Pos.TOP_RIGHT);
        topBar.setStyle("-fx-background-color: rgba(173, 216, 230, 0.7);");

        // Gallery button
        Button galleryButton = new Button("Gallery");
        galleryButton.setStyle("-fx-background-color: magenta; -fx-text-fill: white;");
        galleryButton.setOnAction(e -> showGallery());

        // Info button
        Button infoButton = new Button("Info");
        infoButton.setStyle("-fx-background-color: magenta; -fx-text-fill: white;");
        infoButton.setOnAction(e -> showInfo());

        // Exit button
        Button exitButton = new Button("Exit");
        exitButton.setStyle("-fx-background-color: magenta; -fx-text-fill: white;");
        exitButton.setOnAction(e -> returnToHome());

        topBar.getChildren().addAll(galleryButton, infoButton, exitButton);
        this.setTop(topBar);

        // Center content - love interest image
        ImageView liImage = new ImageView(new Image(getClass().getResourceAsStream("/" + loveInterestName + "_main.png")));
        liImage.setPreserveRatio(true);
        liImage.setFitWidth(400);
        this.setCenter(liImage);
    }

    private void showGallery() {
        // Implement gallery functionality here
        System.out.println("Showing gallery for " + loveInterestName);
    }

    private void showInfo() {
        // Implement info popup here
        System.out.println("Showing info for " + loveInterestName);

        // You can create a popup similar to how you did in Homescreen
        // with character-specific information
    }

    private void returnToHome() {
        Homescreen home = new Homescreen();
        home.start(primaryStage);
    }
}