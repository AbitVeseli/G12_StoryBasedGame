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

public class Chapter7SceneDylan {
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

    public Chapter7SceneDylan(Stage primaryStage) {
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
                new String[]{"", "The aftermath of the attack lingers in the air, the ballroom dimmed, the once-vibrant music now only a distant hum in your mind."},
                new String[]{"", "Your pulse still races, but the immediate danger has faded."},
                new String[]{"", "Dylan stands near the shattered remnants of a chandelier, his shirt torn at the shoulder where magic nearly took him."},
                new String[]{"", "He exhales, rolling his wrist, shaking off the weight of battle, but his usual smirk lacks its typical arrogance."},
                new String[]{"Abita", "You almost died."},
                new String[]{"Dylan", "You say that like it's news. I take risks, Vesela, it's part of the fun."},
                new String[]{"Abita", "And if I hadn't stepped in?"},
                new String[]{"", "Dylan pauses. Just slightly. Just enough for you to know he actually thought about it."},
                new String[]{"Dylan", "Then I guess it would've finally been my turn."},
                new String[]{"", "Something about the way he says it unsettles you."},
                new String[]{"", "This isn't the first time he's been close to death."},
                new String[]{"", "And yet he never asked to be saved."},
                new String[]{"", "The room clears as students stumble out, shaken but safe."},
                new String[]{"", "A few murmurs linger, but soon it's just you and Dylan beneath the fractured glow of the chandeliers."},
                new String[]{"", "He leans against the bar, pouring two drinks like nothing happened."},
                new String[]{"Dylan", "Come on. I think you've earned something stronger than a punch."},
                new String[]{"Abita", "You always act like you know everything before the rest of us do."},
                new String[]{"Dylan", "Maybe I do."},
                new String[]{"Abita", "Then tell me what is this place really hiding?"},
                new String[]{"Dylan", "More than you think. But you're not ready for that yet, detective."},
                new String[]{"Abita", "I saved your life. You owe me answers."},
                new String[]{"Dylan", "That's exactly why I can't give you them."}
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
            case 1 -> loadFirstChoices();
            case 3 -> loadSecondChoices();
            case 5 -> showEndScreen();
        }
    }

    private void loadFirstChoices() {
        messageQueue.clear();
        currentSection = 2;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Push for honesty "},
                new String[]{"Option 2", "Play his game "},
                new String[]{"Option 3", "Call him out "}
        );
    }

    private void loadFirstResponse(int choice) {
        showCharacterImage("dylan.png");
        messageQueue.clear();
        currentSection = 3;

        switch(choice) {
            case 1 -> {
                relationshipPoints += 1;
                messageQueue.add(new String[]{"Abita", "Stop hiding. If I'm in this, I deserve the truth."});
                messageQueue.add(new String[]{"Dylan", "You really don't let up, do you?"});
                messageQueue.add(new String[]{"", "But something changes in his gaze - respect, maybe. Or something deeper."});
            }
            case 2 -> {
                messageQueue.add(new String[]{"Abita", "Fine. Keep your secrets. I'll just find the answers myself."});
                messageQueue.add(new String[]{"Dylan", "I'd expect nothing less."});
            }
            case 3 -> {
                relationshipPoints -= 1;
                messageQueue.add(new String[]{"Abita", "You're just like the rest of them - talk big, but run when it matters."});
                messageQueue.add(new String[]{"Dylan", "You have no idea what I've done to survive this place."});
            }
        }

        messageQueue.addAll(Arrays.asList(
                new String[]{"", "You place your glass down, watching him carefully."},
                new String[]{"Abita", "You know who killed Mara, don't you?"},
                new String[]{"", "Dylan's fingers are still around the drink. For a second, he doesn't breathe."},
                new String[]{"Dylan", "That's a dangerous question."},
                new String[]{"Abita", "I'm done playing safe."},
                new String[]{"", "He exhales, setting his own glass down with deliberate ease."},
                new String[]{"", "Then, slowly - almost too naturally - he reaches toward you, fingers brushing against your wrist."},
                new String[]{"Dylan", "You should know better than to trust people like me, Vesela."},
                new String[]{"", "But he doesn't pull away."}
        ));

        dialogContainer.getChildren().clear();
        dialogContainer.getChildren().addAll(createTextbox());
        showNextMessage();
    }

    private void loadSecondChoices() {
        messageQueue.clear();
        currentSection = 4;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Accept the closeness "},
                new String[]{"Option 2", "Step back "},
                new String[]{"Option 3", "Confront him "}
        );
    }

    private void loadSecondResponse(int choice) {
        showCharacterImage("dylan.png");
        messageQueue.clear();
        currentSection = 5;

        switch(choice) {
            case 1 -> {
                relationshipPoints += 1;
                messageQueue.add(new String[]{"", "You don't move. You let his touch linger."});
                messageQueue.add(new String[]{"Dylan", "You really are something else."});
            }
            case 2 -> {
                messageQueue.add(new String[]{"", "You shift away, pulse steady."});
                messageQueue.add(new String[]{"Abita", "You don't get to distract me that easily."});
                messageQueue.add(new String[]{"Dylan", "I figured it was worth a try."});
            }
            case 3 -> {
                relationshipPoints -= 1;
                messageQueue.add(new String[]{"", "You grip his wrist before he can retreat, eyes burning."});
                messageQueue.add(new String[]{"Abita", "Stop playing games. What are you hiding?"});
                messageQueue.add(new String[]{"Dylan", "You don't want to know."});
            }
        }

        messageQueue.addAll(Arrays.asList(
                new String[]{"", "Dylan moves as if to say something else, something real."},
                new String[]{"", "But before he can, before the moment can break, the world tilts."},
                new String[]{"", "Your vision blurs. The drink. Something was in the drink."},
                new String[]{"Dylan", "Abita?! Hey, stay with me!"},
                new String[]{"", "His grip tightens around your arm, but you're already falling."},
                new String[]{"", "Everything goes black."},
                new String[]{"", "The moment of vulnerability between you was real, but now, everything hangs in the balance."},
                new String[]{"", "When you wake, if you wake, the truth will finally come to light."},
                new String[]{"", "But will Dylan still be there when it does?"}
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
            case 2 -> loadFirstResponse(choice);
            case 4 -> loadSecondResponse(choice);
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
                        Chapter8SceneDylan chapter8 = new Chapter8SceneDylan(primaryStage);
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