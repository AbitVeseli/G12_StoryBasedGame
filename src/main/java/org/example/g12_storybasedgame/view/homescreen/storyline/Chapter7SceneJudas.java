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

public class Chapter7SceneJudas {
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

    public Chapter7SceneJudas(Stage primaryStage) {
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
                new String[]{"", "The backstage hallway is quiet now, shielded from the echoes of chaos still bleeding from the ballroom."},
                new String[]{"", "The distant flicker of shattered magic pulses weakly in the dark like the last embers of a dying fire."},
                new String[]{"", "You and Judas sit on a low bench near the supply alcove, the silence between you more honest than anything spoken in the light of day."},
                new String[]{"", "Judas sits hunched forward, his fingers fidgeting with the worn pendant you now recognize as deeply personal."},
                new String[]{"", "It hangs from a frayed cord, glinting softly in the candlelight salvaged from a shattered candelabra nearby."},
                new String[]{"Judas", "(quietly) I didn't think anyone would come for me. Not really."},
                new String[]{"Abita", "You don't have to be alone in this anymore."},
                new String[]{"", "He turns his head slowly toward you. His eyes are different now, still shadowed, still sharp, but no longer guarded."},
                new String[]{"", "The wall he's always kept between you is cracked."},
                new String[]{"Judas", "I've built my life on half-truths and disappearances. For a while, I thought that was safer."},
                new String[]{"Judas", "That if no one got close, no one could be used against me."},
                new String[]{"", "You don't say anything yet. You let him talk. Let him choose to trust you."},
                new String[]{"Judas", "But tonight, I realized something worse. If no one's close, no one comes when you fall."},
                new String[]{"", "A long pause. His hand brushes against yours on the bench, intentional but tentative."},
                new String[]{"", "When you don't pull away, he lets it rest there."}
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
            case 1 -> loadJudasChoices1();
            case 3 -> loadJudasChoices2();
            case 5 -> showEndScreen();
        }
    }

    private void loadJudasChoices1() {
        messageQueue.clear();
        currentSection = 2;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Offer unconditional support "},
                new String[]{"Option 2", "Keep it honest but cautious "},
                new String[]{"Option 3", "Pull away slightly "}
        );
    }

    private void loadJudasResponse1(int choice) {
        showCharacterImage("judas.png");
        messageQueue.clear();
        currentSection = 3;

        switch(choice) {
            case 1 -> {
                relationshipPoints += 1;
                messageQueue.add(new String[]{"Abita", "I'm here, Judas. And I'm not going anywhere."});
            }
            case 2 -> {
                messageQueue.add(new String[]{"Abita", "I'm with you... but this is a lot. I need to understand what I'm getting into."});
            }
            case 3 -> {
                relationshipPoints -= 1;
                messageQueue.add(new String[]{"Abita", "I care about you, but maybe we're too different. Maybe this isn't meant to work."});
            }
        }

        messageQueue.addAll(Arrays.asList(
                new String[]{"", "He looks down at your joined hands, processing your response. Then he exhales, the sound uneven, like it hurts to let himself feel this much."},
                new String[]{"Judas", "I never asked for someone like you to show up in my life. But now that you have... I don't want to go back to the way things were."},
                new String[]{"", "He shifts slightly, reaching into his coat. This time, it's not a weapon or a charm, but a folded photograph. Weathered, soft at the edges."},
                new String[]{"Judas", "My sister. Her name was Caela. She vanished during a ritual meant to bind shadow magic. The school covered it up. Said she was expelled for dangerous behavior. But I know better."},
                new String[]{"", "He hands you the photo, his fingers trembling just slightly."},
                new String[]{"Judas", "She's the reason I came here. Why do I act like... this. I thought if I could figure out what happened, maybe I'd find her. Or at least the truth."},
                new String[]{"Abita", "She's not the only one who deserves saving."}
        ));

        dialogContainer.getChildren().clear();
        dialogContainer.getChildren().addAll(createTextbox());
        showNextMessage();
    }

    private void loadJudasChoices2() {
        messageQueue.clear();
        currentSection = 4;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Confess love "},
                new String[]{"Option 2", "Commit to the mission "},
                new String[]{"Option 3", "Leave it ambiguous "}
        );
    }

    private void loadJudasResponse2(int choice) {
        showCharacterImage("judas.png");
        messageQueue.clear();
        currentSection = 5;

        switch(choice) {
            case 1 -> {
                relationshipPoints += 1;
                messageQueue.add(new String[]{"Abita", "You matter to me, Judas. I didn't expect it, but I care. More than I realized."});
            }
            case 2 -> {
                messageQueue.add(new String[]{"Abita", "Whatever happened to her, we'll find out together. That's a promise."});
            }
            case 3 -> {
                relationshipPoints -= 1;
                messageQueue.add(new String[]{"Abita", "I don't know where this leads, but I'm still here. For now."});
            }
        }

        messageQueue.addAll(Arrays.asList(
                new String[]{"", "Judas closes his eyes briefly as if bracing himself. When he opens them again, they're softer. Clearer."},
                new String[]{"Judas", "Being with you... it makes me feel like I'm still human. Like I haven't drowned in all this darkness yet."},
                new String[]{"", "The moment stretches. Close. Intimate. His hand rises to brush your cheek, rough knuckles, warm despite the cool air. You don't flinch."},
                new String[]{"", "He leans in, slowly, deliberately. And when your lips meet, it's with the tenderness of two people who've both forgotten what it feels like to be wanted and the relief of finally being seen."},
                new String[]{"", "You pull apart just slightly. He takes out a small flask hidden under his coat, pouring the content in the glasses from before."},
                new String[]{"Judas", "For strength. And maybe... something like hope."},
                new String[]{"", "You clink glasses lightly. The liquid is warm, spiced, and surprisingly sweet."},
                new String[]{"", "But then, a strange sensation coils in your stomach. Cold, wrong. The room tilts."},
                new String[]{"", "Your vision darkens at the edges."},
                new String[]{"Judas", "Abita? Hey, stay with me! Look at me!"},
                new String[]{"", "You reach for him, but your arm doesn't obey. His voice is the last thing you hear before the world collapses inward, darkness swallowing everything."}
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
            case 2 -> loadJudasResponse1(choice);
            case 4 -> loadJudasResponse2(choice);
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

        Label endLabel = new Label("CHAPTER 7 COMPLETE");
        endLabel.setStyle("-fx-font-size: 32px; " +
                "-fx-text-fill: " + TEXT_COLOR + "; " +
                "-fx-font-family: 'Arial Rounded MT Bold';");

        Label pointsLabel = new Label("Total Relationship Points: " + relationshipPoints);
        pointsLabel.setStyle("-fx-font-size: 24px; " +
                "-fx-text-fill: " + DARK_PINK + "; " +
                "-fx-font-family: 'Arial Rounded MT Bold';");

        VBox buttonBox = new VBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        Button continueButton = new Button("Continue to Chapter 8");
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

        Label loadingLabel = new Label("Loading Chapter 8...");
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
                        Chapter8SceneJudas chapter8 = new Chapter8SceneJudas(primaryStage);
                        primaryStage.setScene(chapter8.getScene());
                    } catch (Exception ex) {
                        loadingLabel.setText("Chapter 8 is coming soon!");
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