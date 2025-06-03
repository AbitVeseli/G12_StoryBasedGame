package org.example.g12_storybasedgame.view.homescreen.storyline;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import org.example.g12_storybasedgame.view.homescreen.Homescreen;

import java.io.InputStream;
import java.util.*;

public class StorylineScene extends BorderPane {
    private Scene scene;
    private BorderPane root;
    private Stage primaryStage;
    private Map<Integer, Boolean> unlockedChapters;

    // Color scheme matching Chapter1Scene
    private final String PINK_BG = "-fx-background-color: #FFD6E0;";
    private final String DARK_PINK = "#FF85A2";
    private final String LIGHT_PINK = "#FFC2D1";
    private final String TEXT_COLOR = "#5E2D40";

    public StorylineScene(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.unlockedChapters = new HashMap<>();
        initializeUnlockedChapters();
        setupUI();
    }

    private void initializeUnlockedChapters() {
        unlockedChapters.put(1, true); // Chapter 1 always unlocked
        unlockedChapters.put(2, true); //ta bort sen och byt loopen från 3 till 2
        unlockedChapters.put(3, true);
        for (int i = 4; i <= 6; i++) {
            unlockedChapters.put(i, false);
        }
    }

    private void setupUI() {

        InputStream imageStream = getClass().getResourceAsStream("/MAPCHAPTER.jpg");
        if (imageStream != null) {
            Image backgroundImage = new Image(imageStream);
            BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
            BackgroundImage bgImage = new BackgroundImage(
                    backgroundImage,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    backgroundSize
            );
            this.setBackground(new Background(bgImage));
        } else {
            System.err.println("MAPCHAPTER.jpg kunde inte hittas i resources. Använder fallback-färg.");
            this.setStyle(PINK_BG);
        }


        // Title
        Label title = new Label("Storyline Map");
        title.setStyle("-fx-font-size: 32px; " +
                "-fx-text-fill: " + TEXT_COLOR + "; " +
                "-fx-font-family: 'Arial Rounded MT Bold'; " +
                "-fx-font-weight: bold;");
        BorderPane.setAlignment(title, Pos.TOP_CENTER);
        BorderPane.setMargin(title, new Insets(20, 0, 0, 0));
        this.setTop(title);

        // Map container with chapter buttons
        StackPane mapContainer = new StackPane();
        mapContainer.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        Pane buttonOverlay = new Pane();
        buttonOverlay.setBackground(Background.EMPTY);
        buttonOverlay.setPickOnBounds(false);

        // Chapter button positions (x%, y%)
        Map<Integer, Point2D> chapterPositions = new HashMap<>();
        chapterPositions.put(1, new Point2D(0.09, 0.874));
        chapterPositions.put(2, new Point2D(0.25, 0.874));
        chapterPositions.put(3, new Point2D(0.50, 0.35));
        chapterPositions.put(4, new Point2D(0.70, 0.55));
        chapterPositions.put(5, new Point2D(0.85, 0.75));
        chapterPositions.put(6, new Point2D(0.50, 0.80));

        // Create chapter buttons
        for (int i = 1; i <= 6; i++) {
            Button chapterBtn = createChapterButton(i);
            chapterBtn.layoutXProperty().bind(mapContainer.widthProperty().multiply(chapterPositions.get(i).getX()));
            chapterBtn.layoutYProperty().bind(mapContainer.heightProperty().multiply(chapterPositions.get(i).getY()));
            buttonOverlay.getChildren().add(chapterBtn);
        }

        // Connection lines between chapters
        for (int i = 2; i <= 6; i++) {
            Line connectionLine = new Line();
            connectionLine.setStroke(Color.web(DARK_PINK));
            connectionLine.setStrokeWidth(3);

            connectionLine.startXProperty().bind(
                    mapContainer.widthProperty().multiply(chapterPositions.get(i-1).getX()).add(30)
            );
            connectionLine.startYProperty().bind(
                    mapContainer.heightProperty().multiply(chapterPositions.get(i-1).getY()).add(30)
            );
            connectionLine.endXProperty().bind(
                    mapContainer.widthProperty().multiply(chapterPositions.get(i).getX()).add(30)
            );
            connectionLine.endYProperty().bind(
                    mapContainer.heightProperty().multiply(chapterPositions.get(i).getY()).add(30)
            );

            buttonOverlay.getChildren().add(connectionLine);
        }

        mapContainer.getChildren().add(buttonOverlay);
        this.setCenter(mapContainer);

        // Back button
        Button backBtn = new Button("Back to homescreen");
        backBtn.setStyle("-fx-font-size: 16px; " +
                "-fx-text-fill: white; " +
                "-fx-background-color: " + DARK_PINK + "; " +
                "-fx-padding: 8 16; " +
                "-fx-background-radius: 10;");
        backBtn.setOnAction(e -> returnToHomescreen());
        BorderPane.setAlignment(backBtn, Pos.BOTTOM_CENTER);
        BorderPane.setMargin(backBtn, new Insets(0, 0, 20, 0));
        this.setBottom(backBtn);
    }

