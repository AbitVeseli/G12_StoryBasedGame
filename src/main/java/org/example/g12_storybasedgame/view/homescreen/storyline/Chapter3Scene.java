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

public class Chapter3Scene {
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

    public Chapter3Scene(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.root = new BorderPane();
        setupUI();
        this.scene = new Scene(root, 1024, 768);
        loadOpeningMessages();
        showNextMessage();

        scene.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e -> handleContinue());
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
        dialogText.setPrefSize(900, 180);
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
                new String[]{"", "The next day you rush to school to see whether new information has been released."},
                new String[]{"", "Surprisingly, everyone around you appears to be unbothered by the previous day's events."},
                new String[]{"Abita", " (Inner monologue) What the hell...? Is this a joke? Mara's gone. She died here. And everyone's just... pretending like it's a normal day?"},
                new String[]{"", "The teacher walks in but someone else walks in after her and stops beside her."},
                new String[]{"Teacher", "Class, attention please! We have a new transfer student joining us today. Please welcome Benny Blade Vi."},
                new String[]{"Benny", "Yo. Nice to meet you all."},
                new String[]{"", "Chatter erupts in the classroom and it's as if yesterday's murder didn't happen."},
                new String[]{"Female student", "Who is that?"},
                new String[]{"Male student", "Do you see his cool eyes?"},
                new String[]{"Female student", "He's kinda hot… But why now?"},
                new String[]{"Abita", " (Inner monologue) A transfer student? The day after a murder? Not even a full 24 hours have passed. That's more than suspicious…"},
                new String[]{"", "You glance over at your classmates. Emilio is staring at the new guy, brow furrowed. Judas remains slouched in his seat, unfazed. Dylan meets your eyes and offers a smooth smirk."}
        ));
    }

    private void loadDylanChoices() {
        messageQueue.clear();
        currentSection = 2;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Look away quickly"},
                new String[]{"Option 2", "Raise an eyebrow "},
                new String[]{"Option 3", "Stare coldly"}
        );
    }

    private void loadDylanResponse(int choice) {
        showCharacterImage("dylan.png");
        messageQueue.clear();
        currentSection = 3;

        switch(choice) {
            case 1 -> relationshipPoints -= 1;
            case 2 -> relationshipPoints += 1;
            case 3 -> relationshipPoints += 0;
        }

        messageQueue.addAll(Arrays.asList(
                new String[]{"Abita", " (Inner monologue) That look again. He's watching me like he knows something..."},
                new String[]{"Abita", " (Inner monologue) Fine. Two can play that game."},
                new String[]{"", "You look away from Dylan and move your attention toward Emilio."},
                new String[]{"Abita", "Hey, Valmont."},
                new String[]{"Emilio", "Hey. You okay? You don't look like you slept a lot."},
                new String[]{"Abita", "I didn't. I… I had a dream. A weird one. Mara came to me in that dream."},
                new String[]{"Emilio", "Mara…?"},
                new String[]{"Abita", "She told me to stop looking for her killer. She looked... terrified."},
                new String[]{"Emilio", "Maybe... Maybe you should listen to her."}
        ));

        dialogContainer.getChildren().clear();
        dialogContainer.getChildren().addAll(createTextbox());
        showNextMessage();
    }

    private void loadEmilioChoices1() {
        messageQueue.clear();
        currentSection = 4;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Nod and agree"},
                new String[]{"Option 2", "Reluctantly agree"}
        );
    }

    private void loadEmilioResponse1(int choice) {
        showCharacterImage("emilio.png");
        messageQueue.clear();
        currentSection = 5;

        switch(choice) {
            case 1 -> relationshipPoints += 1;
            case 2 -> relationshipPoints -= 1;
        }

        messageQueue.addAll(Arrays.asList(
                new String[]{"Abita", "But I can't just stop. She was my best friend. That dream... if it was her, then it was a warning. But also a sign. I have to know the truth."},
                new String[]{"Teacher", "Alright class, settle down. You can talk to him later and make him feel welcome here. Let's begin the class."},
                new String[]{"", "Suspicious of the new transfer student and filled with a need to avenge your friend you set out to uncover the mysteries surrounding her death."},
                new String[]{"", "The investigation begins. You start gathering clues after class, try talking to potential witnesses but they don't seem to have any useful information."},
                new String[]{"Abita", " (Inner monologue) I've asked a lot of people but something seems off about everyone lately."},
                new String[]{"", "While investigating you find yourself in the library. You see Emilio there and decide to start talking to him."},
                new String[]{"Abita", "Hey Emilio. You seemed nervous this morning. What's going on?"},
                new String[]{"Emilio", "Nervous? Me? Nah, I'm just... bad with new people."},
                new String[]{"Abita", "Are you sure that's it?"},
                new String[]{"Emilio", "No, it's about Mara and other things.. Are still digging, aren't you?"},
                new String[]{"Abita", "Of course I am. I know what she meant to me. And you.. You cared about her too, didn't you?"},
                new String[]{"Emilio", "I do but I heard something the night she died. But I don't want you getting dragged deeper into this. It's dangerous."},
                new String[]{"Abita", "But I have to know the truth of what happened to Mara."}
        ));

        dialogContainer.getChildren().clear();
        dialogContainer.getChildren().addAll(createTextbox());
        showNextMessage();
    }

    private void loadEmilioChoices2() {
        messageQueue.clear();
        currentSection = 6;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Gently press Emilio for more information"},
                new String[]{"Option 2", "Get angry and accuse Emilio"}
        );
    }

    private void loadEmilioResponse2(int choice) {
        showCharacterImage("emilio.png");
        messageQueue.clear();
        currentSection = 7;

        switch(choice) {
            case 1 -> {
                relationshipPoints += 1;
                messageQueue.add(new String[]{"Abita", "You can trust me. If you know something, anything, I need it. For Mara."});
            }
            case 2 -> {
                relationshipPoints -= 1;
                messageQueue.add(new String[]{"Abita", "If you know something, stop dancing around it. Are you protecting them? Or yourself?"});
            }
        }

        messageQueue.addAll(Arrays.asList(
                new String[]{"Emilio", "Alright I'll tell you. I heard voices near the greenhouse. It sounded like a big argument but I didn't see who it was, so hurriedly walked away. But... one voice sounded familiar.I just don't know from where yet."},
                new String[]{"Abita", "If you remember or see anything else promise you'll tell me."},
                new String[]{"Emilio", "I will. Just… be careful, Abita. I'm here if you need help. Promise me that"},
                new String[]{"", "You walk out of the library and find Benny standing alone with his hands in his pockets and gazing somewhere distant."},
                new String[]{"Abita", "You transferred here right after someone died. That's not just a coincidence. It almost seem calculated by someone"},
                new String[]{"Benny", "You're direct. I like that. But let me give you some advice, stop looking. This place? It's darker than it looks."}
        ));

        dialogContainer.getChildren().clear();
        dialogContainer.getChildren().addAll(createTextbox());
        showNextMessage();
    }

    private void loadBennyChoices() {
        messageQueue.clear();
        currentSection = 8;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Back down"},
                new String[]{"Option 2", "Keep your stance"},
                new String[]{"Option 3", "Get hostile"}
        );
    }

    private void loadBennyResponse(int choice) {
        showCharacterImage("benny.png");
        messageQueue.clear();
        currentSection = 9;

        switch(choice) {
            case 1 -> {
                relationshipPoints += 1;
                messageQueue.add(new String[]{"Abita", "...Alright. I get it. I'm not trying to make things worse. But I'm still looking for answers. Just... keep an eye out for me, okay?"});
            }
            case 2 -> {
                relationshipPoints += 0;
                messageQueue.add(new String[]{"Abita", "I'm not backing down. If anything happens to me, it's on you. But I'm going to get to the bottom of this, with or without your help."});
            }
            case 3 -> {
                relationshipPoints -= 1;
                messageQueue.add(new String[]{"Abita", "You think you can scare me into giving up? I'm not afraid of you, Benny. I'll find out what's going on, even if it means crossing every line you've set."});
                messageQueue.add(new String[]{"Benny", "You don't get it, do you? You're playing a dangerous game, and sooner or later, you'll find out just how much danger you're really in. But if you think you can handle it, then go ahead. Just don't say I didn't warn you."});
                messageQueue.add(new String[]{"Abita", "Thanks, but I'm not here to play nice. I'll find out what's going on with or without your help."});
                messageQueue.add(new String[]{"Benny", "Just try not to end up in the same place as your friend. These are dangerous times."});
            }
        }

        messageQueue.addAll(Arrays.asList(
                new String[]{"Abita", " (Inner monologue) He's trying to intimidate me. But it's not working. He knows more than he's letting on, and I'm going to find out what that is."},
                new String[]{"", "You walk outside and find Judas leaning against a brick wall, half in shadow. His arms are crossed, expression unreadable. His eyes flick briefly toward you."},
                new String[]{"Abita", "You haven't said anything since Mara died. Don't you care?"},
                new String[]{"Judas", "Caring doesn't bring the dead back. And asking the wrong questions gets more people killed."}
        ));

        dialogContainer.getChildren().clear();
        dialogContainer.getChildren().addAll(createTextbox());
        showNextMessage();
    }

    private void loadJudasChoices() {
        messageQueue.clear();
        currentSection = 10;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Agree with him"},
                new String[]{"Option 2", "Push back"},
                new String[]{"Option 3", "Demand answers"}
        );
    }

    private void loadJudasResponse(int choice) {
        showCharacterImage("judas.png");
        messageQueue.clear();
        currentSection = 11;

        switch(choice) {
            case 1 -> {
                relationshipPoints += 1;
                messageQueue.add(new String[]{"Abita", "Maybe you're right. Maybe justice is just a fantasy. But I still have to try."});
            }
            case 2 -> {
                relationshipPoints -= 1;
                messageQueue.add(new String[]{"Abita", "Just because you gave up on people doesn't mean I will."});
            }
            case 3 -> {
                relationshipPoints -= 2;
                messageQueue.add(new String[]{"Abita", "Stop avoiding it. What do you know, Judas?"});
            }
        }

        messageQueue.addAll(Arrays.asList(
                new String[]{"Judas", "You want justice? Justice is a myth. There's only truth and most people can't handle it."},
                new String[]{"Abita", "Try me."},
                new String[]{"Judas", "You don't know what you're asking for."},
                new String[]{"", "He pushes off the wall, brushing past you. As he walks away, his voice trails behind quiet, but heavy."},
                new String[]{"Judas", "Some truths don't stay buried without reason."},
                new String[]{"", "You watch Judas disappear around the corner, his words lingering in your mind like smoke. Shaking it off, you make your way toward the courtyard."},
                new String[]{"", "Before you can catch your breath, a voice cuts in smoothly beside you. It's Dylan."},
                new String[]{"Dylan", "Well, well… Detective Vesela. Heard you've been snooping around."},
                new String[]{"Abita", "Are you surprised? Or… worried?"}
        ));

        dialogContainer.getChildren().clear();
        dialogContainer.getChildren().addAll(createTextbox());
        showNextMessage();
    }

    private void loadDylanFinalChoices() {
        messageQueue.clear();
        currentSection = 12;

        dialogContainer.getChildren().clear();
        showChoiceButtons(
                new String[]{"Option 1", "Flirt back"},
                new String[]{"Option 2", "Challenge him"},
                new String[]{"Option 3", "Stay cold"}
        );
    }

    private void loadDylanFinalResponse(int choice) {
        showCharacterImage("dylan.png");
        messageQueue.clear();
        currentSection = 13;

        switch(choice) {
            case 1 -> {
                relationshipPoints += 1;
                messageQueue.add(new String[]{"Abita", "Maybe I just wanted your attention. Looks like it worked."});
            }
            case 2 -> {
                relationshipPoints -= 1;
                messageQueue.add(new String[]{"Abita", "Only guilty people get nervous when someone starts asking questions."});
            }
            case 3 -> {
                relationshipPoints += 0;
                messageQueue.add(new String[]{"Abita", "I'm not here to impress you, Dylan. Just tell me what you know."});
            }
        }

        messageQueue.addAll(Arrays.asList(
                new String[]{"Dylan", " (amused, with a hint of menace) You're bold. That might just get you killed, you know. Or… worse, getting noticed by the wrong people."},
                new String[]{"", "You turn away from Dylan, his words echoing in your mind. With your steps firm and keeping your voice steady. You whispering to yourself"},
                new String[]{"Abita", "Too late for that. I'm already in this… and I'm not backing down now."}
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
            return;
        }

        if (!messageQueue.isEmpty()) {
            showNextMessage();
            return;
        }

        showCurrentChoices();
    }

    private void showCurrentChoices() {
        switch (currentSection) {
            case 1 -> loadDylanChoices();
            case 3 -> loadEmilioChoices1();
            case 5 -> loadEmilioChoices2();
            case 7 -> loadBennyChoices();
            case 9 -> loadJudasChoices();
            case 11 -> loadDylanFinalChoices();
            case 13 -> showEndScreen();
        }
    }

    private void handleChoice(int choice) {
        switch (currentSection) {
            case 2 -> loadDylanResponse(choice);
            case 4 -> loadEmilioResponse1(choice);
            case 6 -> loadEmilioResponse2(choice);
            case 8 -> loadBennyResponse(choice);
            case 10 -> loadJudasResponse(choice);
            case 12 -> loadDylanFinalResponse(choice);
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

        Label endLabel = new Label("CHAPTER 3 COMPLETE");
        endLabel.setStyle("-fx-font-size: 32px; " +
                "-fx-text-fill: " + TEXT_COLOR + "; " +
                "-fx-font-family: 'Arial Rounded MT Bold';");

        Label pointsLabel = new Label("Total Relationship Points: " + relationshipPoints);
        pointsLabel.setStyle("-fx-font-size: 24px; " +
                "-fx-text-fill: " + DARK_PINK + "; " +
                "-fx-font-family: 'Arial Rounded MT Bold';");

        VBox buttonBox = new VBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        Button continueButton = new Button("Continue to Chapter 4");
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

        Label loadingLabel = new Label("Loading Chapter 4...");
        loadingLabel.setStyle("-fx-font-size: 32px; " +
                "-fx-text-fill: " + TEXT_COLOR + "; " +
                "-fx-font-family: 'Arial Rounded MT Bold';");

        ProgressIndicator progress = new ProgressIndicator();
        progress.setStyle("-fx-progress-color: " + DARK_PINK + ";");
        progress.setPrefSize(100, 100);

        loadingContent.getChildren().addAll(loadingLabel, progress);
        loadingPane.getChildren().add(loadingContent);

        root.setCenter(loadingPane);

        // Create the loading timeline
        Timeline loadingTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    // First show loading progress
                    loadingLabel.setText("Almost there...");
                }),
                new KeyFrame(Duration.seconds(2), e -> {
                    try {
                        // Attempt to load Chapter 4
                        Chapter4Scene chapter4 = new Chapter4Scene(primaryStage);
                        primaryStage.setScene(chapter4.getScene());
                    } catch (Exception ex) {
                        // Fallback if Chapter4 isn't available
                        loadingLabel.setText("Chapter 4 is coming soon!");
                        progress.setVisible(false);

                        // Add a back button
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