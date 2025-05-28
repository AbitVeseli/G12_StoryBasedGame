package org.example.g12_storybasedgame.view.homescreen.visit;

import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.g12_storybasedgame.view.homescreen.Homescreen;

import java.io.InputStream;
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
        // Top bar with back button on left
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(15));
        topBar.setAlignment(Pos.TOP_LEFT); // Changed to TOP_LEFT
        topBar.setStyle("-fx-background-color: rgba(173, 216, 230, 0.7);");

        // Back button (replacing Exit)
        Button backButton = new Button("← Back");
        backButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 16px;");
        backButton.setOnAction(e -> returnToCharacterSelection());

        // Right-aligned buttons container
        HBox rightButtons = new HBox(20);
        rightButtons.setAlignment(Pos.TOP_RIGHT);

        // Gallery button
        Button galleryButton = new Button("Gallery");
        galleryButton.setStyle("-fx-background-color: magenta; -fx-text-fill: white;");
        galleryButton.setOnAction(e -> showGallery());

        // Info button
        Button infoButton = new Button("Info");
        infoButton.setStyle("-fx-background-color: magenta; -fx-text-fill: white;");
        infoButton.setOnAction(e -> showInfo());

        rightButtons.getChildren().addAll(galleryButton, infoButton);

        // Add both to top bar with spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topBar.getChildren().addAll(backButton, spacer, rightButtons);
        this.setTop(topBar);

        // Center content - love interest image
        try {
            InputStream imageStream = getClass().getResourceAsStream("/" + loveInterestName + "_main.png");
            if (imageStream == null) {
                System.err.println("Image not found: /" + loveInterestName + "_main.png");
                // Add fallback content
                this.setCenter(new Text("Image not found: " + loveInterestName));
            } else {
                ImageView liImage = new ImageView(new Image(imageStream));
                liImage.setPreserveRatio(true);
                liImage.setFitWidth(400);
                this.setCenter(liImage);
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
            this.setCenter(new Text("Error loading image"));
        }
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


    private void returnToCharacterSelection() {
        // Get the current stage
        StackPane root = (StackPane) this.getScene().getRoot();

        if (root.getChildren().size() > 1) {
            root.getChildren().remove(root.getChildren().size() - 1);
        }

    }
}