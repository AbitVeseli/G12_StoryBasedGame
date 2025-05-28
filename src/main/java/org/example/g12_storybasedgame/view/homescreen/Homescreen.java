package org.example.g12_storybasedgame.view.homescreen; //detta kan behövas ändra senare
                                        // om vi ska skapa paket för varje sak
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import org.example.g12_storybasedgame.view.homescreen.storyline.StorylineScene;
import org.example.g12_storybasedgame.view.homescreen.visit.LoveInterestPage;

import java.io.InputStream;

public class Homescreen extends Application {

    private Stage primaryStage;

    private void showPopup(String title, String[] contentItems) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(title);

        VBox popupLayout = new VBox(10);
        popupLayout.setPadding(new Insets(20));
        popupLayout.setStyle("-fx-background-color: #f0f8ff; -fx-border-color: black; -fx-border-width: 2px;");

        HBox topBar = new HBox();
        topBar.setAlignment(Pos.TOP_RIGHT);


        popupLayout.getChildren().add(topBar);

        for (String item : contentItems) {
            Label label = new Label(item);
            label.setFont(Font.font("Arial", 16));
            popupLayout.getChildren().add(label);
        }

        Scene popupScene = new Scene(popupLayout, 300, 250);
        popupStage.setScene(popupScene);
        popupStage.show();
    }