    public void unlockChapter(int chapterNumber) {
        unlockedChapters.put(chapterNumber, true);
    }

    private Button createChapterButton(int chapterNumber) {
        Button btn = new Button();
        btn.setShape(new Circle(30));
        btn.setMinSize(60, 60);
        btn.setMaxSize(60, 60);

        if (unlockedChapters.get(chapterNumber)) {
            btn.setText(String.valueOf(chapterNumber));
            btn.setStyle("-fx-background-color: " + DARK_PINK + "; " +
                    "-fx-text-fill: white; " +
                    "-fx-font-size: 20px; " +
                    "-fx-font-weight: bold;");
            btn.setOnAction(e -> startChapter(chapterNumber));
        } else {
            btn.setText("🔒");
            btn.setStyle("-fx-background-color: " + LIGHT_PINK + "; " +
                    "-fx-text-fill: " + TEXT_COLOR + "; " +
                    "-fx-font-size: 20px; " +
                    "-fx-opacity: 0.7;");
            btn.setDisable(true);
        }

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.web(DARK_PINK));
        shadow.setRadius(10);
        btn.setEffect(shadow);

        return btn;
    }

    private void startChapter(int chapterNumber) {
        switch(chapterNumber) {
            case 1:
                Chapter1Scene chapter1 = new Chapter1Scene(primaryStage);
                primaryStage.setScene(chapter1.getScene());
                break;
            case 2:
                Chapter2Scene chapter2 = new Chapter2Scene(primaryStage);
                primaryStage.setScene(chapter2.getScene());
                break;
            case 3:
                Chapter3Scene chapter3 = new Chapter3Scene(primaryStage);
                primaryStage.setScene(chapter3.getScene());
                break;

            default:
                // Show coming soon message
                StackPane messagePane = new StackPane();
                messagePane.setStyle(PINK_BG);

                Label messageLabel = new Label("Chapter " + chapterNumber + " is coming soon!");
                messageLabel.setStyle("-fx-font-size: 24px; " +
                        "-fx-text-fill: " + TEXT_COLOR + ";");

                Button backBtn = new Button("Return to Map");
                backBtn.setStyle("-fx-background-color: " + DARK_PINK + "; " +
                        "-fx-text-fill: white;");
                backBtn.setOnAction(e -> {
                    StorylineScene storylineScene = new StorylineScene(primaryStage);
                    primaryStage.setScene(storylineScene.getScene());
                });

                VBox box = new VBox(20, messageLabel, backBtn);
                box.setAlignment(Pos.CENTER);
                messagePane.getChildren().add(box);

                Scene messageScene = new Scene(messagePane, 1024, 619);
                primaryStage.setScene(messageScene);
        }
    }

    public void returnToHomescreen() {
        Homescreen homescreen = new Homescreen();
        try {
            homescreen.start(primaryStage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Scene asScene() {
        if (scene == null) {
            scene = new Scene(this, 1024, 619); // Anpassa storlek om du vill
        }
        return scene;
    }

}