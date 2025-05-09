package org.example.g12_storybasedgame.view.homescreen.storyline;

import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import javafx.stage.Stage;
import org.example.g12_storybasedgame.view.homescreen.Homescreen;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.*;

public class StorylineScene {
    private Scene scene;
    private BorderPane root;
    private Stage primaryStage;
    private Map<Integer, Boolean> unlockedChapters;

    public StorylineScene(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.unlockedChapters = new HashMap<>();
        initializeUnlockedChapters();
        this.root = new BorderPane();
        setupUI();
        this.scene = new Scene(root, 1024, 619);
    }

    private void initializeUnlockedChapters() {
        unlockedChapters.put(1, true);
        for (int i = 2; i <= 6; i++) {
            unlockedChapters.put(i, false);
        }
    }

    private void setupUI() {
        // Bakgrundsbild
        BackgroundImage bgImage = new BackgroundImage(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/MAPCHAPTER.jpeg"))),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        new BackgroundSize(100, 100, true, true, true, true));
        root.setBackground(new Background(bgImage));

        // Titel
        Label title = new Label("Storyline Map");
        title.setStyle("-fx-font-size: 32px; -fx-text-fill: #FF69B4; -fx-font-family: 'Garamond'; -fx-font-weight: bold;");
        BorderPane.setAlignment(title, Pos.TOP_CENTER);
        BorderPane.setMargin(title, new Insets(20, 0, 0, 0));
        root.setTop(title);

        // Container för kartan med procentuell positionering
        StackPane mapContainer = new StackPane();
        mapContainer.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        // Skapa en overlay-pane för knapparna
        Pane buttonOverlay = new Pane();
        buttonOverlay.setBackground(Background.EMPTY);
        buttonOverlay.setPickOnBounds(false); // Låt klick gå igenom till bakgrunden

        // Positioner i procent av containerns storlek (x%, y%)
        Map<Integer, Point2D> chapterPositions = new HashMap<>();
        chapterPositions.put(1, new Point2D(0.09, 0.874));  // 15% från vänster, 75% från toppen
        chapterPositions.put(2, new Point2D(0.25, 0.874));
        chapterPositions.put(3, new Point2D(0.50, 0.35));  // Mitten
        chapterPositions.put(4, new Point2D(0.70, 0.55));
        chapterPositions.put(5, new Point2D(0.85, 0.75));  // 85% från vänster, 75% från toppen
        chapterPositions.put(6, new Point2D(0.50, 0.80));  // Centrum nedre

        // Skapa knappar för varje kapitel
        for (int i = 1; i <= 6; i++) {
            Button chapterBtn = createChapterButton(i);

            // Bind positionen till containerns storlek
            chapterBtn.layoutXProperty().bind(mapContainer.widthProperty().multiply(chapterPositions.get(i).getX()));
            chapterBtn.layoutYProperty().bind(mapContainer.heightProperty().multiply(chapterPositions.get(i).getY()));

            buttonOverlay.getChildren().add(chapterBtn);
        }

        // Lägg till linjer mellan kapitel (fasta relativt knapppositionerna)
        for (int i = 2; i <= 6; i++) {
            Line connectionLine = new Line();
            connectionLine.setStroke(Color.PINK);
            connectionLine.setStrokeWidth(3);

            // Bind linjens start- och slutpunkter till knapppositionerna
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

        mapContainer.getChildren().addAll(buttonOverlay);
        root.setCenter(mapContainer);

        // Tillbaka-knapp
        Button backBtn = new Button("Back to homescreen");
        backBtn.setOnAction(e -> returnToHomescreen());
        backBtn.setStyle("-fx-background-color: #FFB6C1; -fx-text-fill: white; -fx-font-size: 16px;");
        BorderPane.setAlignment(backBtn, Pos.BOTTOM_CENTER);
        BorderPane.setMargin(backBtn, new Insets(0, 0, 20, 0));
        root.setBottom(backBtn);
    }

    private Button createChapterButton(int chapterNumber) {
        Button btn = new Button();
        btn.setShape(new Circle(30));
        btn.setMinSize(60, 60);
        btn.setMaxSize(60, 60);

        if (unlockedChapters.get(chapterNumber)) {
            btn.setText(String.valueOf(chapterNumber));
            btn.setStyle("-fx-background-color: #FF69B4; -fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");
            btn.setOnAction(e -> startChapter(chapterNumber));
        } else {
            btn.setText("🔒");
            btn.setStyle("-fx-background-color: #FF69B4; -fx-text-fill: #696969; -fx-font-size: 20px; -fx-opacity: 0.7;");
            btn.setDisable(true);
        }

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.PINK);
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
            default:
                System.out.println("Kapitel " + chapterNumber + " är inte implementerat än");
        }
    }

    private void returnToHomescreen() {
        Homescreen homescreen = new Homescreen();
        homescreen.start(primaryStage);
    }

    public Scene getScene() {
        return scene;
    }
}