//    public static void main(String[] args) {
//        launch(args);
//    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage; // Spara primaryStage för senare användning
        //primaryStage.setFullScreen(true);

        // Root layout
        BorderPane root = new BorderPane();


        // Set background image (denna kod är inte förändrad)
        BackgroundImage bgImage = new BackgroundImage(
                new Image(getClass().getResource("/ww.jpg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        root.setBackground(new Background(bgImage));

        // Lägg till knappar direkt i toppen (vänstra topphörnet)
        VBox topBar = new VBox(10);  // Skapa en VBox direkt för att arrangera knappar vertikalt
        topBar.setPadding(new Insets(10));
        topBar.setAlignment(Pos.TOP_LEFT);  // Justera för vänsterjustering i toppvänstra hörnet

        // Ladda bilder för knapparna från resurserna
        Image settingsImage = new Image(getClass().getResource("/SETTINGS.png").toExternalForm());
        Image menuImage = new Image(getClass().getResource("/star.png").toExternalForm());

        // Skapa inställningsknappen med en bild
        Button settingsButton = new Button();
        ImageView settingsImageView = new ImageView(settingsImage);
        settingsImageView.setFitWidth(40);  // Justera storlek på bilden
        settingsImageView.setFitHeight(40);  // Justera storlek på bilden
        settingsButton.setGraphic(settingsImageView);  // Sätt bild som ikon
        settingsButton.setStyle("-fx-background-color: #ADD8E6;"  // Sätt bakgrunden till en ljusblå färg
                + "-fx-padding: 10;"  // Justera padding
                + "-fx-background-radius: 50%;"  // Gör knappen rund
                + "-fx-pref-width: 40px;"  // Sätt en fast bredd för att göra knapparna lika stora
                + "-fx-pref-height: 40px;");  // Sätt en fast höjd för att göra knapparna lika stora

        // Skapa menuknappen med en bild
        Button menuButton = new Button();
        ImageView menuImageView = new ImageView(menuImage);
        menuImageView.setFitWidth(40);  // Justera storlek på bilden
        menuImageView.setFitHeight(40);  // Justera storlek på bilden
        menuButton.setGraphic(menuImageView);  // Sätt bild som ikon
        menuButton.setStyle("-fx-background-color: #ADD8E6;"  // Sätt bakgrunden till en ljusblå färg
                + "-fx-padding: 10;"  // Justera padding
                + "-fx-background-radius: 50%;"  // Gör knappen rund
                + "-fx-pref-width: 40px;"  // Sätt en fast bredd för att göra knapparna lika stora
                + "-fx-pref-height: 40px;");  // Sätt en fast höjd för att göra knapparna lika stora

        //  Popup-innehåll och onAction för knappar
        String[] settingsContent = {
                "🎵 Music: ON",
                "🔊 Sound Effects: OFF",
                "🌙 Theme: Dark Mode",
                "🗣️ Language: English"
        };

        String[] menuContent = {
                "📖 Load Game",
                "💾 Save Game",
                "📂 Extras",
                "🏠 Return to Title"
        };

        settingsButton.setOnAction(e -> showPopup("Settings", settingsContent));
        menuButton.setOnAction(e -> showPopup("Menu", menuContent));

        // Lägg till knappar till topBar
        topBar.getChildren().addAll(settingsButton, menuButton);

        // Sätt topBar som rootens toppkomponent
        root.setTop(topBar);

// Bottom button bar
        HBox bottomBar = new HBox();  // Skapar en HBox för att hålla knapparna
        bottomBar.setStyle("-fx-alignment: center; -fx-background-color: transparent;");  // Ingen bakgrundsfärg

// Justera padding så att det inte finns några fasta avstånd
        bottomBar.setPadding(new Insets(0)); // Ingen padding här för att använda flexibel mellanrum

// Skapa en array med knappetiketter
        String[] buttonLabels = {"Storyline", "Visit", "Clue", "???", "Play"};

// Lägg till en Region i början för att skapa mellanrum från vänster kant
        Region leftSpacer = new Region();
        HBox.setHgrow(leftSpacer, Priority.ALWAYS);  // Den här Region kommer växa för att skapa utrymme från vänster kant
        bottomBar.getChildren().add(leftSpacer);

        for (int i = 0; i < buttonLabels.length; i++) {
            Button btn = new Button(buttonLabels[i]);

            // Apply style to button
            btn.setStyle("-fx-background-color: magenta;"              // Magenta bakgrund
                    + "-fx-text-fill: white;"                        // Vit text
                    + "-fx-font-size: 24px;"                          // Textstorlek
                    + "-fx-font-weight: bold;"                         // Fetstil på texten
                    + "-fx-padding: 20 40;"                           // Ökad padding för större knappar,
                    + "-fx-background-radius: 15;"                    // Rundade hörn
                    + "-fx-border-color: transparent;"                // Ingen kantlinje
                    + "-fx-border-width: 0px;"                        // Ingen kantlinje
                    + "-fx-cursor: hand;");                           // Hand-cursor när man hovrar över knappen

            // Hover-effekt (ändra textfärg till ljusblå vid hover)
            btn.setOnMouseEntered(event -> btn.setStyle("-fx-background-color: magenta;"  //Samma bakgrund färg
                    + "-fx-text-fill: lightblue;"       // Ljusblå text vid hover
                    + "-fx-font-size: 24px;"            // Textstorlek
                    + "-fx-font-weight: bold;"          // Fetstil på texten
                    + "-fx-padding: 20 40;"             // Padding
                    + "-fx-background-radius: 15;"      // Rundade hörn
                    + "-fx-border-color: transparent;"  // Ingen border
                    + "-fx-border-width: 0px;"          // Ingen kantlinje
                    + "-fx-cursor: hand;"));

            btn.setOnMouseExited(event -> btn.setStyle("-fx-background-color: magenta;"    // Samma bakgrund färg
                    + "-fx-text-fill: white;"           // Vit text när musen är borta
                    + "-fx-font-size: 24px;"            // Textstorlek
                    + "-fx-font-weight: bold;"          // Fetstil på texten
                    + "-fx-padding: 20 40;"             // Padding
                    + "-fx-background-radius: 15;"      // Rundade hörn
                    + "-fx-border-color: transparent;"  // Ingen border
                    + "-fx-border-width: 0px;"          // Ingen kantlinje
                    + "-fx-cursor: hand;"));            // Hand-cursor

            //  Koppling till varje knapp senare kan skrivas in här
            //Storyline
            if (buttonLabels[i].equals("Storyline")) {
                btn.setOnAction(e -> {
                    StorylineScene storylineScene = new StorylineScene(primaryStage);
                    primaryStage.setScene(storylineScene.getScene());
                });
            }   else if (buttonLabels[i].equals("Visit")) {
            btn.setOnAction(e -> showLoveInterestsPopup());

        }

            // Gör så att varje knapp växer och delar utrymmet jämt
            HBox.setHgrow(btn, Priority.ALWAYS);  // Gör att varje knapp växer jämt och fyller bredden

            bottomBar.getChildren().add(btn);

            // Lägg till en Region (en tom plats) mellan knappar för att skapa jämnt mellanrum
            if (i < buttonLabels.length - 1) {
                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);  // Gör att denna region växer för att skapa mellanrum
                bottomBar.getChildren().add(spacer);
            }
        }

// Lägg till en Region i slutet för att skapa mellanrum från höger kant
        Region rightSpacer = new Region();
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);  // Den här Region kommer växa för att skapa utrymme från höger kant
        bottomBar.getChildren().add(rightSpacer);

// Justera för att få bättre avstånd från botten
        bottomBar.setTranslateY(-20);  // Justera för att få bättre avstånd från botten

        root.setBottom(bottomBar);


        // Scene and stage
        Scene scene = new Scene(root, 1024, 619);
        primaryStage.setResizable(true);          // låt användaren ändra storlek

        primaryStage.setTitle("Otome Game - Home");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showLoveInterestsPopup() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Love Interests");

        // Create the popup layout with semi-transparent background
        BorderPane popupLayout = new BorderPane();
        popupLayout.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");

        // Main content container
        VBox popupContent = new VBox(20);
        popupContent.setPadding(new Insets(30));
        popupContent.setAlignment(Pos.CENTER);
        popupContent.setStyle("-fx-background-color: linear-gradient(to bottom, #ffb6c1, #ff69b4);" +
                "-fx-background-radius: 15;" +
                "-fx-border-color: white;" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 15;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");

        // Title
        Label title = new Label("Choose Your Love Interest");
        title.setStyle("-fx-font-size: 28px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: white; " +
                "-fx-effect: dropshadow(one-pass-box, rgba(0,0,0,0.5), 2, 0.0, 1, 1);");

        // Love interest buttons container
        HBox buttonsContainer = new HBox(30);
        buttonsContainer.setAlignment(Pos.CENTER);
        buttonsContainer.setPadding(new Insets(20, 0, 30, 0));

        String[] loveInterests = {"LI1", "LI2", "LI3", "LI4"};

        for (String li : loveInterests) {
            VBox liContainer = new VBox(10);
            liContainer.setAlignment(Pos.CENTER);

            // Create button with circular image
            Button liButton = new Button();
            liButton.setStyle("-fx-background-radius: 50%; " +
                    "-fx-border-color: white; " +
                    "-fx-border-width: 2; " +
                    "-fx-border-radius: 50%; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 5, 0.0, 0, 1); " +
                    "-fx-background-color: transparent; " +  // Prevents darkening
                    "-fx-padding: 0;");  // Removes default button padding

            try {
                InputStream imageStream = getClass().getResourceAsStream("/" + li + ".png");
                if (imageStream != null) {
                    Image image = new Image(imageStream);
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(120);
                    imageView.setFitHeight(120);
                    imageView.setClip(new Circle(60, 60, 60)); // Makes image circular
                    imageView.setPickOnBounds(true); // Allows clicks directly on the image
                    liButton.setGraphic(imageView);
                } else {
                    liButton.setText(li); // Fallback to text if image missing
                    liButton.setStyle(liButton.getStyle() + "-fx-font-size: 16px; -fx-text-fill: white;");
                }
            } catch (Exception e) {
                liButton.setText(li); // Fallback to text if error
                liButton.setStyle(liButton.getStyle() + "-fx-font-size: 16px; -fx-text-fill: white;");
            }

            // Character name label
            Label liName = new Label(li);
            liName.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");

            liContainer.getChildren().addAll(liButton, liName);

            // Button action - shows profile when clicked
            liButton.setOnAction(e -> {
                popupStage.close();
                showLoveInterestProfile(li); // Make sure this method exists in your class
            });

            // Hover effects
            liButton.setOnMouseEntered(event -> {
                liButton.setScaleX(1.1);
                liButton.setScaleY(1.1);
                liButton.setCursor(Cursor.HAND);
            });

            liButton.setOnMouseExited(event -> {
                liButton.setScaleX(1.0);
                liButton.setScaleY(1.0);
            });

            buttonsContainer.getChildren().add(liContainer);
        }

        // Close button
        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-background-color: white; " +
                "-fx-text-fill: #ff69b4; " +
                "-fx-font-size: 16px; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 8 25; " +
                "-fx-background-radius: 15;");

        closeButton.setOnAction(e -> popupStage.close());

        // Close button hover effects
        closeButton.setOnMouseEntered(event -> {
            closeButton.setStyle(closeButton.getStyle() +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 5, 0.0, 0, 1);");
            closeButton.setCursor(Cursor.HAND);
        });

        closeButton.setOnMouseExited(event -> {
            closeButton.setStyle(closeButton.getStyle()
                    .replace("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 5, 0.0, 0, 1);", ""));
        });

        // Add all components to the popup
        popupContent.getChildren().addAll(title, buttonsContainer, closeButton);
        popupLayout.setCenter(popupContent);

        // Create and show the scene
        Scene popupScene = new Scene(popupLayout, 600, 400);
        popupScene.setFill(Color.TRANSPARENT);
        popupStage.setScene(popupScene);

        // Make the popup draggable
        final Delta dragDelta = new Delta();
        popupContent.setOnMousePressed(mouseEvent -> {
            dragDelta.x = popupStage.getX() - mouseEvent.getScreenX();
            dragDelta.y = popupStage.getY() - mouseEvent.getScreenY();
        });

        popupContent.setOnMouseDragged(mouseEvent -> {
            popupStage.setX(mouseEvent.getScreenX() + dragDelta.x);
            popupStage.setY(mouseEvent.getScreenY() + dragDelta.y);
        });

        popupStage.showAndWait();
    }

    // Delta class for draggable functionality (add this as an inner class)
    class Delta {
        double x, y;
    }

    private void showLoveInterestProfile(String loveInterest) {
        Stage profileStage = new Stage();
        profileStage.initModality(Modality.APPLICATION_MODAL);
        profileStage.setTitle(loveInterest + "'s Profile");

        BorderPane profileLayout = new BorderPane();
        profileLayout.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");

        VBox profileContent = new VBox(20);
        profileContent.setPadding(new Insets(30));
        profileContent.setAlignment(Pos.CENTER);
        profileContent.setStyle("-fx-background-color: linear-gradient(to bottom, #ffb6c1, #ff69b4);" +
                "-fx-background-radius: 15;" +
                "-fx-border-color: white;" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 15;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");

        // Main image
        ImageView mainImageView = new ImageView();
        try {
            Image image = new Image(getClass().getResourceAsStream("/" + loveInterest + "_main.png"));
            mainImageView.setImage(image);
            mainImageView.setFitWidth(300);
            mainImageView.setPreserveRatio(true);
        } catch (Exception e) {
            mainImageView.setImage(null);
            Label errorLabel = new Label("Image not available");
            errorLabel.setStyle("-fx-text-fill: white;");
            profileContent.getChildren().add(errorLabel);
        }

        // Action buttons (Gallery, Info, etc.)
        HBox actionButtons = new HBox(20);
        actionButtons.setAlignment(Pos.CENTER);

        Button galleryButton = createActionButton("Gallery", loveInterest);
        Button infoButton = createActionButton("Info", loveInterest);
        Button backButton = createActionButton("Back", loveInterest);

        actionButtons.getChildren().addAll(galleryButton, infoButton, backButton);

        // Set button actions
        galleryButton.setOnAction(e -> showGallery(loveInterest));
        infoButton.setOnAction(e -> showCharacterInfo(loveInterest));
        backButton.setOnAction(e -> profileStage.close());

        // Add all components
        profileContent.getChildren().addAll(mainImageView, new Label(loveInterest), actionButtons);
        profileLayout.setCenter(profileContent);

        Scene profileScene = new Scene(profileLayout, 400, 500);
        profileScene.setFill(Color.TRANSPARENT);
        profileStage.setScene(profileScene);
        makeDraggable(profileStage, profileContent);
        profileStage.showAndWait();
    }

    private Button createActionButton(String text, String loveInterest) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: white; " +
                "-fx-text-fill: #ff69b4; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 8 15; " +
                "-fx-background-radius: 10;");

        // Hover effects
        button.setOnMouseEntered(e -> button.setStyle(button.getStyle() +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 5, 0.0, 0, 1);"));
        button.setOnMouseExited(e -> button.setStyle(button.getStyle()
                .replace("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 5, 0.0, 0, 1);", "")));

        return button;
    }

    private void showGallery(String loveInterest) {
        // Implement gallery functionality
        System.out.println("Showing gallery for " + loveInterest);
        // You can create a new popup or scene showing the character's gallery
    }

    private void showCharacterInfo(String loveInterest) {
        // Implement info functionality
        System.out.println("Showing info for " + loveInterest);
        // You can reuse your existing popup mechanism to show character info
    }

    // Helper method for hover effects
    private void setupButtonHoverEffects(Button button) {
        String originalStyle = button.getStyle();

        button.setOnMouseEntered(event -> {
            button.setStyle(originalStyle +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 5, 0.0, 0, 1);" +
                    "-fx-cursor: hand;");
        });

        button.setOnMouseExited(event -> {
            button.setStyle(originalStyle);
        });
    }

    // Helper method to make windows draggable
    private void makeDraggable(Stage stage, Node node) {
        final Delta dragDelta = new Delta();

        node.setOnMousePressed(mouseEvent -> {
            dragDelta.x = stage.getX() - mouseEvent.getScreenX();
            dragDelta.y = stage.getY() - mouseEvent.getScreenY();
        });

        node.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() + dragDelta.x);
            stage.setY(mouseEvent.getScreenY() + dragDelta.y);
        });
    }


}
