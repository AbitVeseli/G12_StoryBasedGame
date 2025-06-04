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

public class Chapter6SceneDylan {
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

    public Chapter6SceneDylan(Stage primaryStage) {
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
                new String[]{"", "The hall is chaos, shattered glass litters the floor, the scent of scorched magic thick in the air."},
                new String[]{"", "Your pulse pounds in your ears as you scan the room, searching for Dylan."},
                new String[]{"", "Then, you spot him."},
                new String[]{"", "He's cornered, standing against the broken edge of the grand piano, blood trailing from a fresh gash along his shoulder."},
                new String[]{"", "His illusions flicker, unstable, barely holding up against the crushing weight of the dark energy suffocating the room."},
                new String[]{"Dylan", "Damn it"}
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
            case 1 -> loadInitialChoices();
            case 3 -> loadBattleChoices();
            case 5 -> loadFinalChoices();
            case 7 -> showEndScreen();
        }
    }

    private void loadInitialChoices() {
        messageQueue.clear();
        currentSection = 2;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Reach for him, no hesitation "},
                new String[]{"Option 2", "Question why he's struggling "},
                new String[]{"Option 3", "Demand he fight harder "}
        );
    }

    private void loadInitialResponse(int choice) {
        showCharacterImage("dylan.png");
        messageQueue.clear();
        currentSection = 3;

        switch(choice) {
            case 1 -> {
                relationshipPoints += 1;
                messageQueue.add(new String[]{"Abita", "Hang on—I've got you!"});
                messageQueue.add(new String[]{"", "Dylan's eyes flicker to yours, shock flashing through them before something softer settles."});
            }
            case 2 -> {
                messageQueue.add(new String[]{"Abita", "You're usually ten steps ahead. Why are you losing now?"});
                messageQueue.add(new String[]{"", "Dylan exhales sharply, frustration tightening his jaw."});
            }
            case 3 -> {
                relationshipPoints -= 1;
                messageQueue.add(new String[]{"Abita", "Get up, Dylan! Don't tell me this is all you've got!"});
                messageQueue.add(new String[]{"", "His smirk is faint, but bitter like you just challenged something deeper than his ego."});
            }
        }

        messageQueue.addAll(Arrays.asList(
                new String[]{"", "You stand beside him now, magic crackling through your fingertips."},
                new String[]{"Dylan", "And here I thought you didn't trust me, Vesela."},
                new String[]{"Abita", "Trust is earned."},
                new String[]{"Dylan", "Then I guess I better make this worth your while."},
                new String[]{"", "The battle surges again. Together, you strike, weaving spells in sync, each movement calculated, each breath sharp with adrenaline."},
                new String[]{"", "For a moment, you understand him. Not as the charming noble with well practiced smiles, but as someone who knows how to survive."},
                new String[]{"", "And then, the attacker shifts focus."},
                new String[]{"", "A spell tears through the air, barreling toward Dylan at full force."},
                new String[]{"", "You have seconds to react."}
        ));

        dialogContainer.getChildren().clear();
        dialogContainer.getChildren().addAll(createTextbox());
        showNextMessage();
    }

    private void loadBattleChoices() {
        messageQueue.clear();
        currentSection = 4;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Shield Him with Your Own Magic "},
                new String[]{"Option 2", "Drag Him Out of the Spell's Path "},
                new String[]{"Option 3", "Warn Him Too Late "}
        );
    }

    private void loadBattleResponse(int choice) {
        showCharacterImage("dylan.png");
        messageQueue.clear();
        currentSection = 5;

        switch(choice) {
            case 1 -> {
                relationshipPoints += 1;
                messageQueue.add(new String[]{"", "You throw up a barrier, the energy colliding violently against your strength."});
                messageQueue.add(new String[]{"", "Dylan stares at you in shock. You protected him."});
            }
            case 2 -> {
                messageQueue.add(new String[]{"", "You grab his wrist and yank him aside, just barely avoiding the hit."});
                messageQueue.add(new String[]{"", "Dylan exhales sharply, gaze flicking to where you still have a grip on him."});
            }
            case 3 -> {
                relationshipPoints -= 1;
                messageQueue.add(new String[]{"Abita", "Move!"});
                messageQueue.add(new String[]{"", "He tries, but the spell catches him. He collapses, breath ragged."});
            }
        }

        messageQueue.addAll(Arrays.asList(
                new String[]{"", "The battle settles. The enemy retreats. The hall is ruined, but for now, the danger has passed."},
                new String[]{"", "Dylan leans against the wall, breathing hard, his usual charm fractured."},
                new String[]{"Dylan", "I don't get it. Why the hell did you save me?"}
        ));

        dialogContainer.getChildren().clear();
        dialogContainer.getChildren().addAll(createTextbox());
        showNextMessage();
    }

    private void loadFinalChoices() {
        messageQueue.clear();
        currentSection = 6;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Honest Answer "},
                new String[]{"Option 2", "Deflect "},
                new String[]{"Option 3", "Mock Him "}
        );
    }

    private void loadFinalResponse(int choice) {
        showCharacterImage("dylan.png");
        messageQueue.clear();
        currentSection = 7;

        switch(choice) {
            case 1 -> {
                relationshipPoints += 1;
                messageQueue.add(new String[]{"Abita", "Because you're worth saving."});
                messageQueue.add(new String[]{"", "Dylan blinks. His smirk wavers like he doesn't know what to do with real sincerity."});
            }
            case 2 -> {
                messageQueue.add(new String[]{"Abita", "Because you would've done the same. Wouldn't you?"});
                messageQueue.add(new String[]{"", "He lets out a quiet chuckle, shaking his head."});
            }
            case 3 -> {
                relationshipPoints -= 1;
                messageQueue.add(new String[]{"Abita", "Don't get used to it. Next time, I might let you fall."});
                messageQueue.add(new String[]{"", "Dylan laughs, but there's something almost sad in his expression."});
            }
        }

        messageQueue.addAll(Arrays.asList(
                new String[]{"", "No matter what you say, Dylan watches you differently now."},
                new String[]{"", "Not just amusement. Something deeper. Something uncertain."},
                new String[]{"", "And as the embers of the battle fade, you realize tonight changed everything."}
        ));

        dialogContainer.getChildren().clear();
        dialogContainer.getChildren().addAll(createTextbox());
        showNextMessage();
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
            case 2 -> loadInitialResponse(choice);
            case 4 -> loadBattleResponse(choice);
            case 6 -> loadFinalResponse(choice);
        }
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

    private void showEndScreen() {
        dialogContainer.getChildren().clear();

        VBox endBox = new VBox(20);
        endBox.setAlignment(Pos.CENTER);
        endBox.setStyle("-fx-background-color: rgba(255,214,224,0.9); " +
                "-fx-background-radius: 15; " +
                "-fx-padding: 40; " +
                "-fx-border-color: " + DARK_PINK + "; " +
                "-fx-border-width: 3; " +
                "-fx-border-radius: 15;");

        Label endLabel = new Label("CHAPTER 6 COMPLETE");
        endLabel.setStyle("-fx-font-size: 32px; " +
                "-fx-text-fill: " + TEXT_COLOR + "; " +
                "-fx-font-family: 'Arial Rounded MT Bold';");

        Label pointsLabel = new Label("Total Relationship Points: " + relationshipPoints);
        pointsLabel.setStyle("-fx-font-size: 24px; " +
                "-fx-text-fill: " + DARK_PINK + "; " +
                "-fx-font-family: 'Arial Rounded MT Bold';");

        VBox buttonBox = new VBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        Button continueButton = new Button("Continue to Chapter 7");
        continueButton.setStyle("-fx-font-size: 18px; " +
                "-fx-text-fill: white; " +
                "-fx-background-color: " + DARK_PINK + "; " +
                "-fx-padding: 10 20; " +
                "-fx-background-radius: 20;");
        continueButton.setOnAction(e -> showLoadingScreen());

        Button backButton = new Button("Return to Storyline Map");
        backButton.setStyle("-fx-font-size: 18px; " +
                "-fx-text-fill: white; " +
                "-fx-background-color: " + DARK_PINK + "; " +
                "-fx-padding: 10 20; " +
                "-fx-background-radius: 20;");
        backButton.setOnAction(e -> {
            StorylineScene storylineScene = new StorylineScene(primaryStage);
            primaryStage.setScene(storylineScene.getScene());
        });

        buttonBox.getChildren().addAll(continueButton, backButton);
        endBox.getChildren().addAll(endLabel, pointsLabel, buttonBox);
        dialogContainer.getChildren().add(endBox);
    }

    private void showLoadingScreen() {
        StackPane loadingPane = new StackPane();
        loadingPane.setStyle("-fx-background-color: " + PINK_BG + ";");

        VBox loadingContent = new VBox(20);
        loadingContent.setAlignment(Pos.CENTER);

        Label loadingLabel = new Label("Loading Chapter 7...");
        loadingLabel.setStyle("-fx-font-size: 32px; " +
                "-fx-text-fill: " + TEXT_COLOR + "; " +
                "-fx-font-family: 'Arial Rounded MT Bold';");

        ProgressIndicator progress = new ProgressIndicator();
        progress.setStyle("-fx-progress-color: " + DARK_PINK + ";");
        progress.setPrefSize(100, 100);

        loadingContent.getChildren().addAll(loadingLabel, progress);
        loadingPane.getChildren().add(loadingContent);

        root.setCenter(loadingPane);

        Timeline loadingTimeline = new Timeline(
                new KeyFrame(Duration.seconds(2), e -> {
                    try {
                        Chapter7SceneDylan chapter7 = new Chapter7SceneDylan(primaryStage);
                        primaryStage.setScene(chapter7.getScene());
                    } catch (Exception ex) {
                        loadingLabel.setText("Chapter 7 is coming soon!");
                        progress.setVisible(false);

                        Button backButton = new Button("Return to Storyline Map");
                        backButton.setStyle("-fx-font-size: 18px; " +
                                "-fx-text-fill: white; " +
                                "-fx-background-color: " + DARK_PINK + "; " +
                                "-fx-padding: 10 20; " +
                                "-fx-background-radius: 20;");
                        backButton.setOnAction(event -> {
                            StorylineScene storylineScene = new StorylineScene(primaryStage);
                            primaryStage.setScene(storylineScene.getScene());
                        });

                        loadingContent.getChildren().add(backButton);
                    }
                })
        );
        loadingTimeline.play();
    }

    public Scene getScene() {
        return scene;
    }
}