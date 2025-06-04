package org.example.g12_storybasedgame.view.homescreen.storyline;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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

public class Chapter4Scene {
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

    // Color scheme
    private final String PINK_BG = "-fx-background-color: #FFD6E0;";
    private final String DARK_PINK = "#FF85A2";
    private final String LIGHT_PINK = "#FFC2D1";
    private final String TEXT_COLOR = "#5E2D40";

    public Chapter4Scene(Stage primaryStage) {
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

        // Character image setup
        characterImageView = new ImageView();
        characterImageView.setFitWidth(350);
        characterImageView.setFitHeight(300);
        characterImageView.setPreserveRatio(true);
        characterImageView.setVisible(false);

        StackPane characterPane = new StackPane(characterImageView);
        characterPane.setAlignment(Pos.CENTER);
        characterPane.setPadding(new Insets(20));
        root.setLeft(characterPane);

        // Dialog container setup
        dialogContainer = new VBox();
        dialogContainer.setAlignment(Pos.BOTTOM_CENTER);
        dialogContainer.setPadding(new Insets(0, 0, 40, 0));
        dialogContainer.setSpacing(5);
        dialogContainer.setStyle("-fx-background-color: transparent;");

        // Textbox header
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

        // Dialog text area
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
                "-fx-background-radius: 0 0 10 10;");
        dialogText.setPrefHeight(200);
        dialogText.setPadding(new Insets(15));

        dialogContainer.getChildren().addAll(textboxHeader, dialogText);
        root.setBottom(dialogContainer);

