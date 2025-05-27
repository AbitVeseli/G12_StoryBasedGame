package org.example.g12_storybasedgame.view.homescreen.storyline;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
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
    private String[] currentMessage;

    // Pink color scheme
    private final String PINK_BG = "-fx-background-color: #FFD6E0;";
    private final String DARK_PINK = "#FF85A2";
    private final String LIGHT_PINK = "#FFC2D1";
    private final String TEXT_COLOR = "#5E2D40";

    public Chapter1Scene(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.root = new BorderPane();
        setupUI(primaryStage);
        this.scene = new Scene(root, 1024, 768);
        loadOpeningMessages();
        showNextMessage();

        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> handleContinue());
//        scene.setOnKeyPressed(e -> {
//            if (e.getCode() == KeyCode.SPACE) {
//                handleContinue();
//            }
//        });

    }

    private void setupUI(Stage primaryStage) {
        // 1. Background with pink gradient
        root.setStyle(PINK_BG + " -fx-background-radius: 10;");

        // Character image container (left side)
        characterImageView = new ImageView();
        characterImageView.setFitWidth(350); // Du kan göra denna dynamisk också om det behövs
        characterImageView.setFitHeight(300);
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

        // Textbox header
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

        // Textbox content – nu med dynamisk storlek
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
        dialogText.setPadding(new Insets(15));


        // Gör så att textfältet blir 25% av scenens höjd
        primaryStage.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                dialogText.prefWidthProperty().bind(newScene.widthProperty().subtract(80)); // minus padding
                dialogText.prefHeightProperty().bind(newScene.heightProperty().multiply(0.15)); // 25% av höjden
            }
        });

        dialogContainer.getChildren().addAll(textboxHeader, dialogText);
        root.setBottom(dialogContainer);

        // Top bar med relation
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
                new String[]{"", "You suddenly wake up from sleep because you hear someone calling for you and see a golden bird."},
                new String[]{"Nugari", "Abita wake up!! You're going to be late to school!!"},
                new String[]{"Abita", "Ugh, Nugari you're being too loud. Give me 5 more minutes..."},
                new String[]{"Abita (Inner monologue)", "Wow, the weather's so nice today, a nice breeze and the sun is out, I have a good feeling about today. I should go look for Mara!"},
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
                messageQueue.add(new String[]{"Abita", "I’m sorry, I didn’t mean to bump into you."});
                messageQueue.add(new String[]{"???", "Watch where you're going!"});
            }
            case 2 -> {
                relationshipPoints -= 1;
                messageQueue.add(new String[]{"", "You continue walking..."});
                messageQueue.add(new String[]{"???", "HEY!!"});
            }
            case 3 -> {
                relationshipPoints -= 2;
                messageQueue.add(new String[]{"Abita", "I was here the whole time, are your eyes only for decoration?"});
                messageQueue.add(new String[]{"", "You walk away."});
                messageQueue.add(new String[]{"???", "Wha..."});
            }
        }

        messageQueue.addAll(Arrays.asList(
                new String[]{"", "As you walk away from the encounter you look after the student who you just bumped into."},

                new String[]{"Abita", "Black colly hair, undone tie, rude attitude. It's definitely *Judas Blackthorne*."},

                new String[]{"Abita", "Ugh to bump into him of all people today. This totally ruins my mood."},
                new String[]{"Abita", "Now where the heck is Mara?!"},
                new String[]{"", "In a bad mood you enter the classroom and hear the chatter from your fellow classmates."},
                new String[]{"", "*Chatter* *Chatter*"},

                new String[]{"Abita (Inner monologue)", "Why are there so many people here today? Let's find a place to sit first."},
                new String[]{"", "You see a group of people surround Dylan T. Ravenwood."},
                new String[]{"", "Dylan is a popular kid and comes from a noble ancestry, admired by all. "},
                new String[]{"", "From a distance you hear their conversations."},

                new String[]{"Fangirl 1", "Hahahh Dylan you're so funny!"},
                new String[]{"Fanboy 1", "Of course he is. He's the best at everything!"},
                new String[]{"Dylan", "Now, now guys, I'm flattered, but I'm not that special."},
                new String[]{"Both fans in unsion", "NO! Your looks, your brains, your FAMILY. You're superior in every way!!"},
                new String[]{"Abita (Inner monologue)", "I feel eyes looking at me in the back of my head."},
                new String[]{"", "You turn around."},
                new String[]{"Abita (Inner monologue)", "Why is Dylan T. Ravenwood looking at me?"}
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
            showCurrentChoices();
            return;
        }

        currentMessage = messageQueue.poll();
        animateText(currentMessage[0], currentMessage[1]);

        // Show character image based on speaker
        if (!currentMessage[0].equals("Textbox") && !currentMessage[0].equals(" ")) {
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
            // Skip animation and show full text
            textAnimation.stop();
            dialogText.setText(currentMessage[1]);
            isAnimating = false;
        } else {
            showNextMessage();
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

            buttonRow.getChildren().add(btn);
        }

        choicesBox.getChildren().addAll(promptLabel, buttonRow);
        dialogContainer.getChildren().add(choicesBox);
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
                new String[]{"Option 1", "Look and wave"},
                new String[]{"Option 2", "Look and furrow your brows"},
                new String[]{"Option 3", "Don't look back"}
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
                new String[]{"Abita (Inner monologue)", "Ugh, that was so uncomfortable. Let's find a familiar face instead. Oh! It's Emilio!"},
                new String[]{"", "You sit down next to Emilio Valmont. You've sat next to him since your first day at school and you share a comfortable and friendly bond with him despite his scary demeanor."},
                new String[]{"Abita", "Valmont, have you heard from Mara?"},
                new String[]{"Emilio", "Hey Abita, lovely to see you too, how are you?"},
                new String[]{"Abita", "Ha ha ha, I'm great, how are you? No but for real, I haven't been able to get in touch with her since yesterday."},
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
                new String[]{"Option 1", "Nod and agree with him"},
                new String[]{"Option 2", "Reluctantly agree"},
                new String[]{"Option 3", "Disagree with him"}
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
                new String[]{"Abita (Inner monologue)", "Mara where the hell are you?"},
                new String[]{"", "The conversations calm down as the headmistress enters and heads toward the podium."},
                new String[]{" ", "*Click* *Clack* *Click* *Clack* *Click* *Clack*"},
                new String[]{"", "Her shoes can be heard loudly."},
                new String[]{"", "Classroom continues to bustle with noise."},
                new String[]{"Headmistress", "SILENCE."},
                new String[]{"", "The whole classroom goes silent."},
                new String[]{"Headmistress", "My beloved students. My name is Sabrina Wharner and I am your headmistress in case some of you've missed that."},
                new String[]{"Headmistress", "I am here today to deliver some unfortunate news. It saddens me as a teacher and fellow human to see a student of ours leave this world so early."},
                new String[]{"Headmistress", "On our school premises no less. All of your lessons for today are cancelled. Let's hold a silent moment for the deceased."},
                new String[]{"", "The classroom is eerily silent as the headmistress puts her hand on her chest and lowers her head in silent prayer."},
                new String[]{"", "The classroom follows suit and before long the whole classroom starts being emptied out of students."},
                new String[]{"Abita", "Where the hell is Mara."}
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