package org.example.g12_storybasedgame.view.homescreen;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import org.example.g12_storybasedgame.view.homescreen.storyline.StorylineScene;
import org.example.g12_storybasedgame.view.homescreen.visit.CharacterSelectionScreen;
import org.example.g12_storybasedgame.view.menu.MenuManager;

public class Homescreen extends Application {

    private Stage primaryStage;

    private void showSettingsPopup() {
        Stage popupStage = new Stage();

        // Use initOwner instead of initModality for better fullscreen behavior on macOS
        popupStage.initOwner(primaryStage);
        popupStage.setTitle("Settings");

        // Explicit settings to make it a small popup
        popupStage.setFullScreen(false);
        popupStage.setMaximized(false);
        popupStage.setResizable(false);
        popupStage.setAlwaysOnTop(true);

        VBox popupLayout = new VBox(15);
        popupLayout.setPadding(new Insets(20));
        popupLayout.setStyle("-fx-background-color: #f0f8ff; -fx-border-color: black; -fx-border-width: 2px;");

        // Title
        Label titleLabel = new Label("Settings");
        titleLabel.setFont(Font.font("Arial", 18));
        titleLabel.setTextFill(Color.DARKBLUE);
        popupLayout.getChildren().add(titleLabel);

        // Add separator after title
        Separator separator = new Separator();
        popupLayout.getChildren().add(separator);

        // Add action buttons in a single row
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        // Fullscreen toggle button
        Button fullscreenButton = new Button(primaryStage.isFullScreen() ? "Exit Fullscreen" : "Enter Fullscreen");
        fullscreenButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-font-size: 14px;");
        fullscreenButton.setOnAction(e -> {
            popupStage.close();
            Platform.runLater(() -> toggleFullscreen());
        });

        // Logout button
        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-font-size: 14px;");
        logoutButton.setOnAction(e -> {
            popupStage.close();
            logout();
        });

        // Exit game button
        Button exitButton = new Button("Exit Game");
        exitButton.setStyle("-fx-background-color: #ff9800; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-font-size: 14px;");
        exitButton.setOnAction(e -> {
            popupStage.close();
            exitGame();
        });

        buttonBox.getChildren().addAll(fullscreenButton, logoutButton, exitButton);
        popupLayout.getChildren().add(buttonBox);

        Scene popupScene = new Scene(popupLayout, 400, 150);
        popupStage.setScene(popupScene);

        // Calculate position to center the popup
        double centerX = primaryStage.getX() + (primaryStage.getWidth() - 400) / 2;
        double centerY = primaryStage.getY() + (primaryStage.getHeight() - 150) / 2;
        popupStage.setX(centerX);
        popupStage.setY(centerY);

        popupStage.show();
    }

