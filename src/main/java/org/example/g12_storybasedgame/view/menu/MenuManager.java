package org.example.g12_storybasedgame.view.menu;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.g12_storybasedgame.model.User;
import org.example.g12_storybasedgame.model.UserFileManager;
import org.example.g12_storybasedgame.view.homescreen.Homescreen;

import java.util.HashMap;

public class MenuManager extends Application {
    private Stage primaryStage;
    private StackPane rootContainer;
    private HashMap<String, User> users = UserFileManager.laddaAnvändare();
    private Scene mainScene;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.rootContainer = new StackPane();
        this.mainScene = new Scene(rootContainer);

        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Otome Adventure");
        primaryStage.setFullScreen(true); // false är endast för testning annars true
        primaryStage.setFullScreenExitHint("");

        showStartScreen();
        primaryStage.show();
    }

    // ===== STARTSkÄRM =====
    public void showStartScreen() {
        rootContainer.getChildren().clear();

        ImageView backgroundView = new ImageView(new Image(getClass().getResourceAsStream("/STORYBASED1.jpg")));
        backgroundView.setPreserveRatio(false);
        backgroundView.fitWidthProperty().bind(primaryStage.widthProperty());
        backgroundView.fitHeightProperty().bind(primaryStage.heightProperty());

        Label clickLabel = new Label("Tap Anywhere to start");
        clickLabel.setStyle(
                "-fx-font-family: 'Playfair Display', serif;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 2em;" +
                        "-fx-text-fill: #5e1519;"
        );

        Button startButton = new Button();
        startButton.setStyle("-fx-background-color: transparent; -fx-border-width: 0;");
        startButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        TranslateTransition hopAnimation = new TranslateTransition(Duration.seconds(1.4), clickLabel);
        hopAnimation.setFromY(9);
        hopAnimation.setToY(-2);
        hopAnimation.setAutoReverse(true);
        hopAnimation.setCycleCount(Animation.INDEFINITE);

        rootContainer.getChildren().addAll(backgroundView, clickLabel, startButton);
        StackPane.setAlignment(clickLabel, Pos.BOTTOM_CENTER);
        StackPane.setMargin(clickLabel, new Insets(0, 0, 50, 0));

        mainScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                primaryStage.setFullScreen(false);
            }
        });

        startButton.setOnAction(e -> {
            hopAnimation.stop();
            showLoginScreen();
        });

        startButton.setOnMouseEntered(e -> startButton.setCursor(Cursor.HAND));
        startButton.setOnMouseExited(e -> startButton.setCursor(Cursor.DEFAULT));

        hopAnimation.play();
    }

    // ===== INLOGGNING =====
    public void showLoginScreen() {
        // Rensa root containern
        rootContainer.getChildren().clear();

        // Skapa huvudcontainer med StackPane för lager
        StackPane mainContainer = new StackPane();
        mainContainer.setAlignment(Pos.CENTER_LEFT);

        // 1. Lägg till bakgrundsbild
        ImageView backgroundImage = new ImageView(new Image(getClass().getResourceAsStream("/ww.jpg")));
        backgroundImage.setFitWidth(primaryStage.getWidth());
        backgroundImage.setFitHeight(primaryStage.getHeight());
        backgroundImage.setPreserveRatio(false); // Fyll hela skärmen utan att behålla proportioner

        // 2. Lägg till mörk overlay för bättre kontrast
        Rectangle overlay = new Rectangle(primaryStage.getWidth(), primaryStage.getHeight());
        overlay.setFill(Color.rgb(0, 0, 0, 0.5)); // 50% genomskinlig svart

        // 3. Skapa innehållscontainer
        VBox loginForm = new VBox(20);
        loginForm.setAlignment(Pos.CENTER);
        loginForm.setMaxWidth(400); // Begränsa bredden på formuläret
        loginForm.setStyle("-fx-padding: 40; -fx-background-color: rgba(0, 0, 0, 0.5); -fx-background-radius: 0;");

        //FÖR SETTINGS
        VBox settingsForm = new VBox(20);
        settingsForm.setAlignment(Pos.TOP_LEFT);
        settingsForm.setMaxWidth(30); // Begränsa bredden på formuläret
        settingsForm.setStyle("-fx-padding: 40; -fx-background-color: transparent;");


        // Skapa och konfigurera formulärelement
        // Logo (mindre version av bakgrundsbilden eller separat logo)
        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/star.png"))); // Anpassa sökväg
        logo.setFitWidth(180);
        logo.setPreserveRatio(true);

        // Titel
        Label title = new Label("Welcome!");
        title.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-font-weight: bold;");

        // Användarnamn fält
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-font-size: 14px; -fx-min-width: 250px; -fx-padding: 8px; -fx-background-radius: 5;");

        // Lösenord fält
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-font-size: 14px; -fx-min-width: 250px; -fx-padding: 8px; -fx-background-radius: 5;");

        // Knappar
        Button loginButton = new Button("Log In");
        loginButton.setStyle("-fx-font-size: 14px; -fx-min-width: 120px; -fx-padding: 8px 16px; -fx-background-radius: 5;");

        Button registerButton = new Button("Create Account");
        registerButton.setStyle("-fx-font-size: 14px; -fx-min-width: 120px; -fx-padding: 8px 16px; -fx-background-radius: 5;");

        Button settingsButton = new Button("");
        settingsButton.setStyle("-fx-font-size: 14px; -fx-min-width: 120px; -fx-padding: 8px 16px; -fx-background-radius: 5;");

        //SETTINGS KNAPPEN
        ImageView settingsIcon = new ImageView(new Image(getClass().getResourceAsStream("/SETTINGS.png")));
        settingsIcon.setFitWidth(50);
        settingsIcon.setFitHeight(50);
        settingsIcon.setPreserveRatio(true);
        //settingsIcon.setStyle("-fx-background-radius: 20;");

//        Rectangle clip = new Rectangle(50,50);
//        clip.setArcWidth(30);
//        clip.setArcHeight(30);

        //settingsIcon.setClip(clip);

        settingsButton.setGraphic(settingsIcon);
        settingsButton.setStyle(" -fx-background-color: rgba(0, 0, 0, 0.01); -fx-background-radius: 50;");
        settingsButton.setCursor(Cursor.HAND);
        StackPane.setAlignment(settingsForm, Pos.TOP_LEFT);
        StackPane.setMargin(settingsForm, new Insets(-20, 0, 0, -35)); // top, right, bottom, left



        // Knappcontainer
        HBox buttonBox = new HBox(20, loginButton, registerButton);
        buttonBox.setAlignment(Pos.CENTER);

        HBox buttonBox2 = new HBox(20, settingsButton);
        buttonBox.setAlignment(Pos.TOP_LEFT);


        // Lägg till alla element i formuläret
        loginForm.getChildren().addAll(
                logo,
                title,
                usernameField,
                passwordField,
                buttonBox
        );
        settingsForm.getChildren().addAll(
                settingsButton,
                buttonBox2
        );


        // Lägg till alla lager i huvudcontainern
        mainContainer.getChildren().addAll(
                backgroundImage,
                overlay,
                loginForm,
                settingsForm
        );


        // Lägg till huvudcontainern i rootContainer
        rootContainer.getChildren().add(mainContainer);

        // Gör layouten responsiv
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            backgroundImage.setFitWidth(newVal.doubleValue());
            overlay.setWidth(newVal.doubleValue());
        });

        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            backgroundImage.setFitHeight(newVal.doubleValue());
            overlay.setHeight(newVal.doubleValue());
        });

        // Knapphanterare
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();


            if (loginUser(usernameField.getText(), passwordField.getText())) {
                showMainMenu();
            } else if (username.isEmpty() || password.isEmpty()) {
                showAlert("Error", "Please fill in all fields.");

        }else {
                showAlert("Error", "Wrong username or password, try again!");
            }
        });

        settingsButton.setOnAction(e -> {
            showAlert("Error", "This has not yet been implemented!");

        });

        registerButton.setOnAction(e -> showRegisterScreen());
    }

    // ===== SETTINGS =====
    public void showSettingsPopUp() {

    }


    // ===== REGISTRERING =====
    public void showRegisterScreen() {
        rootContainer.getChildren().clear();

        // Skapa huvudcontainer med StackPane för lager
        StackPane mainContainer = new StackPane();
        mainContainer.setAlignment(Pos.CENTER_LEFT);

        // 1. Lägg till bakgrundsbild (samma som vid inloggning)
        ImageView backgroundImage = new ImageView(new Image(getClass().getResourceAsStream("/STORYBASED.jpg")));
        backgroundImage.setFitWidth(primaryStage.getWidth());
        backgroundImage.setFitHeight(primaryStage.getHeight());
        backgroundImage.setPreserveRatio(false);

        // 2. Lägg till mörk overlay för bättre kontrast
        Rectangle overlay = new Rectangle(primaryStage.getWidth(), primaryStage.getHeight());
        overlay.setFill(Color.rgb(0, 0, 0, 0.5)); // 50% genomskinlig svart

        // 3. Skapa registreringsformulär
        VBox registerForm = new VBox(20);
        registerForm.setAlignment(Pos.CENTER);
        registerForm.setMaxWidth(400); // Samma bredd som inloggningsformuläret
        registerForm.setStyle("-fx-padding: 40; -fx-background-color: rgba(0, 0, 0, 0.5);");

        // Skapa och konfigurera formulärelement
        Label title = new Label("Create a new account");
        title.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-font-weight: bold;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Choose username");
        usernameField.setStyle("-fx-font-size: 14px; -fx-min-width: 250px; -fx-padding: 8px; -fx-background-radius: 5;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Choose password");
        passwordField.setStyle("-fx-font-size: 14px; -fx-min-width: 250px; -fx-padding: 8px; -fx-background-radius: 5;");

        // Lägg till bekräftelselösenordsfält
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm password");
        confirmPasswordField.setStyle("-fx-font-size: 14px; -fx-min-width: 250px; -fx-padding: 8px; -fx-background-radius: 5;");

        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-font-size: 14px; -fx-min-width: 120px; -fx-padding: 8px 16px; -fx-background-radius: 5;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 14px; -fx-min-width: 120px; -fx-padding: 8px 16px; -fx-background-radius: 5;");

        // Knappcontainer
        HBox buttonBox = new HBox(20, registerButton, backButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Lägg till alla element i formuläret
        registerForm.getChildren().addAll(
                title,
                usernameField,
                passwordField,
                confirmPasswordField,
                buttonBox
        );

        // Lägg till alla lager i huvudcontainern
        mainContainer.getChildren().addAll(
                backgroundImage,
                overlay,
                registerForm
        );

        // Lägg till huvudcontainern i rootContainer
        rootContainer.getChildren().add(mainContainer);

        // Gör layouten responsiv
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            backgroundImage.setFitWidth(newVal.doubleValue());
            overlay.setWidth(newVal.doubleValue());
        });

        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            backgroundImage.setFitHeight(newVal.doubleValue());
            overlay.setHeight(newVal.doubleValue());
        });

        // Knapphanterare
        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            // Validering
            if (username.isEmpty() || password.isEmpty()) {
                showAlert("Error", "Please fill in all fields.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                showAlert("Error", "Passwords do not match.");
                return;
            }

            if (registerUser(username, password)) {
                showAlert("Success", "Account created! Please log in.");
                showLoginScreen();
            } else {
                showAlert("Error", "Username already taken.");
            }
        });

        backButton.setOnAction(e -> showLoginScreen());
    }

    // ===== HUVUDMENY =====
    public void showMainMenu() {
        rootContainer.getChildren().clear();

        Label welcomeLabel = new Label("Welcome player!");
        welcomeLabel.setStyle("-fx-font-size: 2em; -fx-text-fill: white;");

        Button startGameButton = new Button("Start Game");
        startGameButton.setStyle("-fx-font-size: 1.5em;");
        startGameButton.setOnAction(e -> {
            Stage homescreenStage = new Stage();
            new Homescreen().start(homescreenStage); // Starta Homescreen i ett nytt fönster
            primaryStage.close(); // Stäng inloggningsfönstret
        });

        VBox layout = new VBox(30);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(welcomeLabel, startGameButton);
        layout.setStyle("-fx-background-color: purple");

        rootContainer.getChildren().add(layout);
    }

    // ===== HJÄLPMETODER =====
    private boolean loginUser(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }

    private boolean registerUser(String username, String password) {
        if (users.containsKey(username)) {
            return false;
        }

        users.put(username, new User(username, password));
        UserFileManager.sparaAnvändare(username, password);
        return true;
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

    }

//    public static void main(String[] args) {
//        launch(args);
//    }
}