        // Relationship points display
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
                new String[]{"", "After a whole day of investigating you walk back to your dorm room, late at night."},
                new String[]{"", "You sit down at your desk and see the moonlight spill across your desk like silver ink."},
                new String[]{"Abita", " (Inner monologue) I'm so tired... but I can't stop thinking about Mara..."},
                new String[]{"", "Slip."},
                new String[]{"", "Something glides beneath the door. A folded envelope."},
                new String[]{"Abita", " (Inner monologue) What's this...?"},
                new String[]{"", "Your heartbeat quickens as you snatch it up, break the seal and rip it open."},
                new String[]{"", "\"Midnight. Greenhouse. Come alone.\""},
                new String[]{"Abita", " (Inner monologue) The abandoned greenhouse... Who would do this?"}
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
            case 1 -> loadGreenhouseChoices();
            case 3 -> loadConfrontationChoices();
            case 5 -> loadEmilioChoices();
            case 7 -> loadJudasChoices();
            case 9 -> loadBennyChoices();
            case 11 -> loadDylanChoices();
            case 13 -> showEndScreen();
        }
    }

    private void loadGreenhouseChoices() {
        messageQueue.clear();
        currentSection = 2;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Investigate immediately"},
                new String[]{"Option 2", "Prepare first"},
                new String[]{"Option 3", "Tell Emilio"}
        );
    }

    private void loadGreenhouseScene(int choice) {
        showCharacterImage("abita.png");
        messageQueue.clear();
        currentSection = 3;

        switch(choice) {
            case 1 -> relationshipPoints += 1;
            case 2 -> relationshipPoints += 0;
            case 3 -> relationshipPoints -= 1;
        }

        messageQueue.addAll(Arrays.asList(
                new String[]{"", "At close to midnight you slip on your coat and head to the greenhouse."},
                new String[]{"", "The path winds through overgrown hedges and forgotten stone."},
                new String[]{"Abita", " (Inner monologue) This place gives me chills..."},
                new String[]{"", "The greenhouse stands at the far edge of the academy grounds."},
                new String[]{"", "The moonlight glints against the glass panes and dimly lights up the greenhouse."},
                new String[]{"", "The door stands slightly ajar. You push the creaky door open. It groans in protest."},
                new String[]{"Abita", "Hello? Is anyone here?"},
                new String[]{"", "No answer. Only silence."},
                new String[]{"", "You walk slowly and your footsteps echo across the tiles."},
                new String[]{"", "Something on the floor catches your eye - a scarf."},
                new String[]{"Abita", "This pattern... No. That's Mara's!"},
                new String[]{"", "You kneel and pick it up. Your hand trembles as you lift the blood-stained fabric."},
                new String[]{"", "SLAM! The door crashes shut behind you."},
                new String[]{"Abita", "Who's there?! Show yourself!"},
                new String[]{"", "No reply. Shadows dance across the walls. The wind has stopped."},
                new String[]{"???", "You should've stayed out of this."}
        ));

        dialogContainer.getChildren().clear();
        dialogContainer.getChildren().addAll(createTextbox());
        showNextMessage();
    }

    private void loadConfrontationChoices() {
        messageQueue.clear();
        currentSection = 4;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Stay calm "},
                new String[]{"Option 2", "Threaten "},
                new String[]{"Option 3", "Panic "}
        );
    }

    private void loadConfrontationResponse(int choice) {
        showCharacterImage("abita.png");
        messageQueue.clear();
        currentSection = 5;

        switch(choice) {
            case 1 -> {
                relationshipPoints += 1; // Emilio
                relationshipPoints += 1; // Benny
                messageQueue.add(new String[]{"Abita", "I came to talk. Not to fight. Whoever you are... I'm listening."});
            }
            case 2 -> {
                relationshipPoints -= 1; // Judas
                relationshipPoints -= 1; // Benny
                messageQueue.add(new String[]{"Abita", "If you hurt Mara. I'll make sure you pay."});
            }
            case 3 -> {
                relationshipPoints -= 1; // Emilio
                relationshipPoints -= 1; // Dylan
                messageQueue.add(new String[]{"Abita", "Please! Just tell me what you want!"});
            }
        }

        messageQueue.addAll(Arrays.asList(
                new String[]{"", "Footsteps approach slowly, measured and deliberate. But then... nothing."},
                new String[]{"", "A sharp gust of wind explodes from nowhere—blowing the door wide open again."},
                new String[]{"Abita", " (Inner monologue) What just happened...?"},
                new String[]{"", "You grab Mara's scarf and rush out into the cold night."},
                new String[]{"", "The next morning, the sun feels false, weak."},
                new String[]{"", "The events of last night swirl in your mind. You walk briskly through campus."},
                new String[]{"", "The scarf is hidden in your coat pocket."},
                new String[]{"", "You spot Emilio pacing under a tree. He sees you and approaches."},
                new String[]{"Emilio", "Abita! You look pale. Are you okay?"},
                new String[]{"Abita", "I found her scarf, Emilio. In the greenhouse. It was bloody. Someone lured me there."},
                new String[]{"Emilio", "You went there alone? You could've been hurt! Why didn't you tell me you were going?"}
        ));

        dialogContainer.getChildren().clear();
        dialogContainer.getChildren().addAll(createTextbox());
        showNextMessage();
    }

    private void loadEmilioChoices() {
        messageQueue.clear();
        currentSection = 6;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Apologize "},
                new String[]{"Option 2", "Deflect "},
                new String[]{"Option 3", "Snap at him "}
        );
    }

    private void loadEmilioResponse(int choice) {
        showCharacterImage("emilio.png");
        messageQueue.clear();
        currentSection = 7;

        switch(choice) {
            case 1 -> {
                relationshipPoints += 1;
                messageQueue.add(new String[]{"Abita", "I'm sorry. I didn't want to put you in danger."});
            }
            case 2 -> {
                messageQueue.add(new String[]{"Abita", "It was a risk I had to take. I had to go. You know that."});
            }
            case 3 -> {
                relationshipPoints -= 1;
                messageQueue.add(new String[]{"Abita", "What would you have done? Told me to stay home and cry? Wait for someone else to die?"});
            }
        }

        messageQueue.addAll(Arrays.asList(
                new String[]{"Emilio", "I just don't want to lose anyone else."},
                new String[]{"", "You look into his eyes, and something softens between you."},
                new String[]{"", "Later in the library. You sit, still a bit shaken, when Judas appears."},
                new String[]{"", "He slides into the seat across from you. As always, his presence is shadowed and unreadable."},
                new String[]{"Judas", "You're still digging. Even after the warning."},
                new String[]{"Abita", "I need answers. You know something, don't you?"},
                new String[]{"Judas", "This won't change what's done. Asking the wrong questions gets you killed."}
        ));

        dialogContainer.getChildren().clear();
        dialogContainer.getChildren().addAll(createTextbox());
        showNextMessage();
    }

    private void loadJudasChoices() {
        messageQueue.clear();
        currentSection = 8;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Ask gently "},
                new String[]{"Option 2", "Be direct "},
                new String[]{"Option 3", "Accuse "}
        );
    }

    private void loadJudasResponse(int choice) {
        showCharacterImage("judas.png");
        messageQueue.clear();
        currentSection = 9;

        switch(choice) {
            case 1 -> {
                relationshipPoints += 1;
                messageQueue.add(new String[]{"Abita", "I don't believe you're as heartless as you pretend. Help me."});
            }
            case 2 -> {
                messageQueue.add(new String[]{"Abita", "You know something. Stop pretending you don't."});
            }
            case 3 -> {
                relationshipPoints -= 1;
                messageQueue.add(new String[]{"Abita", "You knew she was dead before they even told us, didn't you?"});
            }
        }

        messageQueue.addAll(Arrays.asList(
                new String[]{"Judas", "I'm warning you now, back off. Some truths can't be buried. But maybe they should be..."},
                new String[]{"Judas", "What I know could bury you. You don't have to die for this."},
                new String[]{"", "He walks away before you can respond."},
                new String[]{"", "Later in the evening, on a balcony. You catch sight of Benny overlooking the campus."},
                new String[]{"", "He doesn't react as you approach."},
                new String[]{"Abita", "Last night at midnight. Someone lured me to the greenhouse"},
                new String[]{"Benny", "And you went. Of course you did. Did you find what you were looking for?"},
                new String[]{"Abita", "No. Just more silence. More threats. But you know something, don't you?"}
        ));

        dialogContainer.getChildren().clear();
        dialogContainer.getChildren().addAll(createTextbox());
        showNextMessage();
    }

    private void loadBennyChoices() {
        messageQueue.clear();
        currentSection = 10;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Appeal to him "},
                new String[]{"Option 2", "Test him "},
                new String[]{"Option 3", "Distrust him "}
        );
    }

    private void loadBennyResponse(int choice) {
        showCharacterImage("benny.png");
        messageQueue.clear();
        currentSection = 11;

        switch(choice) {
            case 1 -> {
                relationshipPoints += 1;
                messageQueue.add(new String[]{"Abita", "If you're here for a reason, you owe me the truth."});
            }
            case 2 -> {
                messageQueue.add(new String[]{"Abita", "You walk like someone who's seen this before. Who are you really, Benny?"});
            }
            case 3 -> {
                relationshipPoints -= 1;
                messageQueue.add(new String[]{"Abita", "You're hiding something. Maybe you set the whole thing up."});
            }
        }

        messageQueue.addAll(Arrays.asList(
                new String[]{"Benny", "You're not as naïve as I thought. Good."},
                new String[]{"", "He gives you a smile then walks off, but not before tossing one last comment over his shoulder"},
                new String[]{"Benny", "This school's hiding more than blood and murder. Stay sharp."},
                new String[]{"", "After your talk with Benny, the sun sets and it quickly becomes dark."},
                new String[]{"", "You walk outside towards your dorm house. The stars are barely visible through the swirling clouds."},
                new String[]{"", "You pause when you come across Dylan outside of the dorm house."},
                new String[]{"Dylan", "You're really going full detective and diving into this headfirst."},
                new String[]{"Abita", "Funny. I don't remember inviting you to commentate. But you knew Mara. Did she ever tell you anything strange?"},
                new String[]{"Dylan", "Might want to be careful, Vesela. You've got many watching you now, including the ones you really don't want noticing."}
        ));

        dialogContainer.getChildren().clear();
        dialogContainer.getChildren().addAll(createTextbox());
        showNextMessage();
    }

    private void loadDylanChoices() {
        messageQueue.clear();
        currentSection = 12;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Flirt "},
                new String[]{"Option 2", "Press for answers "},
                new String[]{"Option 3", "Shut him down "}
        );
    }

    private void loadDylanResponse(int choice) {
        showCharacterImage("dylan.png");
        messageQueue.clear();
        currentSection = 13;

        switch(choice) {
            case 1 -> {
                relationshipPoints += 1;
                messageQueue.add(new String[]{"Abita", "Maybe I'm just trying to impress you."});
            }
            case 2 -> {
                messageQueue.add(new String[]{"Abita", "You always act like you know more than you let on. What do you know?"});
            }
            case 3 -> {
                relationshipPoints -= 1;
                messageQueue.add(new String[]{"Abita", "Save the charm. I want facts, not flattery."});
            }
        }

        messageQueue.addAll(Arrays.asList(
                new String[]{"Dylan", "I'll say this, the people behind this? They don't play fair. But they do like a good party."},
                new String[]{"", "He walks away with a smirk, whistling softly."},
                new String[]{"Dylan", "See you at the prom, Abita. Wear something pretty and beautiful, and that makes them regret underestimating you."},
                new String[]{"", "That night as you lie in your bed looking at Mara's scarf that is resting beside you."},
                new String[]{"", "The dried blood has darkened the fabric, but it's still hers."},
                new String[]{"", "You gently fold it and place it on your nightstand, beside it you notice a flyer."},
                new String[]{"", "On the flyer it says with big letters and glittery borders: \"Annual Starfall Prom - One Night of Magic\"."},
                new String[]{"Abita", " (Inner monologue) Prom. I almost forgot. And yet... Dylan's words. That smirk. \"See you at the prom.\""},
                new String[]{"", "You place the flyer beside the scarf, the two looking strangely out of place next to each other."},
                new String[]{"Abita", " (Inner monologue) I don't know if it's fate, bad luck or just malice that I found this scarf, but something inside of me tells that prom is where it all changes."},
                new String[]{"Abita", " (Inner monologue) If the killer is here, if they're hiding in plain sight. Maybe they will be there."}
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
            case 2 -> loadGreenhouseScene(choice);
            case 4 -> loadConfrontationResponse(choice);
            case 6 -> loadEmilioResponse(choice);
            case 8 -> loadJudasResponse(choice);
            case 10 -> loadBennyResponse(choice);
            case 12 -> loadDylanResponse(choice);
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
        // Unlock chapter 5
        StorylineScene storylineScene = new StorylineScene(primaryStage);
        storylineScene.unlockChapter(5);

        dialogContainer.getChildren().clear();

        VBox endBox = new VBox(20);
        endBox.setAlignment(Pos.CENTER);
        endBox.setStyle("-fx-background-color: rgba(255,214,224,0.9); " +
                "-fx-background-radius: 15; " +
                "-fx-padding: 40; " +
                "-fx-border-color: " + DARK_PINK + "; " +
                "-fx-border-width: 3; " +
                "-fx-border-radius: 15;");

        Label endLabel = new Label("CHAPTER 4 COMPLETE");
        endLabel.setStyle("-fx-font-size: 32px; " +
                "-fx-text-fill: " + TEXT_COLOR + "; " +
                "-fx-font-family: 'Arial Rounded MT Bold';");

        Label pointsLabel = new Label("Total Relationship Points: " + relationshipPoints);
        pointsLabel.setStyle("-fx-font-size: 24px; " +
                "-fx-text-fill: " + DARK_PINK + "; " +
                "-fx-font-family: 'Arial Rounded MT Bold';");

        VBox buttonBox = new VBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        Button continueButton = new Button("Continue to Chapter 5");
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
            StorylineScene newStorylineScene = new StorylineScene(primaryStage);
            primaryStage.setScene(newStorylineScene.asScene());
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

        Label loadingLabel = new Label("Loading Chapter 5: Starfall Prom...");
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
                        Chapter5Scene chapter5 = new Chapter5Scene(primaryStage);
                        primaryStage.setScene(chapter5.getScene());
                    } catch (Exception ex) {
                        loadingLabel.setText("Ready for the prom night...");
                        progress.setVisible(false);

                        Button startButton = new Button("Begin Chapter 5");
                        startButton.setStyle("-fx-font-size: 18px; " +
                                "-fx-text-fill: white; " +
                                "-fx-background-color: " + DARK_PINK + "; " +
                                "-fx-padding: 10 20; " +
                                "-fx-background-radius: 20;");
                        startButton.setOnAction(event -> {
                            Chapter5Scene chapter5 = new Chapter5Scene(primaryStage);
                            primaryStage.setScene(chapter5.getScene());
                        });

                        loadingContent.getChildren().add(startButton);
                    }
                })
        );
        loadingTimeline.play();
    }

    public Scene getScene() {
        return scene;
    }
}