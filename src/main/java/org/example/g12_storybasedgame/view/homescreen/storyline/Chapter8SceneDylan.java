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

public class Chapter8SceneDylan {
    private Scene scene;
    private BorderPane root;
    private Stage primaryStage;
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

    public Chapter8SceneDylan(Stage primaryStage) {
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

        HBox topBar = new HBox();
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
                new String[]{"", "Heat. Cold. Silence."},
                new String[]{"", "The world tilts. Your pulse thrums sluggishly. Voices blur in and out of recognition."},
                new String[]{"Dylan", "You should know by now, Vesela - things don't always go your way."},
                new String[]{"", "Something cold brushes against your cheek. Not desperate. Calculated."},
                new String[]{"", "You blink, vision struggling against the haze. Dim candle light flickers against stone walls, no ballroom, no windows, no exit."},
                new String[]{"", "This isn't a mistake. This is a cage."},
                new String[]{"", "And sitting just feet away, is Dylan Ravenwood."},
                new String[]{"Dylan", "Welcome home."},
                new String[]{"Abita", "What did you do?"},
                new String[]{"Dylan", "Relax. You're safe. Safer than you would've been anywhere else."},
                new String[]{"Abita", "You drugged me."},
                new String[]{"Dylan", "You say 'drugged' like it wasn't necessary. You were reckless tonight, running straight into chaos, getting yourself hurt."},
                new String[]{"Dylan", "I had to make sure you didn't do something stupid."},
                new String[]{"Abita", "You had no right."},
                new String[]{"", "Dylan's smirk fades slightly, gaze flickering with something unreadable."},
                new String[]{"Dylan", "You really think anyone else would've kept you alive tonight? After everything you stirred up?"},
                new String[]{"", "The words linger. This isn't about protection. This is about control."},
                new String[]{"Dylan", "Let's stop pretending, Vesela. You have two choices."},
                new String[]{"", "He stands, stepping toward you, gaze cool, like the decision has already been made."},
                new String[]{"Dylan", "You can keep acting like you're above all this. That you'll walk away untouched, that you'll win somehow."},
                new String[]{"", "His voice lowers, dangerously close now."},
                new String[]{"Dylan", "Or you can face reality, and accept what this world really is."}
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
            case 1 -> loadFinalChoices();
            case 3 -> showEndScreen();
        }
    }

    private void loadFinalChoices() {
        messageQueue.clear();
        currentSection = 2;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Try to escape"},
                new String[]{"Option 2", "Submit to Dylan"}
        );
    }

    private void loadFinalResponse(int choice) {
        messageQueue.clear();
        currentSection = 3;

        if (choice == 1) {
            // Bad Ending 2: Framed for Murder
            messageQueue.addAll(Arrays.asList(
                    new String[]{"Abita", "You killed Mara."},
                    new String[]{"Dylan", "Did I?"},
                    new String[]{"Abita", "Stop playing games."},
                    new String[]{"Dylan", "You keep looking at me like I'm the villain here. But tell me, Vesela, who do you think the academy will believe?"},
                    new String[]{"Dylan", "Everyone saw the way you searched for answers. The way you got involved. The way you didn't let anything go."},
                    new String[]{"Dylan", "And when they find out that Mara was killed over a secret she uncovered... don't you think you make the perfect suspect?"},
                    new String[]{"Abita", "You wouldn't."},
                    new String[]{"Dylan", "I don't have to do anything, detective. The pieces are already in place."},
                    new String[]{"Abita", "You planned this from the start."},
                    new String[]{"Dylan", "I didn't plan for you to be this fun. But now that we're here..."},
                    new String[]{"", "The door swings open—guards step in, magic cuffs ready."},
                    new String[]{"", "And the last thing you hear as they drag you away, to the fate Dylan carved for you is his soft, satisfied laughter."}
            ));
        } else {
            // Bad Ending 1: Kidnapped by Dylan
            messageQueue.addAll(Arrays.asList(
                    new String[]{"Abita", "You can't keep me here."},
                    new String[]{"Dylan", "And yet, here you are."},
                    new String[]{"", "You push up from the chair, body tense, every muscle screaming at you to run but Dylan moves first."},
                    new String[]{"Dylan", "I saved you tonight, Vesela. I protected you. If I hadn't, you'd be dead."},
                    new String[]{"Abita", "You didn't save me - you took me!"},
                    new String[]{"Dylan", "Think about it. The academy is ruined. Your name? Destroyed. No one will believe you over me."},
                    new String[]{"Abita", "Please, Dylan..."},
                    new String[]{"Dylan", "Relax, Vesela. It'll be easier that way."},
                    new String[]{"", "Your vision blurs again, the room closing in."},
                    new String[]{"", "And as the world fades, you realize, you were never meant to escape him."}
            ));
        }

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

        Label promptLabel = new Label("Your Final Decision");
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
            case 2 -> loadFinalResponse(choice);
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

        Label endLabel = new Label("CHAPTER 8 COMPLETE");
        endLabel.setStyle("-fx-font-size: 32px; " +
                "-fx-text-fill: " + TEXT_COLOR + "; " +
                "-fx-font-family: 'Arial Rounded MT Bold';");

        Label thanksLabel = new Label("Thank you for playing!");
        thanksLabel.setStyle("-fx-font-size: 24px; " +
                "-fx-text-fill: " + DARK_PINK + "; " +
                "-fx-font-family: 'Arial Rounded MT Bold';");

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

        endBox.getChildren().addAll(endLabel, thanksLabel, backButton);
        dialogContainer.getChildren().add(endBox);
    }

    public Scene getScene() {
        return scene;
    }
}