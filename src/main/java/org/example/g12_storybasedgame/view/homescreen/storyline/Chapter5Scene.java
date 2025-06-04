package org.example.g12_storybasedgame.view.homescreen.storyline;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.geometry.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.*;

public class Chapter5Scene {
    private Scene scene;
    private BorderPane root;
    private Stage primaryStage;
    private int relationshipPoints = 0;
    private Timeline textAnimation;
    private VBox dialogContainer;
    private TextArea dialogText;
    private Label speakerLabel;
    private ImageView characterImageView;
    private Queue<String[]> messageQueue = new LinkedList<>();
    private boolean isAnimating = false;
    private int currentSection = 0;
    private String[] currentMessage;

    // Pink color scheme
    private final String PINK_BG = "-fx-background-color: #FFD6E0;";
    private final String DARK_PINK = "#FF85A2";
    private final String LIGHT_PINK = "#FFC2D1";
    private final String TEXT_COLOR = "#5E2D40";

    public Chapter5Scene(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.root = new BorderPane();
        setupUI();
        this.scene = new Scene(root, 1024, 768);
        loadOpeningMessages();
        showNextMessage();

        scene.setOnMouseClicked(e -> handleContinue());
    }

    private void setupUI() {
        root.setStyle(PINK_BG + " -fx-background-radius: 10;");

        characterImageView = new ImageView();
        characterImageView.setFitWidth(350);
        characterImageView.setFitHeight(300);
        characterImageView.setPreserveRatio(true);
        characterImageView.setVisible(false);

        StackPane characterPane = new StackPane(characterImageView);
        characterPane.setAlignment(Pos.CENTER);
        characterPane.setPadding(new Insets(20));
        root.setLeft(characterPane);

        dialogContainer = new VBox();
        dialogContainer.setAlignment(Pos.BOTTOM_CENTER);
        dialogContainer.setPadding(new Insets(0, 0, 40, 0));
        dialogContainer.setSpacing(5);
        dialogContainer.setStyle("-fx-background-color: transparent;");

        HBox textboxHeader = new HBox();
        textboxHeader.setAlignment(Pos.CENTER_LEFT);
        textboxHeader.setStyle("-fx-background-color: " + DARK_PINK + "; " +
                "-fx-background-radius: 10 10 0 0; " +
                "-fx-padding: 10 20;");

        speakerLabel = new Label(" ");
        speakerLabel.setStyle("-fx-font-size: 18px; " +
                "-fx-text-fill: white; " +
                "-fx-font-family: 'Arial Rounded MT Bold';");
        textboxHeader.getChildren().add(speakerLabel);

        dialogText = new TextArea();
        dialogText.setEditable(false);
        dialogText.setWrapText(true);
        dialogText.setStyle("-fx-font-size: 16px; " +
                "-fx-font-family: 'Arial Rounded MT Bold'; " +
                "-fx-background-color: rgba(255,255,255,0.7); " +
                "-fx-text-fill: " + TEXT_COLOR + "; " +
                "-fx-border-color: " + DARK_PINK + "; " +
                "-fx-border-width: 2; " +
                "-fx-border-radius: 0 0 10 10; " +
                "-fx-background-radius: 0 0 10 10; " +
                "-fx-control-inner-background: transparent;");
        dialogText.setPrefHeight(200);
        dialogText.setPadding(new Insets(15));

        dialogContainer.getChildren().addAll(textboxHeader, dialogText);
        root.setBottom(dialogContainer);

        Label pointsLabel = new Label("Relationship Points: " + relationshipPoints);
        pointsLabel.setStyle("-fx-font-size: 16px; " +
                "-fx-text-fill: " + TEXT_COLOR + "; " +
                "-fx-font-family: 'Arial Rounded MT Bold';");
        HBox topBar = new HBox(pointsLabel);
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setPadding(new Insets(15));
        topBar.setStyle("-fx-background-color: " + LIGHT_PINK + "; " +
                "-fx-background-radius: 0 0 10 10;");
        root.setTop(topBar);
    }