    private void toggleFullscreen() {
        try {
            boolean newFullscreenState = !primaryStage.isFullScreen();

            primaryStage.setFullScreenExitKeyCombination(null);

            new Thread(() -> {
                try {
                    Thread.sleep(100);
                    Platform.runLater(() -> {
                        primaryStage.setFullScreen(newFullscreenState);
                    });
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }).start();

        } catch (Exception e) {
            System.err.println("Error toggling fullscreen: " + e.getMessage());
            showAlert("Fullscreen Error", "Could not change fullscreen mode. Please try again.");
        }
    }

    private void showAlert(String title, String message) {
        Platform.runLater(() -> {
            Stage alertStage = new Stage();
            alertStage.initOwner(primaryStage); // Use initOwner here too
            alertStage.setTitle(title);
            alertStage.setFullScreen(false);
            alertStage.setMaximized(false);
            alertStage.setResizable(false);

            VBox alertLayout = new VBox(15);
            alertLayout.setPadding(new Insets(20));
            alertLayout.setAlignment(Pos.CENTER);
            alertLayout.setStyle("-fx-background-color: #ffe6e6; -fx-border-color: #ffcccc; -fx-border-width: 2px;");

            Label messageLabel = new Label(message);
            messageLabel.setFont(Font.font("Arial", 14));

            Button okButton = new Button("OK");
            okButton.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-padding: 8px 16px;");
            okButton.setOnAction(e -> alertStage.close());

            alertLayout.getChildren().addAll(messageLabel, okButton);

            Scene alertScene = new Scene(alertLayout, 300, 150);
            alertStage.setScene(alertScene);

            // Center the alert
            double centerX = primaryStage.getX() + (primaryStage.getWidth() - 300) / 2;
            double centerY = primaryStage.getY() + (primaryStage.getHeight() - 150) / 2;
            alertStage.setX(centerX);
            alertStage.setY(centerY);

            alertStage.show();
        });
    }

    private void logout() {
        Stage confirmStage = new Stage();
        confirmStage.initOwner(primaryStage); // Use initOwner here too
        confirmStage.setTitle("Logout Confirmation");
        confirmStage.setFullScreen(false);
        confirmStage.setMaximized(false);
        confirmStage.setResizable(false);

        VBox confirmLayout = new VBox(20);
        confirmLayout.setPadding(new Insets(20));
        confirmLayout.setAlignment(Pos.CENTER);
        confirmLayout.setStyle("-fx-background-color: #fff3cd; -fx-border-color: #ffeaa7; -fx-border-width: 2px;");

        Label messageLabel = new Label("Are you sure you want to logout?");
        messageLabel.setFont(Font.font("Arial", 16));

        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        Button yesButton = new Button("Yes, Logout");
        yesButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-padding: 8px 16px;");
        yesButton.setOnAction(e -> {
            confirmStage.close();
            performLogout();
        });

        Button noButton = new Button("Cancel");
        noButton.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-padding: 8px 16px;");
        noButton.setOnAction(e -> confirmStage.close());

        buttonBox.getChildren().addAll(yesButton, noButton);
        confirmLayout.getChildren().addAll(messageLabel, buttonBox);

        Scene confirmScene = new Scene(confirmLayout, 300, 150);
        confirmStage.setScene(confirmScene);

        // Center the confirmation dialog
        double centerX = primaryStage.getX() + (primaryStage.getWidth() - 300) / 2;
        double centerY = primaryStage.getY() + (primaryStage.getHeight() - 150) / 2;
        confirmStage.setX(centerX);
        confirmStage.setY(centerY);

        confirmStage.show();
    }

    private void performLogout() {
        try {
            primaryStage.close();

            Stage loginStage = new Stage();
            MenuManager menuManager = new MenuManager();
            menuManager.start(loginStage);

        } catch (Exception e) {
            e.printStackTrace();
            Platform.exit();
        }
    }

    private void exitGame() {
        Stage confirmStage = new Stage();
        confirmStage.initOwner(primaryStage); // Use initOwner here too
        confirmStage.setTitle("Exit Game");
        confirmStage.setFullScreen(false);
        confirmStage.setMaximized(false);
        confirmStage.setResizable(false);

        VBox confirmLayout = new VBox(20);
        confirmLayout.setPadding(new Insets(20));
        confirmLayout.setAlignment(Pos.CENTER);
        confirmLayout.setStyle("-fx-background-color: #ffe6e6; -fx-border-color: #ffcccc; -fx-border-width: 2px;");

        Label messageLabel = new Label("Are you sure you want to exit the game?");
        messageLabel.setFont(Font.font("Arial", 16));

        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        Button yesButton = new Button("Yes, Exit");
        yesButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-padding: 8px 16px;");
        yesButton.setOnAction(e -> {
            confirmStage.close();
            Platform.exit();
        });

        Button noButton = new Button("Cancel");
        noButton.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-padding: 8px 16px;");
        noButton.setOnAction(e -> confirmStage.close());

        buttonBox.getChildren().addAll(yesButton, noButton);
        confirmLayout.getChildren().addAll(messageLabel, buttonBox);

        Scene confirmScene = new Scene(confirmLayout, 300, 150);
        confirmStage.setScene(confirmScene);

        // Center the exit confirmation dialog
        double centerX = primaryStage.getX() + (primaryStage.getWidth() - 300) / 2;
        double centerY = primaryStage.getY() + (primaryStage.getHeight() - 150) / 2;
        confirmStage.setX(centerX);
        confirmStage.setY(centerY);

        confirmStage.show();
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        StackPane root = new StackPane();
        BorderPane mainContent = new BorderPane();

        // Set background image
        BackgroundImage bgImage = new BackgroundImage(
                new Image(getClass().getResource("/Homescreen.jpg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        mainContent.setBackground(new Background(bgImage));

        // Top bar with only settings button
        VBox topBar = new VBox(10);
        topBar.setPadding(new Insets(10));
        topBar.setAlignment(Pos.TOP_LEFT);

        // Only settings button remains
        Image settingsImage = new Image(getClass().getResource("/SETTINGS.png").toExternalForm());
        Button settingsButton = createRoundButton(settingsImage);

        settingsButton.setOnAction(e -> showSettingsPopup());

        topBar.getChildren().add(settingsButton);
        mainContent.setTop(topBar);

        // Bottom button bar
        HBox bottomBar = new HBox();
        bottomBar.setStyle("-fx-alignment: center; -fx-background-color: transparent;");
        bottomBar.setPadding(new Insets(0));

        String[] buttonLabels = {"Visit", "Play"};

        Region leftSpacer = new Region();
        HBox.setHgrow(leftSpacer, Priority.ALWAYS);
        bottomBar.getChildren().add(leftSpacer);

        for (int i = 0; i < buttonLabels.length; i++) {
            Button btn = createMainButton(buttonLabels[i]);

            if (buttonLabels[i].equals("Play")) {
                btn.setOnAction(e -> {
                    StorylineScene storylineOverlay = new StorylineScene(primaryStage);
                    storylineOverlay.setStyle("-fx-background-color: linear-gradient(to bottom, #ffb6c1, #ff69b4);");
                    StackPane storyline = (StackPane) primaryStage.getScene().getRoot();
                    storyline.getChildren().add(storylineOverlay);
                });
            } else if (buttonLabels[i].equals("Visit")) {
                btn.setOnAction(e -> {
                    CharacterSelectionScreen selectionScreen = new CharacterSelectionScreen(primaryStage);
                    selectionScreen.setStyle("-fx-background-color: linear-gradient(to bottom, #ffb6c1, #ff69b4);");
                    StackPane visit = (StackPane) primaryStage.getScene().getRoot();
                    visit.getChildren().add(selectionScreen);
                });
            }

            HBox.setHgrow(btn, Priority.ALWAYS);
            bottomBar.getChildren().add(btn);

            if (i < buttonLabels.length - 1) {
                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);
                bottomBar.getChildren().add(spacer);
            }
        }

        Region rightSpacer = new Region();
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);
        bottomBar.getChildren().add(rightSpacer);

        bottomBar.setTranslateY(-20);
        mainContent.setBottom(bottomBar);

        // Add main content to root
        root.getChildren().add(mainContent);

        // Scene and stage setup
        Scene scene = new Scene(root, 1024, 619);

        primaryStage.setFullScreenExitHint("");
        primaryStage.setResizable(true);
        primaryStage.setTitle("Otome Game - Home");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createRoundButton(Image image) {
        Button button = new Button();
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);
        button.setGraphic(imageView);
        button.setStyle("-fx-background-color: purple;"
                + "-fx-padding: 10;"
                + "-fx-background-radius: 50%;"
                + "-fx-pref-width: 40px;"
                + "-fx-pref-height: 40px;");
        return button;
    }

    private Button createMainButton(String text) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: purple;"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 24px;"
                + "-fx-font-weight: bold;"
                + "-fx-padding: 20 40;"
                + "-fx-background-radius: 15;"
                + "-fx-border-color: transparent;"
                + "-fx-border-width: 0px;"
                + "-fx-cursor: hand;");

        btn.setOnMouseEntered(event -> btn.setStyle(btn.getStyle()
                .replace("-fx-text-fill: white;", "-fx-text-fill: lightblue;")));

        btn.setOnMouseExited(event -> btn.setStyle(btn.getStyle()
                .replace("-fx-text-fill: lightblue;", "-fx-text-fill: white;")));

        return btn;
    }
}