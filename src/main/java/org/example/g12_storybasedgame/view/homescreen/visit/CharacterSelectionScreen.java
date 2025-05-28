package org.example.g12_storybasedgame.view.homescreen.visit;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import java.io.InputStream;

public class CharacterSelectionScreen extends BorderPane {
    private final Stage primaryStage;

    public CharacterSelectionScreen(Stage primaryStage) {
        this.primaryStage = primaryStage;
        setupUI();
    }

    private void setupUI() {
//        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.9);");

        // Back button
        Button backButton = new Button("← Back");
        backButton.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-background-color: transparent;");
        backButton.setOnAction(e -> returnToHomeScreen());

        HBox topBar = new HBox(backButton);
        topBar.setPadding(new Insets(15));
        this.setTop(topBar);

        // Main content
        VBox content = new VBox(30);
        content.setPadding(new Insets(50));
        content.setAlignment(Pos.CENTER);

        Label title = new Label("Choose Your Love Interest");
        title.setStyle("-fx-font-size: 32px; -fx-text-fill: white; -fx-font-weight: bold;");

        // Love interest grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(40);
        grid.setVgap(40);
        grid.setPadding(new Insets(20));

        String[] loveInterests = {"LI1", "LI2", "LI3", "LI4"};
        for (int i = 0; i < loveInterests.length; i++) {
            String li = loveInterests[i];
            VBox characterCard = createCharacterCard(li);
            grid.add(characterCard, i % 2, i / 2);
        }

        content.getChildren().addAll(title, grid);
        this.setCenter(content);
    }

    private VBox createCharacterCard(String character) {
        VBox characterCard = new VBox(10);
        characterCard.setAlignment(Pos.CENTER);
        characterCard.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2);" +
                "-fx-background-radius: 15;" +
                "-fx-padding: 20;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");

        // Character image
        StackPane imageContainer = new StackPane();
        imageContainer.setStyle("-fx-background-radius: 50%;" +
                "-fx-border-color: white;" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 50%;");

        ImageView imageView = new ImageView();
        try {
            Image image = new Image(getClass().getResourceAsStream("/" + character + ".png"));
            imageView.setImage(image);
            imageView.setFitWidth(150);
            imageView.setFitHeight(150);
            imageView.setPreserveRatio(true);
            imageView.setClip(new Circle(75, 75, 75));
        } catch (Exception e) {
            imageView.setImage(null);
            Label placeholder = new Label(character);
            placeholder.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
            imageContainer.getChildren().add(placeholder);
        }

        imageContainer.getChildren().add(imageView);

        // Character name
        Label nameLabel = new Label(character);
        nameLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white; -fx-font-weight: bold;");

        characterCard.getChildren().addAll(imageContainer, nameLabel);

        // Add click handler
        characterCard.setOnMouseClicked(e -> showCharacterProfile(character));
        characterCard.setCursor(Cursor.HAND);

        // Add hover effect
        characterCard.setOnMouseEntered(e -> {
            characterCard.setScaleX(1.05);
            characterCard.setScaleY(1.05);
            characterCard.setStyle(characterCard.getStyle() +
                    "-fx-background-color: rgba(255, 255, 255, 0.2);");
        });

        characterCard.setOnMouseExited(e -> {
            characterCard.setScaleX(1.0);
            characterCard.setScaleY(1.0);
            characterCard.setStyle(characterCard.getStyle()
                    .replace("-fx-background-color: rgba(255, 255, 255, 0.2);",
                            "-fx-background-color: rgba(255, 255, 255, 0.1);"));
        });

        return characterCard;
    }

    private void showCharacterProfile(String character) {
        LoveInterestPage profileScreen = new LoveInterestPage(primaryStage, character);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), profileScreen);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        StackPane root = (StackPane) primaryStage.getScene().getRoot();
        root.getChildren().add(profileScreen);
        fadeIn.play();
    }

    private void returnToHomeScreen() {
        StackPane root = (StackPane) primaryStage.getScene().getRoot();
        if (root.getChildren().size() > 1) {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(200), root.getChildren().get(1));
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(e -> root.getChildren().remove(1));
            fadeOut.play();
        }
    }
}