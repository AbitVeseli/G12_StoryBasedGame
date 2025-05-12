package org.example.g12_storybasedgame.view.homescreen.storyline;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.util.*;
import javafx.scene.input.MouseEvent;
import org.example.g12_storybasedgame.view.homescreen.storyline.StorylineScene;
import org.example.g12_storybasedgame.view.homescreen.storyline.Chapter2Scene;

public class Chapter1Scene {
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

    // Pink color scheme
    private final String PINK_BG = "-fx-background-color: #FFD6E0;";
    private final String DARK_PINK = "#FF85A2";
    private final String LIGHT_PINK = "#FFC2D1";
    private final String TEXT_COLOR = "#5E2D40";

    public Chapter1Scene(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.root = new BorderPane();
        setupUI();
        this.scene = new Scene(root, 1024, 768);
        loadOpeningMessages();
        showNextMessage();

        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> handleContinue());
    }

    private void setupUI() {
        // 1. Background with pink gradient
        root.setStyle(PINK_BG + " -fx-background-radius: 10;");

        // Character image container (left side)
        characterImageView = new ImageView();
        characterImageView.setFitWidth(350);
        characterImageView.setFitHeight(500);
        characterImageView.setPreserveRatio(true);
        characterImageView.setVisible(false);

        StackPane characterPane = new StackPane(characterImageView);
        characterPane.setAlignment(Pos.CENTER);
        characterPane.setPadding(new Insets(20));
        root.setLeft(characterPane);

        // 2. Textbox container
        dialogContainer = new VBox();
        dialogContainer.setAlignment(Pos.BOTTOM_CENTER);
        dialogContainer.setPadding(new Insets(0, 0, 40, 0));
        dialogContainer.setSpacing(5);
        dialogContainer.setStyle("-fx-background-color: transparent;");

        // Textbox header with pink styling
        HBox textboxHeader = new HBox();
        textboxHeader.setAlignment(Pos.CENTER_LEFT);
        textboxHeader.setStyle("-fx-background-color: " + DARK_PINK + "; " +
                "-fx-background-radius: 10 10 0 0; " +
                "-fx-padding: 10 20;");

        speakerLabel = new Label("Textbox");
        speakerLabel.setStyle("-fx-font-size: 18px; " +
                "-fx-text-fill: white; " +
                "-fx-font-family: 'Arial Rounded MT Bold';");
        textboxHeader.getChildren().add(speakerLabel);

        // Textbox content with pink styling
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
        dialogText.setPrefSize(900, 180);
        dialogText.setPadding(new Insets(15));

        dialogContainer.getChildren().addAll(textboxHeader, dialogText);
        root.setBottom(dialogContainer);

        // Add relationship points display at top
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
                new String[]{"Textbox", "You suddenly wake up from sleep because you hear someone calling for you and see a golden bird."},
                new String[]{"Nugari", "Abita wake up!! You're going to be late to school!!"},
                new String[]{"Abita", "Ugh, Nugari you're being too loud. Give me 5 more minutes..."},

                new String[]{"Abita (Inner monologue)", "Wow, the weather's so nice today..."},
                new String[]{"Abita (Inner monologue)", "A nice breeze and the sun is out, I have a good feeling about today."},
                new String[]{"Abita (Inner monologue)", "I should go look for Mara!"},
                new String[]{" ", "*BAM/CRASH*"},
                new String[]{"???", "Huff, are your eyes only for decoration? Watch where you're going!"}
        ));
    }

    private void loadJudasChoices() {
        messageQueue.clear();
        currentSection = 2;

        // Clear current dialog and show choices
        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Apologize"},
                new String[]{"Option 2", "Ignore"},
                new String[]{"Option 3", "Gaslight"}
        );
    }

    private void loadJudasResponse(int choice) {
        showCharacterImage("judas.png");

        messageQueue.clear();
        currentSection = 3;

        switch(choice) {
            case 1 -> {
                relationshipPoints += 1;
                messageQueue.add(new String[]{"???", "Watch where you're going!"});
            }
            case 2 -> {
                relationshipPoints -= 1;
                messageQueue.add(new String[]{"???", "HEY!!"});
            }
            case 3 -> {
                relationshipPoints -= 2;
                messageQueue.add(new String[]{"???", "(Stunned silent)"});
            }
        }

        messageQueue.addAll(Arrays.asList(
                new String[]{"Abita", "Black coily hair, undone tie, rude attitude..."},
                new String[]{"Abita", "It's definitely Judas Blackthorne."},
                new String[]{"Abita", "Ugh to bump into him of all people today."},
                new String[]{"Abita", "This totally ruins my mood."},
                new String[]{"Abita", "Now where the heck is Mara?!"},
                new String[]{"Textbox", "In a bad mood you enter the classroom..."},
                new String[]{"Textbox", "You hear the chatter from your classmates..."},
                new String[]{"Textbox", "You see a group surrounding Dylan T. Ravenwood."},
                new String[]{"Textbox", "Dylan is popular and comes from noble ancestry."},
                new String[]{"Textbox", "You pay him no mind and sit next to Emilio Valmont."},
                new String[]{"Textbox", "You've sat together since your first day at school."},
                new String[]{"Abita", "Why are there so many people here today?"},
                new String[]{"Abita", "Let's find a place to sit first..."},
                new String[]{"Fangirl 1", "Hahahh Dylan you're so funny"},
                new String[]{"Fanboy 1", "Of course he is. He's the best at everything!"},
                new String[]{"Dylan", "Now now guys, I'm flattered, but I'm not that special."},
                new String[]{"Fans", "NO! Your looks, your brains your FAMILY. You're superior!"},
                new String[]{"Abita", "I feel eyes in the back of my head..."},
                new String[]{"Textbox", "(Dylan is looking at you)"}
        ));

        // Restore dialog UI
        dialogContainer.getChildren().clear();
        dialogContainer.getChildren().addAll(createTextbox());
        showNextMessage();
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

    private void showNextMessage() {
        if (messageQueue.isEmpty()) {
            return;
        }

        String[] message = messageQueue.poll();
        animateText(message[0], message[1]);

        // Show character image based on speaker
        if (!message[0].equals("Textbox")) {
            showCharacterImage(message[0].toLowerCase() + ".png");
        } else {
            hideCharacterImage();
        }
    }

    private void animateText(String speaker, String text) {
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
            isAnimating = false;
            if (!messageQueue.isEmpty()) {
                String[] nextMsg = messageQueue.peek();
                dialogText.setText(nextMsg[1]);
                speakerLabel.setText(nextMsg[0]);
            }
        } else {
            if (messageQueue.isEmpty()) {
                showCurrentChoices();
            } else {
                showNextMessage();
            }
        }
    }

    private void showCurrentChoices() {
        switch (currentSection) {
            case 1 -> loadJudasChoices();
            case 3 -> loadDylanChoices();
            case 5 -> loadEmilioChoices();
            case 7 -> showEndScreen();
        }
    }

    private void showChoiceButtons(String[]... options) {
        // Change VBox to HBox for horizontal layout
        HBox choicesBox = new HBox(15); // 15 is the spacing between buttons
        choicesBox.setAlignment(Pos.CENTER);
        choicesBox.setStyle("-fx-background-color: rgba(255,255,255,0.8); " +
                "-fx-background-radius: 10; " +
                "-fx-padding: 30;");

        Label promptLabel = new Label("How do you respond?");
        promptLabel.setStyle("-fx-font-size: 20px; " +
                "-fx-text-fill: " + TEXT_COLOR + "; " +
                "-fx-font-family: 'Arial Rounded MT Bold';");

        // Create a VBox to hold the prompt above the horizontal buttons
        VBox container = new VBox(15, promptLabel, choicesBox);
        container.setAlignment(Pos.CENTER);

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

            // Hover effect
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

            choicesBox.getChildren().add(btn);
        }

        dialogContainer.getChildren().add(container);
    }

    private void handleChoice(int choice) {
        switch (currentSection) {
            case 2 -> loadJudasResponse(choice);
            case 4 -> loadDylanResponse(choice);
            case 6 -> loadEmilioResponse(choice);
        }
    }

    private void loadDylanChoices() {
        messageQueue.clear();
        currentSection = 4;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Look and wave (-1 relationship)"},
                new String[]{"Option 2", "Look and furrow your brows (+1 relationship)"},
                new String[]{"Option 3", "Don't look back (-3 relationship)"}
        );
    }

    private void loadDylanResponse(int choice) {
        showCharacterImage("dylan.png");

        messageQueue.clear();
        currentSection = 5;

        switch(choice) {
            case 1 -> {
                relationshipPoints -= 1;
                messageQueue.add(new String[]{"Dylan", "(Waves back awkwardly)"});
            }
            case 2 -> {
                relationshipPoints += 1;
                messageQueue.add(new String[]{"Dylan", "(Looks confused)"});
            }
            case 3 -> {
                relationshipPoints -= 3;
                messageQueue.add(new String[]{"Dylan", "(Looks disappointed)"});
            }
        }

        messageQueue.addAll(Arrays.asList(
                new String[]{"Abita", "Ugh, that was so uncomfortable. Let's find a familiar face instead. Oh! it's Emilio!"},
                new String[]{"Abita", "Valmont, have you heard from Mara?"},
                new String[]{"Emilio", "Hey Abita, lovely to see you too, how are you?"},
                new String[]{"Abita", "Ha Ha Ha, I'm great how are you? No but for real, I haven't been able to get in touch with her since yesterday."},
                new String[]{"Emilio", "Hmm, don't worry about it! You know Mara, she's always late to class, she'll be here a second before the doors close!"}
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
                new String[]{"Option 1", "Nod and agree with him (+1 relationship)"},
                new String[]{"Option 2", "Reluctantly agree (-1 relationship)"},
                new String[]{"Option 3", "Disagree with him (+0 relationship)"}
        );
    }

    private void loadEmilioResponse(int choice) {
        showCharacterImage("emilio.png");

        messageQueue.clear();
        currentSection = 7;

        switch(choice) {
            case 1 -> {
                relationshipPoints += 1;
                messageQueue.add(new String[]{"Abita", "Yeah, you're probably right..."});
            }
            case 2 -> {
                relationshipPoints -= 1;
                messageQueue.add(new String[]{"Abita", "I guess you're right..."});
            }
            case 3 -> {
                messageQueue.add(new String[]{"Abita", "I don't think so, she always replies to me in the morning."});
            }
        }

        messageQueue.addAll(Arrays.asList(
                new String[]{"Emilio", "We're about to find out."},
                new String[]{"Textbox", "The conversations calm down as the headmistress enters and heads toward the podium..."},
                new String[]{"Textbox", "The room goes silent as the teacher announces that a fellow student from your class has been murdered."}
        ));

        dialogContainer.getChildren().clear();
        dialogContainer.getChildren().addAll(createTextbox());
        showNextMessage();
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
        // Unlock chapter 2 first
        StorylineScene storylineScene = new StorylineScene(primaryStage);
        storylineScene.unlockChapter(2);

        dialogContainer.getChildren().clear();

        VBox endBox = new VBox(20);
        endBox.setAlignment(Pos.CENTER);
        endBox.setStyle("-fx-background-color: rgba(255,214,224,0.9); " +
                "-fx-background-radius: 15; " +
                "-fx-padding: 40; " +
                "-fx-border-color: " + DARK_PINK + "; " +
                "-fx-border-width: 3; " +
                "-fx-border-radius: 15;");

        Label endLabel = new Label("CHAPTER 1 COMPLETE");
        endLabel.setStyle("-fx-font-size: 32px; " +
                "-fx-text-fill: " + TEXT_COLOR + "; " +
                "-fx-font-family: 'Arial Rounded MT Bold';");

        Label pointsLabel = new Label("Total Relationship Points: " + relationshipPoints);
        pointsLabel.setStyle("-fx-font-size: 24px; " +
                "-fx-text-fill: " + DARK_PINK + "; " +
                "-fx-font-family: 'Arial Rounded MT Bold';");

        VBox buttonBox = new VBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        Button continueButton = new Button("Continue to Chapter 2");
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
            primaryStage.setScene(newStorylineScene.getScene());
        });

        buttonBox.getChildren().addAll(continueButton, backButton);
        endBox.getChildren().addAll(endLabel, pointsLabel, buttonBox);
        dialogContainer.getChildren().add(endBox);
    }

    private void showLoadingScreen() {
        StackPane loadingPane = new StackPane();
        loadingPane.setStyle("-fx-background-color: " + PINK_BG + ";");

        VBox loadingContent = new VBox(20);
        loadingContent.setAlignment(Pos.CENTER_LEFT);

        Label loadingLabel = new Label("Loading Chapter 2...");
        loadingLabel.setStyle("-fx-font-size: 32px; " +
                "-fx-text-fill: " + TEXT_COLOR + "; " +
                "-fx-font-family: 'Arial Rounded MT Bold';");

        ProgressIndicator progress = new ProgressIndicator();
        progress.setStyle("-fx-progress-color: " + DARK_PINK + ";");
        progress.setPrefSize(100, 100);

        loadingContent.getChildren().addAll(loadingLabel, progress);
        loadingPane.getChildren().add(loadingContent);

        root.setCenter(loadingPane);

        // Simulate loading with a timeline
        Timeline loadingTimeline = new Timeline(
                new KeyFrame(Duration.seconds(2), e -> {
                    // Transition to Chapter 2
                    Chapter2Scene chapter2 = new Chapter2Scene(primaryStage);
                    primaryStage.setScene(chapter2.getScene());
                })
        );
        loadingTimeline.play();
    }

    public Scene getScene() {
        return scene;
    }
}