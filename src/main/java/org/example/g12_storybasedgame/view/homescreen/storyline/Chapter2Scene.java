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

    // Color scheme
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
        // Lägg till bakgrundsbild
        try {
            Image bgImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/background2.jpg")));
            BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
            BackgroundImage backgroundImage = new BackgroundImage(
                    bgImage,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    backgroundSize
            );
            root.setBackground(new Background(backgroundImage));
        } catch (Exception e) {
            System.err.println("Kunde inte ladda bakgrundsbild: " + e.getMessage());
            root.setStyle(PINK_BG + " -fx-background-radius: 10;");
        }

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

        speakerLabel = new Label("Textbox");
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
                new String[]{"Textbox", "You immediately text your best friend to check where she is..."},
                new String[]{"Textbox", "She hasn't responded since yesterday and isn't in class."},
                new String[]{"Abita", "Come on, Mara. Pick up. Pick up. Please pick up."},
                new String[]{"Textbox", "You frantically tap out another message to Mara."},
                new String[]{"Textbox", "Text Message Sent: \"Hey! Where are you? You didn't answer yesterday and now you're not in class. Text me back, okay?\""},
                new String[]{"Textbox", "No reply."},
                new String[]{"Abita", "This isn't like her. She always texts back, even if it's just an emoji. Something's wrong."},
                new String[]{"Textbox", "You slip out of the classroom before the whispers can catch up to you."},
                new String[]{"Textbox", "Before Emilio can even say your name, you're halfway down the corridor."},
                new String[]{"Textbox", "You lock eyes with:"}
        ));
    }

    private void loadInitialChoices() {
        messageQueue.clear();
        currentSection = 2;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Approach Judas (+1 relationship)"},
                new String[]{"Option 2", "Approach Dylan (+1 relationship)"}
        );
    }

    private void loadJudasPath() {
        showCharacterImage("judas.png");
        messageQueue.clear();
        currentSection = 3;

        messageQueue.addAll(Arrays.asList(
                new String[]{"Judas", "(Looks surprised as you approach)"},
                new String[]{"Textbox", "Your thoughts freeze mid-sentence as you run back to your dorms and up the stairs."},
                new String[]{"Textbox", "Yellow police tape. Uniformed officers. Investigators with gloves."},
                new String[]{"Textbox", "Everything slows down. A low ringing fills your ears."},
                new String[]{"Officer", "Please step back, this area is restricted."},
                new String[]{"Investigator", "Female student, early-twenties... Signs of struggle... Possible time of death... late last night..."},
                new String[]{"Officer", "I said back up! This isn't for students."},
                new String[]{"Abita", "This can't be real. This isn't happening."},
                new String[]{"Textbox", "You manage to catch a glimpse through the half-open dorm door. It's Mara's room."},
                new String[]{"Textbox", "Photo frames knocked over. A broken lamp. A familiar scarf on the floor."},
                new String[]{"Abita", "That scarf. That's hers. That's her favorite one. She wore it the last time I saw her."},
                new String[]{"Textbox", "You turn around, legs trembling beneath you. Somehow, you make it back to your room."},
                new String[]{"Textbox", "Click. Door closes. Lock turns."},
                new String[]{"Textbox", "You drop your phone on the bed and sit there, unmoving."},
                new String[]{"Abita", "She's gone. She's really gone. Why didn't I check on her yesterday? Why didn't I feel it?"},
                new String[]{"Textbox", "Tears slide down Abita's cheeks in silence."},
                new String[]{"Textbox", "Notification sound: Ding"},
                new String[]{"Textbox", "You glance at your phone."},
                new String[]{"Textbox", "Text from Emilio: \"Are you okay? Where are you? Did you hear what they said?\""}
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
                new String[]{"Dylan", "(Looks concerned as you approach)"},
                new String[]{"Textbox", "Your thoughts freeze mid-sentence as you run back to your dorms and up the stairs."},
                new String[]{"Textbox", "Yellow police tape. Uniformed officers. Investigators with gloves."},
                new String[]{"Textbox", "Everything slows down. A low ringing fills your ears."},
                new String[]{"Officer", "Please step back, this area is restricted."},
                new String[]{"Investigator", "Female student, early-twenties... Signs of struggle... Possible time of death... late last night..."},
                new String[]{"Officer", "I said back up! This isn't for students."},
                new String[]{"Abita", "This can't be real. This isn't happening."},
                new String[]{"Textbox", "You manage to catch a glimpse through the half-open dorm door. It's Mara's room."},
                new String[]{"Textbox", "Photo frames knocked over. A broken lamp. A familiar scarf on the floor."},
                new String[]{"Abita", "That scarf. That's hers. That's her favorite one. She wore it the last time I saw her."},
                new String[]{"Textbox", "You turn around, legs trembling beneath you. Somehow, you make it back to your room."},
                new String[]{"Textbox", "Click. Door closes. Lock turns."},
                new String[]{"Textbox", "You drop your phone on the bed and sit there, unmoving."},
                new String[]{"Abita", "She's gone. She's really gone. Why didn't I check on her yesterday? Why didn't I feel it?"},
                new String[]{"Textbox", "Tears slide down Abita's cheeks in silence."},
                new String[]{"Textbox", "Notification sound: Ding"},
                new String[]{"Textbox", "You glance at your phone."},
                new String[]{"Textbox", "Text from Emilio: \"Are you okay? Where are you? Did you hear what they said?\""}
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
                new String[]{"Option 1", "Reply honestly (+2 relationship)"},
                new String[]{"Option 2", "Lie (-1 relationship)"},
                new String[]{"Option 3", "Leave him on read (-2 relationship)"}
        );
    }

    private void loadEmilioResponse(int choice) {
        messageQueue.clear();
        currentSection = 5;

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
                messageQueue.add(new String[]{"Textbox", "(You don't reply)"});
            }
        }

        messageQueue.addAll(Arrays.asList(
                new String[]{"Textbox", "You curl up on the bed, clutching your pillow."},
                new String[]{"Abita", "This isn't just a tragedy. This is personal. Someone killed Mara. And I'm going to find out who."},
                new String[]{"Textbox", "Scene fades to black"}
        ));

        dialogContainer.getChildren().clear();
        dialogContainer.getChildren().addAll(createTextbox());
        showNextMessage();
    }

    private void loadDreamSequence() {
        messageQueue.clear();
        currentSection = 6;

        messageQueue.addAll(Arrays.asList(
                new String[]{"Textbox", "(Mara Rockrose visits MC overnight)"},
                new String[]{"Textbox", "Abita sleeps restlessly in her bed. The night is heavy, air cold."},
                new String[]{"Mara", "Abi....Abit....Abita!!"},
                new String[]{"Abita", "Huh...? That voice... Who's calling me? It feels so close, so familiar..."},
                new String[]{"Mara", "Abita!"},
                new String[]{"Textbox", "Abita's eyes shoot open, before her stands Mara Rockrose, shimmering like a fading star."},
                new String[]{"Abita", "MARA!! Wh-what are you doing here?! No... This is impossible... You, you're gone..."},
                new String[]{"Mara", "Abita, you have to listen to me and you have to listen well. It's really me, I don't have much time."},
                new String[]{"Abita", "But-"},
                new String[]{"Mara", "No time. You want to know who the murderer is. I know you do."},
                new String[]{"Mara", "But I'm begging you, stop now. Stop looking. You can't fight them."},
                new String[]{"Abita", "Mara... Of course I need to avenge you! Tell me, who are they?!"},
                new String[]{"Mara", "I cannot tell you. I've been cursed, trapped between waking and dreaming."},
                new String[]{"Textbox", "The shadows around Mara start to ripple, closing in."},
                new String[]{"Mara", "This is all I can say. He's already searching for me. Abita, please, promise me, stop looking."},
                new String[]{"Abita", "But, Mara, I-"},
                new String[]{"Mara", "Please... stay alive."},
                new String[]{"Textbox", "Mara's figure flickers and vanishes. The room is empty. Cold."},
                new String[]{"Textbox", "Abita wakes up gasping, drenched in sweat."},
                new String[]{"Abita", "That wasn't just a dream... The look on her face... She was terrified."}
        ));

        showNextMessage();
    }

    private void loadFinalChoices() {
        messageQueue.clear();
        currentSection = 7;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Ignore Mara's warning (Vengeance path)"},
                new String[]{"Option 2", "Respect Mara's wishes (Peace path)"}
        );
    }

    private void loadFinalResponse(int choice) {
        messageQueue.clear();
        currentSection = 8;

        switch(choice) {
            case 1 -> {
                relationshipPoints += 1; // Emilio
                relationshipPoints += 1; // Dylan
                relationshipPoints -= 1; // Judas
                relationshipPoints -= 1; // Benny
                messageQueue.add(new String[]{"Abita", "(Determined) I can't stop. I won't stop until I find who did this."});
            }
            case 2 -> {
                relationshipPoints += 1; // Benny
                relationshipPoints += 1; // Emilio
                relationshipPoints -= 2; // Dylan
                messageQueue.add(new String[]{"Abita", "(Tearfully) I... I'll try to let go. For you, Mara."});
            }
        }

        messageQueue.addAll(Arrays.asList(
                new String[]{"Textbox", "The sunlight feels thinner somehow. The memory of Mara's voice clings to Abita like frost."},
                new String[]{"Abita", "My head hurts... I have to make a decision..."}
        ));

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

        String[] message = messageQueue.poll();
        animateText(message[0], message[1]);

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
            dialogText.setText(messageQueue.peek()[1]);
            speakerLabel.setText(messageQueue.peek()[0]);
            isAnimating = false;
            return;
        }

        if (messageQueue.isEmpty()) {
            showCurrentChoices();
        } else {
            showNextMessage();
        }
    }

    private void showCurrentChoices() {
        switch (currentSection) {
            case 1 -> loadInitialChoices();
            case 3 -> loadEmilioChoices();
            case 5 -> loadDreamSequence();
            case 6 -> loadFinalChoices();
            case 7 -> showEndScreen();
            default -> showNextMessage();
        }
    }

    private void showChoiceButtons(String[]... options) {
        HBox choicesBox = new HBox(15);
        choicesBox.setAlignment(Pos.CENTER);
        choicesBox.setStyle("-fx-background-color: rgba(255,255,255,0.8); " +
                "-fx-background-radius: 10; " +
                "-fx-padding: 30;");

        Label promptLabel = new Label("How do you respond?");
        promptLabel.setStyle("-fx-font-size: 20px; " +
                "-fx-text-fill: " + TEXT_COLOR + "; " +
                "-fx-font-family: 'Arial Rounded MT Bold';");

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
            case 2 -> {
                relationshipPoints += 1;
                if (choice == 1) loadJudasPath();
                else loadDylanPath();
            }
            case 4 -> loadEmilioResponse(choice);
            case 7 -> loadFinalResponse(choice);
        }
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
        loadingContent.setAlignment(Pos.CENTER);

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

                    Button backBtn = new Button("Return to Storyline Map");
                    backBtn.setStyle("-fx-font-size: 18px; " +
                            "-fx-text-fill: white; " +
                            "-fx-background-color: " + DARK_PINK + "; " +
                            "-fx-padding: 10 20; " +
                            "-fx-background-radius: 20;");
                    backBtn.setOnAction(event -> {
                        StorylineScene storylineScene = new StorylineScene(primaryStage);
                        primaryStage.setScene(storylineScene.getScene());
                    });
                    loadingContent.getChildren().add(backBtn);
                })
        );
        loadingTimeline.play();
    }

    public Scene getScene() {
        return scene;
    }
}