    private void loadOpeningMessages() {
        messageQueue.clear();
        currentSection = 1;

        messageQueue.addAll(Arrays.asList(
                new String[]{"", "The night of the Starfall Prom arrives in a whirl of glitter and music."},
                new String[]{"", "The grand hall is transformed, twinkling fairy lights drape the walls..."},
                new String[]{"Abita", "(Inner monologue) Tonight should be a celebration. But something's off."},
                new String[]{"", "Outside, under a canopy of stars, you spot Emilio pacing anxiously."},
                new String[]{"Emilio", "Abita, you don't look well. Are you sure you're ready for tonight?"}
        ));
    }

    private void showNextMessage() {
        if (messageQueue.isEmpty()) {
            showCurrentChoices();
            return;
        }

        currentMessage = messageQueue.poll();
        animateText(currentMessage[0], currentMessage[1]);

        if (!currentMessage[0].equals(" ") && !currentMessage[0].contains("(Inner monologue)")) {
            showCharacterImage(currentMessage[0].toLowerCase().replace(" (inner monologue)", "") + ".png");
        } else {
            hideCharacterImage();
        }
    }

    private void animateText(String speaker, String text) {
        if (textAnimation != null) {
            textAnimation.stop();
        }

        isAnimating = true;
        speakerLabel.setText(speaker);
        dialogText.setText("");

        final int[] i = {0};
        textAnimation = new Timeline(new KeyFrame(Duration.millis(30), e -> {
            if (i[0] < text.length()) {
                dialogText.appendText(String.valueOf(text.charAt(i[0])));
                i[0]++;
            } else {
                textAnimation.stop();
                isAnimating = false;
            }
        }));
        textAnimation.setCycleCount(Animation.INDEFINITE);
        textAnimation.play();
    }

    private void handleContinue() {
        if (isAnimating) {
            textAnimation.stop();
            dialogText.setText(currentMessage[1]);
            isAnimating = false;
        } else {
            showNextMessage();
        }
    }

    private void showCurrentChoices() {
        switch (currentSection) {
            case 1 -> loadEmilioChoices();
            case 3 -> loadJudasChoices();
            case 5 -> loadBennyChoices();
            case 7 -> loadDylanChoices();
            case 9 -> loadFinalChoices();
        }
    }

