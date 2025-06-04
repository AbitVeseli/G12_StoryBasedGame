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

public class Chapter8SceneJudas {
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

    public Chapter8SceneJudas(Stage primaryStage) {
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
                new String[]{"", "The world shifts between consciousness and void, your pulse sluggish, limbs too heavy to move."},
                new String[]{"", "A distant voice calls for you, panicked, raw."},
                new String[]{"Judas", "Abita! Damn it, don't do this, wake up!"},
                new String[]{"", "Something brushes your cheek, a desperate touch, grounding you, pulling you back to reality."},
                new String[]{"", "Your eyelids flutter open. The dim light flickers against the cracked walls of an underground chamber, eerie glyphs pulsing along the surfaces."},
                new String[]{"", "The scent of damp stone and magic lingers."},
                new String[]{"", "This isn't the ballroom anymore."},
                new String[]{"", "This is something older. Something hidden."},
                new String[]{"", "And standing just feet away, is Dylan Ravenwood."},
                new String[]{"Dylan", "Welcome to the end of the line."},
                new String[]{"", "Your head throbs, vision still swimming as you struggle to sit up."},
                new String[]{"", "The stone beneath you is slick with moisture, the air thick with ancient magic."},
                new String[]{"", "Judas is crouched beside you, grip tight on his dagger, eyes locked onto Dylan with an expression colder than you've ever seen before."},
                new String[]{"Abita", "What... What is this place?"},
                new String[]{"Dylan", "A graveyard for secrets. A place this academy keeps tucked beneath its pristine halls, where the real magic happens."},
                new String[]{"Dylan", "The rituals. The sacrifices. The binding of power."},
                new String[]{"", "The words drip with amusement, but the weight of them settles like lead in your chest."},
                new String[]{"Judas", "You killed Mara."},
                new String[]{"Dylan", "She was in the way. Just like Caela. And now... it looks like history is repeating itself."},
                new String[]{"", "Your pulse spikes. The pieces snap together."},
                new String[]{"", "Mara wasn't just murdered. She was silenced. Just like Caela."},
                new String[]{"Dylan", "You have two choices, Vesela. Keep pretending you're above this, or open your eyes and see how this world really works."},
                new String[]{"", "You glance at Judas. He hasn't moved. He hasn't spoken. He hasn't denied anything."},
                new String[]{"Abita", "Judas...?"},
                new String[]{"Judas", "You think I had a choice?"},
                new String[]{"Abita", "You knew. You knew about all of this."},
                new String[]{"Judas", "I tried to stop it. I tried to find another way. But there isn't one, Abita. There never was."},
                new String[]{"Dylan", "And here I thought you were smarter than her."},
                new String[]{"Abita", "Tell me it was all a lie."},
                new String[]{"", "Judas grips his pendant like it's the only thing keeping him tethered to himself."},
                new String[]{"Judas", "I couldn't stop it, Abita."},
                new String[]{"Dylan", "There it is. The moment of clarity. The realization that this was never about justice, it was about survival."},
                new String[]{"Dylan", "So what's it going to be, Vesela? Do you fight the inevitable, or do you finally understand?"}
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
                new String[]{"Option 1", "Trust Judas, redemption"},
                new String[]{"Option 2", "Betrayal, Judas was always part of this"}
        );
    }

    private void loadFinalResponse(int choice) {
        messageQueue.clear();
        currentSection = 3;

        if (choice == 1) {
            // Good Ending
            messageQueue.addAll(Arrays.asList(
                    new String[]{"Abita", "You told me to stop searching. You told me some truths should stay buried. But you also never let me face this alone."},
                    new String[]{"", "Judas braces himself, dagger tight in his grip, his breath slow and controlled despite the chaos pressing in."},
                    new String[]{"Dylan", "Do you really think you can walk out of here, Blackthorne? After everything?"},
                    new String[]{"Abita", "We're leaving. Both of us."},
                    new String[]{"Dylan", "That's adorable. Really. What makes you think they'll let you?"},
                    new String[]{"Abita", "Because you won't be here to stop us."},
                    new String[]{"Dylan", "You don't understand how deep this goes. This academy isn't just a school—it's built on blood and sacrifice."},
                    new String[]{"Judas", "Watch us."},
                    new String[]{"", "Judas strikes. The battle is swift, desperate, magic crashing through the chamber in waves."},
                    new String[]{"", "Dylan snarls, hurling spells in rapid succession, but Judas is relentless."},
                    new String[]{"", "The dagger finds its mark, Dylan stumbles, his magic faltering."},
                    new String[]{"Judas", "Run."},
                    new String[]{"", "You don't hesitate. Together, you tear through the hidden tunnels, escaping into the cold night."},
                    new String[]{"", "For the first time, Judas is free and he is by your side, with the truth."}
            ));
        } else {
            // Bad Ending
            messageQueue.addAll(Arrays.asList(
                    new String[]{"Abita", "You let this happen."},
                    new String[]{"Judas", "I didn't"},
                    new String[]{"Abita", "You could have done something. You could have fought."},
                    new String[]{"Judas", "And then what? You think I'd still be standing here if I had?"},
                    new String[]{"Dylan", "She finally sees it, doesn't she?"},
                    new String[]{"Abita", "You let them kill her."},
                    new String[]{"", "Judas inhales sharply. His eyes flicker with regret, rage, weakness, but not denial."},
                    new String[]{"Dylan", "Mara thought she could change things too. And look where that got her."},
                    new String[]{"", "Before you can run, before you can fight, the magic slams into your chest."},
                    new String[]{"", "A sharp, suffocating impact, like drowning in fire."},
                    new String[]{"", "You stumble, gasping, vision flickering, limbs numbing."},
                    new String[]{"", "The last thing you see is Judas reaching for you, but it is too late."},
                    new String[]{"", "Your body collapses. The chamber spins, the shadows swallowing every last bit of warmth."},
                    new String[]{"Dylan", "A shame. I had hopes for you."},
                    new String[]{"", "Judas doesn't move. Doesn't speak. Doesn't stop him."},
                    new String[]{"", "And just like Mara. Just like Caela."},
                    new String[]{"", "You become another ghost in the academy's blood-soaked legacy."},
                    new String[]{"", "Judas will carry the weight of it forever."}
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

        Label promptLabel = new Label("Final Decision - How This Ends");
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