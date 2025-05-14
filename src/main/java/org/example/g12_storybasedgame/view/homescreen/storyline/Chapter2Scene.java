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

public class Chapter2Scene {
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

    private final String PINK_BG = "-fx-background-color: #FFD6E0;";
    private final String DARK_PINK = "#FF85A2";
    private final String LIGHT_PINK = "#FFC2D1";
    private final String TEXT_COLOR = "#5E2D40";

    public Chapter2Scene(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.root = new BorderPane();
        setupUI();
        this.scene = new Scene(root, 1024, 768);
        loadOpeningMessages();
        showNextMessage();

        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> handleContinue());
    }

    private void setupUI() {
        root.setStyle(PINK_BG + " -fx-background-radius: 10;");

        characterImageView = new ImageView();
        characterImageView.setFitWidth(350);
        characterImageView.setFitHeight(500);
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
        dialogText.setPrefSize(900, 180);
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
                new String[]{" ", "BZZT BZZT"},
                new String[]{"Abita (Inner monologue)", "Come on, Mara. Pick up. Pick up. Please pick up."},
                new String[]{" ", "You frantically tap out another message to Mara."},
                new String[]{" ", "Text Message Sent: \"Hey! Where are you? You didn't answer yesterday and now you're not in class. Text me back, okay?\""},
                new String[]{" ", "No reply."},
                new String[]{"Abita (Inner monologue)", "This isn't like her. She always texts back, even if it's just an emoji. Something's wrong."},
                new String[]{" ", "Shoes tapping rapidly on tile floor"},
                new String[]{" ", "You slip out of the classroom before the whispers can catch up to you."},
                new String[]{" ", "Before Emilio can even say your name, you're halfway down the corridor."},
                new String[]{" ", "You lock eyes with:"}
        ));
    }

    private void loadInitialChoices() {
        messageQueue.clear();
        currentSection = 2;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Judas"},
                new String[]{"Option 2", "Dylan"}
        );
    }

    private void loadJudasPath() {
        showCharacterImage("judas.png");
        messageQueue.clear();
        currentSection = 3;

        messageQueue.addAll(Arrays.asList(
                new String[]{"Abita (Inner monologue)", "That was so weird, why did he look at me? Anyway, I just need to check. Maybe she overslept. Maybe she's sick. Maybe she"},
                new String[]{" ", "Your thoughts freeze mid-sentence as you run back to your dorms and up the stairs."},
                new String[]{" ", "Yellow police tape. Uniformed officers. Investigators with gloves."},
                new String[]{" ", "Everything slows down. A low ringing fills your ears. Someone is talking, but the words blur together."},
                new String[]{"Officer 1", "Please step back, this area is restricted."},
                new String[]{"Investigator", "Female student, early-twenties... Signs of struggle... Possible time of death... late last night..."},
                new String[]{" ", "You can't breath. You can't think. You try to step forward again."},
                new String[]{"Officer 2", "I said back up! This isn't for students."},
                new String[]{"Abita (Inner monologue)", "This can't be real. This isn't happening."},
                new String[]{" ", "You manage to catch a glimpse through the half-open dorm door. It's Mara's room."},
                new String[]{" ", "Photo frames knocked over. A broken lamp. A familiar scarf on the floor."},
                new String[]{" ", "You stumble back. The breath in your lungs feels too sharp, too cold."},
                new String[]{"Abita (Inner monologue)", "That scarf. That's hers. That's her favorite one. She wore it the last time I saw her."},
                new String[]{" ", "You turn around, legs trembling beneath you. Somehow, you make it back to your room."},
                new String[]{" ", "Click. Door closes. Lock turns."},
                new String[]{" ", "You drop your phone on the bed and sit there, unmoving."},
                new String[]{"Abita (Inner monologue)", "She's gone. She's really gone. Why didn't I check on her yesterday? Why didn't I feel it?"},
                new String[]{" ", "Tears slide down Abita's cheeks in silence."},
                new String[]{"Notification sound", "Ding"},
                new String[]{" ", "You glance at your phone."},
                new String[]{" ", "Text from Emilio: \"Are you okay? Where are you? Did you hear what they said?\""}
        ));

        dialogContainer.getChildren().clear();
        dialogContainer.getChildren().addAll(createTextbox());
        showNextMessage();
    }

    private void loadDylanPath() {
        showCharacterImage("dylan.png");
        messageQueue.clear();
        currentSection = 3;

        messageQueue.addAll(Arrays.asList(
                new String[]{"Abita (Inner monologue)", "That was so weird, why did he look at me? Anyway, I just need to check. Maybe she overslept. Maybe she's sick. Maybe she"},
                new String[]{" ", "Your thoughts freeze mid-sentence as you run back to your dorms and up the stairs."},
                new String[]{" ", "Yellow police tape. Uniformed officers. Investigators with gloves."},
                new String[]{" ", "Everything slows down. A low ringing fills your ears. Someone is talking, but the words blur together."},
                new String[]{"Officer 1", "Please step back, this area is restricted."},
                new String[]{"Investigator", "Female student, early-twenties... Signs of struggle... Possible time of death... late last night..."},
                new String[]{" ", "You can't breath. You can't think. You try to step forward again."},
                new String[]{"Officer 2", "I said back up! This isn't for students."},
                new String[]{"Abita (Inner monologue)", "This can't be real. This isn't happening."},
                new String[]{" ", "You manage to catch a glimpse through the half-open dorm door. It's Mara's room."},
                new String[]{" ", "Photo frames knocked over. A broken lamp. A familiar scarf on the floor."},
                new String[]{" ", "You stumble back. The breath in your lungs feels too sharp, too cold."},
                new String[]{"Abita (Inner monologue)", "That scarf. That's hers. That's her favorite one. She wore it the last time I saw her."},
                new String[]{" ", "You turn around, legs trembling beneath you. Somehow, you make it back to your room."},
                new String[]{" ", "Click. Door closes. Lock turns."},
                new String[]{" ", "You drop your phone on the bed and sit there, unmoving."},
                new String[]{"Abita (Inner monologue)", "She's gone. She's really gone. Why didn't I check on her yesterday? Why didn't I feel it?"},
                new String[]{" ", "Tears slide down Abita's cheeks in silence."},
                new String[]{"Notification sound ", "Ding"},
                new String[]{" ", "You glance at your phone."},
                new String[]{" ", "Text from Emilio: \"Are you okay? Where are you? Did you hear what they said?\""}
        ));

        dialogContainer.getChildren().clear();
        dialogContainer.getChildren().addAll(createTextbox());
        showNextMessage();
    }

    private void loadEmilioChoices() {
        messageQueue.clear();
        currentSection = 4;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Reply honestly"},
                new String[]{"Option 2", "Lie"},
                new String[]{"Option 3", "Leave him on read"}
        );
    }

    private void loadEmilioResponse(int choice) {
        showCharacterImage("emilio.png");
        messageQueue.clear();
        currentSection = 5; // Changed to 5 to lead to dream sequence

        switch(choice) {
            case 1 -> {
                relationshipPoints += 2;
                messageQueue.add(new String[]{"Abita", "It was Mara."});
            }
            case 2 -> {
                relationshipPoints -= 1;
                messageQueue.add(new String[]{"Abita", "I'm fine."});
            }
            case 3 -> {
                relationshipPoints -= 2;
                messageQueue.add(new String[]{" ", "(You don't reply)"});
            }
        }

        messageQueue.addAll(Arrays.asList(
                new String[]{" ", "You curl up on the bed, clutching your pillow."},
                new String[]{"Abita (Inner monologue)", "This isn't just a tragedy. This is personal. Someone killed Mara. And I'm going to find out who."},
                new String[]{" ", "Scene fades to black"}
        ));

        dialogContainer.getChildren().clear();
        dialogContainer.getChildren().addAll(createTextbox());
        showNextMessage();
    }

    private void loadDreamSequence() {
        messageQueue.clear();
        currentSection = 5;

        messageQueue.addAll(Arrays.asList(
                new String[]{" ", "Abita sleeps restlessly in her bed. The night is heavy, air cold."},
                new String[]{" ", "A voice calls faintly, distant but growing desperate."},
                new String[]{"Mara", "Abi....Abit....Abita!!"},
                new String[]{"Abita", "Huh...? That voice... Who's calling me? It feels so close, so familiar..."},
                new String[]{"Mara", "Abita!"},
                new String[]{" ", "Abita's eyes shoot open, before her stands Mara Rockrose, shimmering like a fading star, her expression urgent."},
                new String[]{"Abita", "MARA!! Wh-what are you doing here?! No... This is impossible... You, you're gone..."},
                new String[]{"Mara", "Abita, you have to listen to me and you have to listen well. It's really me, I don't have much time."},
                new String[]{"Abita", "But-"},
                new String[]{"Mara", "No time. You want to know who the murderer is. I know you do."},
                new String[]{"Mara", "And knowing you... you will find them. But I'm begging you, stop now."},
                new String[]{"Abita", "Mara... I need to avenge you! Tell me who did this!"},
                new String[]{"Mara", "I cannot tell you. I've been cursed, trapped between worlds."},
                new String[]{" ", "The shadows around Mara start to ripple, closing in."},
                new String[]{"Mara", "Please... stay alive."},
                new String[]{" ", "Mara's figure flickers and vanishes. The room is empty. Cold."},
                new String[]{" ", "Abita wakes up gasping, drenched in sweat."},
                new String[]{"Abita (Inner monologue)", "That wasn't just a dream... She was terrified."},
                new String[]{"Abita (Inner monologue)", "What am I supposed to do now?"}
        ));

        dialogContainer.getChildren().clear();
        dialogContainer.getChildren().addAll(createTextbox());
        showNextMessage();
    }

    private void loadFinalResponse() {
        messageQueue.clear();
        currentSection = 6;

        messageQueue.addAll(Arrays.asList(
                new String[]{" ", "It's the next morning."},
                new String[]{" ", "The sunlight feels thinner somehow. The memory of Mara's voice lingers."},
                new String[]{"Abita (Inner monologue)", "My head hurts... I have to make a decision..."}
        ));

        dialogContainer.getChildren().clear();
        dialogContainer.getChildren().addAll(createTextbox());
        showNextMessage();
    }

    private void loadFinalChoices() {
        messageQueue.clear();
        currentSection = 7;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Ignore Mara's warning"},
                new String[]{"Option 2", "Respect Mara's wishes"}
        );
    }

    private void handleFinalChoice(int choice) {
        switch(choice) {
            case 1 -> {
                relationshipPoints += 1; // Emilio
                relationshipPoints += 1; // Dylan
                relationshipPoints -= 1; // Judas
                relationshipPoints -= 1; // Benny
            }
            case 2 -> {
                relationshipPoints += 1; // Benny
                relationshipPoints += 1; // Emilio
                relationshipPoints -= 2; // Dylan
            }
        }
        currentSection = 8;
        showEndScreen();
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

        if (!currentMessage[0].equals(" ") && !currentMessage[0].contains("(Inner monologue)")) {
            showCharacterImage(currentMessage[0].toLowerCase() + ".png");
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
            return;
        }

        if (messageQueue.isEmpty()) {
            switch(currentSection) {
                case 4: // After Emilio response
                    currentSection = 5;
                    loadDreamSequence();
                    break;
                case 5: // After dream sequence
                    currentSection = 6;
                    loadFinalResponse();
                    break;
                case 6: // After final response
                    currentSection = 7;
                    loadFinalChoices();
                    break;
                default:
                    showCurrentChoices();
            }
        } else {
            showNextMessage();
        }
    }

    private void showCurrentChoices() {
        switch (currentSection) {
            case 1: loadInitialChoices(); break;
            case 3: loadEmilioChoices(); break;
            case 5: loadDreamSequence(); break;
            case 6: loadFinalResponse(); break;
            case 7: loadFinalChoices(); break;
            case 8: showEndScreen(); break;
        }
    }

    private void handleChoice(int choice) {
        switch (currentSection) {
            case 2:
                relationshipPoints += 1;
                if (choice == 1) loadJudasPath();
                else loadDylanPath();
                break;
            case 4:
                loadEmilioResponse(choice);
                break;
            case 7:
                handleFinalChoice(choice);
                break;
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

        Label endLabel = new Label("CHAPTER 2 COMPLETE");
        endLabel.setStyle("-fx-font-size: 32px; " +
                "-fx-text-fill: " + TEXT_COLOR + "; " +
                "-fx-font-family: 'Arial Rounded MT Bold';");

        Label pointsLabel = new Label("Total Relationship Points: " + relationshipPoints);
        pointsLabel.setStyle("-fx-font-size: 24px; " +
                "-fx-text-fill: " + DARK_PINK + "; " +
                "-fx-font-family: 'Arial Rounded MT Bold';");

        VBox buttonBox = new VBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        Button continueButton = new Button("Continue to Chapter 3");
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
        loadingContent.setAlignment(Pos.CENTER_LEFT);

        Label loadingLabel = new Label("Loading Chapter 3...");
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
                    loadingLabel.setText("Chapter 3 is not implemented yet");
                    progress.setVisible(false);
                })
        );
        loadingTimeline.play();
    }

    public Scene getScene() {
        return scene;
    }
}