    private void loadEmilioChoices() {
        messageQueue.clear();
        currentSection = 2;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Reassure "},
                new String[]{"Option 2", "Brush off "},
                new String[]{"Option 3", "Confide "}
        );
    }

    private void loadJudasChoices() {
        messageQueue.clear();
        currentSection = 4;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Plead "},
                new String[]{"Option 2", "Challenge "},
                new String[]{"Option 3", "Accuse "}
        );
    }

    private void loadBennyChoices() {
        messageQueue.clear();
        currentSection = 6;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Appeal "},
                new String[]{"Option 2", "Probe "},
                new String[]{"Option 3", "Distrust "}
        );
    }

    private void loadDylanChoices() {
        messageQueue.clear();
        currentSection = 8;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Flirt "},
                new String[]{"Option 2", "Demand answers "},
                new String[]{"Option 3", "Shut down "}
        );
    }

    private void loadFinalChoices() {
        messageQueue.clear();
        currentSection = 10;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Route 1", "Follow Judas"},
                new String[]{"Route 2", "Follow Dylan"}
        );
    }

    private void showChoiceButtons(String[]... options) {
        VBox choicesBox = new VBox(15);
        choicesBox.setAlignment(Pos.CENTER);
        choicesBox.setStyle("-fx-background-color: rgba(255,255,255,0.8); " +
                "-fx-background-radius: 10; " +
                "-fx-padding: 30;");

        Label promptLabel = new Label("How do you respond?");
        promptLabel.setStyle("-fx-font-size: 20px; " +
                "-fx-text-fill: " + TEXT_COLOR + "; " +
                "-fx-font-family: 'Arial Rounded MT Bold';");

        HBox buttonRow = new HBox(15);
        buttonRow.setAlignment(Pos.CENTER);

        for (String[] option : options) {
            Button btn = new Button(option[1]);
            btn.setStyle("-fx-font-size: 16px; " +
                    "-fx-text-fill: white; " +
                    "-fx-background-color: " + DARK_PINK + "; " +
                    "-fx-padding: 12 25; " +
                    "-fx-border-color: white; " +
                    "-fx-border-width: 2; " +
                    "-fx-border-radius: 20; " +
                    "-fx-background-radius: 20;");
            btn.setEffect(new javafx.scene.effect.DropShadow(5, Color.PINK));

            final int choice = Integer.parseInt(option[0].substring(7, 8));
            btn.setOnAction(e -> handleChoice(choice));

            btn.setOnMouseEntered(e -> btn.setStyle("-fx-font-size: 16px; " +
                    "-fx-text-fill: white; " +
                    "-fx-background-color: #FF6B8B; " +
                    "-fx-padding: 12 25; " +
                    "-fx-border-color: white; " +
                    "-fx-border-width: 2; " +
                    "-fx-border-radius: 20; " +
                    "-fx-background-radius: 20;"));

            btn.setOnMouseExited(e -> btn.setStyle("-fx-font-size: 16px; " +
                    "-fx-text-fill: white; " +
                    "-fx-background-color: " + DARK_PINK + "; " +
                    "-fx-padding: 12 25; " +
                    "-fx-border-color: white; " +
                    "-fx-border-width: 2; " +
                    "-fx-border-radius: 20; " +
                    "-fx-background-radius: 20;"));

            buttonRow.getChildren().add(btn);
        }

        choicesBox.getChildren().addAll(promptLabel, buttonRow);
        dialogContainer.getChildren().add(choicesBox);
    }

    private void handleChoice(int choice) {
        switch (currentSection) {
            case 2 -> loadEmilioResponse(choice);
            case 4 -> loadJudasResponse(choice);
            case 6 -> loadBennyResponse(choice);
            case 8 -> loadDylanResponse(choice);
            case 10 -> handleFinalChoice(choice);
        }
    }

    private void loadEmilioResponse(int choice) {
        showCharacterImage("emilio.png");
        messageQueue.clear();
        currentSection = 3;

        switch(choice) {
            case 1 -> {
                relationshipPoints += 1;
                messageQueue.add(new String[]{"Abita", "I'm ready. I just need to stay sharp."});
            }
            case 2 -> {
                messageQueue.add(new String[]{"Abita", "It's just nerves. Nothing more."});
            }
            case 3 -> {
                relationshipPoints -= 1;
                messageQueue.add(new String[]{"Abita", "I'm scared, Emilio. What if something goes wrong?"});
            }
        }

        messageQueue.addAll(Arrays.asList(
                new String[]{"Emilio", "Whatever happens, I'm here. Don't forget that."},
                new String[]{"", "Near the entrance, Judas leans against a pillar..."},
                new String[]{"Judas", "You really should stop chasing shadows, Abita."}
        ));

        dialogContainer.getChildren().clear();
        dialogContainer.getChildren().addAll(createTextbox());
        showNextMessage();
    }

    private void loadJudasResponse(int choice) {
        showCharacterImage("judas.png");
        messageQueue.clear();
        currentSection = 5;

        switch(choice) {
            case 1 -> {
                relationshipPoints += 1;
                messageQueue.add(new String[]{"Abita", "I can't stop now. You know that. Please, help me."});
            }
            case 2 -> {
                messageQueue.add(new String[]{"Abita", "Why do you care? What are you hiding?"});
            }
            case 3 -> {
                relationshipPoints -= 1;
                messageQueue.add(new String[]{"Abita", "You know more than you say. Don't play games."});
            }
        }

        messageQueue.addAll(Arrays.asList(
                new String[]{"", "Judas's expression darkens, but he doesn't respond."},
                new String[]{"", "By the stage, Benny stands calm and collected..."},
                new String[]{"Benny", "Enjoying the show so far? Don't let the glitter blind you."}
        ));

        dialogContainer.getChildren().clear();
        dialogContainer.getChildren().addAll(createTextbox());
        showNextMessage();
    }

    private void loadBennyResponse(int choice) {
        showCharacterImage("benny.png");
        messageQueue.clear();
        currentSection = 7;

        switch(choice) {
            case 1 -> {
                relationshipPoints += 1;
                messageQueue.add(new String[]{"Abita", "If you know something, Benny, now's the time to share."});
            }
            case 2 -> {
                messageQueue.add(new String[]{"Abita", "You act like you've seen it all before. What's your story?"});
            }
            case 3 -> {
                relationshipPoints -= 1;
                messageQueue.add(new String[]{"Abita", "Maybe you're part of this mess, aren't you?"});
            }
        }

        messageQueue.addAll(Arrays.asList(
                new String[]{"Benny", "You're not as naïve as I thought. Good."},
                new String[]{"", "From behind, Dylan appears with a grin..."},
                new String[]{"Dylan", "Ready to steal the spotlight? Just don't get caught in the shadows."}
        ));

        dialogContainer.getChildren().clear();
        dialogContainer.getChildren().addAll(createTextbox());
        showNextMessage();
    }

    private void loadDylanResponse(int choice) {
        showCharacterImage("dylan.png");
        messageQueue.clear();
        currentSection = 9;

        switch(choice) {
            case 1 -> {
                relationshipPoints += 1;
                messageQueue.add(new String[]{"Abita", "Maybe I'm just trying to impress you."});
            }
            case 2 -> {
                messageQueue.add(new String[]{"Abita", "You always know more than you let on. What's really going on?"});
            }
            case 3 -> {
                relationshipPoints -= 1;
                messageQueue.add(new String[]{"Abita", "Save the charm. I want facts."});
            }
        }

        messageQueue.addAll(Arrays.asList(
                new String[]{"Dylan", "I'll say this, the people behind this? They don't play fair."},
                new String[]{"", "The principal's voice booms across the hall..."},
                new String[]{"Principal", "Welcome to the Starfall Prom! Tonight, we celebrate light, friendship, and hope."},
                new String[]{"", "Suddenly, a violent burst of dark magic explodes from the far side of the room."},
                new String[]{"Abita", "(Inner monologue) The hall is chaos... I have to choose who to save!"}
        ));

        dialogContainer.getChildren().clear();
        dialogContainer.getChildren().addAll(createTextbox());
        showNextMessage();
    }

    private void handleFinalChoice(int choice) {
        currentSection = 10;
        dialogContainer.getChildren().clear();

        VBox choiceBox = new VBox(20);
        choiceBox.setAlignment(Pos.CENTER);
        choiceBox.setStyle("-fx-background-color: rgba(255,214,224,0.9); " +
                "-fx-background-radius: 15; " +
                "-fx-padding: 40;");

        Label choiceLabel = new Label("Your choice will change everything...");
        choiceLabel.setStyle("-fx-font-size: 24px; " +
                "-fx-text-fill: " + TEXT_COLOR + "; " +
                "-fx-font-family: 'Arial Rounded MT Bold';");

        ProgressIndicator progress = new ProgressIndicator();
        progress.setStyle("-fx-progress-color: " + DARK_PINK + ";");
        progress.setPrefSize(50, 50);

        choiceBox.getChildren().addAll(choiceLabel, progress);
        dialogContainer.getChildren().add(choiceBox);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(2), e -> {
                    if (choice == 1) {
                        Chapter6SceneJudas chapter6 = new Chapter6SceneJudas(primaryStage);
                        primaryStage.setScene(chapter6.getScene());
                    } else {
                        Chapter6SceneDylan chapter6 = new Chapter6SceneDylan(primaryStage);
                        primaryStage.setScene(chapter6.getScene());
                    }
                })
        );
        timeline.play();
    }

    private void showCharacterImage(String imagePath) {
        try {
            Image image = new Image(getClass().getResourceAsStream("/" + imagePath));
            characterImageView.setImage(image);
            characterImageView.setVisible(true);
        } catch (Exception e) {
            characterImageView.setVisible(false);
            System.err.println("Could not load image: " + imagePath);
        }
    }

    private void hideCharacterImage() {
        characterImageView.setVisible(false);
    }

    private VBox createTextbox() {
        VBox textbox = new VBox();
        textbox.setAlignment(Pos.BOTTOM_CENTER);
        textbox.setStyle("-fx-background-color: transparent;");

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: " + DARK_PINK + "; " +
                "-fx-background-radius: 10 10 0 0; " +
                "-fx-padding: 10 20;");
        header.getChildren().add(speakerLabel);

        textbox.getChildren().addAll(header, dialogText);
        return textbox;
    }

    public Scene getScene() {
        return scene;
    }
}