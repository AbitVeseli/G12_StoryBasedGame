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
        Button backBtn = new Button("← Back");
        backBtn.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-background-color: transparent;");
        backBtn.setOnAction(e -> returnToHomeScreen());

        HBox topBar = new HBox(backBtn);
        topBar.setPadding(new Insets(15));
        this.setTop(topBar);

        // Main content
            // Skapa en BorderPane som huvudlayout
            BorderPane layout = new BorderPane();

            // Skapa hem-knappen och placera den fast i övre högra hörnet
            Button homeBtn = new Button("Back to homescreen");
            homeBtn.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: #C71585; -fx-background-radius: 10;");
            homeBtn.setPadding(new Insets(8));
            homeBtn.setOnAction(e -> returnToHomeScreen());

            // Placera knappen i en högerjusterad behållare
            HBox homeButtonBox = new HBox(homeBtn);
            homeButtonBox.setAlignment(Pos.TOP_RIGHT);
            homeButtonBox.setPadding(new Insets(0, 30, 0, 0)); // Justerar avstånd från kanterna

            // Skapa titeln och centrera den
            Label title = new Label("Choose Your Love Interest");
            title.setStyle("-fx-font-size: 32px; -fx-text-fill: white; -fx-font-weight: bold;");

            // Centrera titeln oberoende av skärmstorlek
            VBox titleBox = new VBox(title);
            titleBox.setAlignment(Pos.CENTER);
            titleBox.setPadding(new Insets(0, 0, 0, 0));

            // Skapa en vertikal layout som innehåller både hemknappen och titeln
            VBox topContent = new VBox();
            topContent.setAlignment(Pos.TOP_CENTER);
            topContent.getChildren().addAll(homeButtonBox, titleBox);

            // Lägg in titeln och hem-knappen i toppen av BorderPane
            layout.setTop(topContent);

            // Skapa huvudlayout för innehållet
            VBox content = new VBox(20);
            content.setPadding(new Insets(20));
            content.setAlignment(Pos.CENTER);

            // Skapa rutnätet för kärleksintressen
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(20);
            grid.setVgap(20);
            grid.setPadding(new Insets(20));

            String[] loveInterests = {"LI1", "LI2", "LI3", "LI4"};
            for (int i = 0; i < loveInterests.length; i++) {
                String li = loveInterests[i];
                VBox characterCard = createCharacterCard(li);
                grid.add(characterCard, i % 2, i / 2);
            }

            content.getChildren().add(grid);
            layout.setCenter(content);

            // Lägg till huvudlayouten i scenen
            this.setCenter(layout);

    }

    private VBox createCharacterCard(String character) {
        VBox characterCard = new VBox(10);
        characterCard.setAlignment(Pos.CENTER);
        characterCard.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2);" +
                "-fx-background-radius: 15;" +
                "-fx-padding: 20;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");

        // Fixed-size container with proper clipping
        StackPane imageContainer = new StackPane();
        imageContainer.setMinSize(150, 150);
        imageContainer.setMaxSize(150, 150);
        imageContainer.setStyle("-fx-background-color: transparent;");

        // ImageView with perfect circular mask
        ImageView imageView = new ImageView();
        imageView.setFitWidth(130);  // Increased to fill circle better
        imageView.setFitHeight(130);
        imageView.setPreserveRatio(false);  // Force exact dimensions

        // Create circular clip with perfect alignment
        Circle clip = new Circle(75, 75, 65);  // CenterX, CenterY, Radius (matches container center)
        imageView.setClip(clip);

        try {
            // Load image with cropping and scaling
            Image image = new Image(getClass().getResourceAsStream("/" + character + ".png"));
            imageView.setImage(image);

            // Center the image within the circle
            imageView.setX(10);
            imageView.setY(10);
        } catch (Exception e) {
            // Fallback with centered text
            Label placeholder = new Label(character);
            placeholder.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
            imageContainer.getChildren().add(placeholder);
        }

        // White border circle (separate from the clip)
        Circle border = new Circle(75, 75, 65);
        border.setFill(Color.TRANSPARENT);
        border.setStroke(Color.WHITE);
        border.setStrokeWidth(2);

        // Add components to container
        if (imageView.getImage() != null) {
            imageContainer.getChildren().addAll(imageView, border);
        }

        // Character name
        Label nameLabel = new Label(character);
        nameLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white; -fx-font-weight: bold;");

        characterCard.getChildren().addAll(imageContainer, nameLabel);

        // Click handling - now on the whole card
        characterCard.setOnMouseClicked(e -> showCharacterProfile(character));
        characterCard.setCursor(Cursor.HAND);

        // Hover effects
        characterCard.setOnMouseEntered(e -> {
            characterCard.setScaleX(1.05);
            characterCard.setScaleY(1.05);
            characterCard.setStyle(characterCard.getStyle() +
                    "-fx-background-color: rgba(255, 255, 255, 0.3);");
        });

        characterCard.setOnMouseExited(e -> {
            characterCard.setScaleX(1.0);
            characterCard.setScaleY(1.0);
            characterCard.setStyle(characterCard.getStyle()
                    .replace("-fx-background-color: rgba(255, 255, 255, 0.3);",
                            "-fx-background-color: rgba(255, 255, 255, 0.2);"));
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
        StackPane root = (StackPane) this.getParent();
        if (root.getChildren().size() > 1) {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(200), this);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(e -> root.getChildren().remove(this));
            fadeOut.play();
        }
    }
}