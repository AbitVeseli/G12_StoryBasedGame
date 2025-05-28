//package org.example.g12_storybasedgame.view.homescreen.visit;
//
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.scene.layout.Priority;
//import javafx.scene.layout.Region;
//import javafx.geometry.Pos;
//import javafx.geometry.Insets;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//import java.io.InputStream;
//
//public class VisitButton extends VBox {
//    private Button mainButton;
//    private HBox loveInterestButtons;
//    private Stage primaryStage;
//
//    public VisitButton(Stage primaryStage) {
//        this.primaryStage = primaryStage;
//        setupMainButton();
//        setupLoveInterestButtons();
//
//        this.setAlignment(Pos.CENTER);
//        this.setSpacing(10);
//
//        // Initially hide the love interest buttons
//        loveInterestButtons.setVisible(false);
//
//        // Add hover effects
//        setupHoverEffects();
//
//        setStyle("-fx-background-color: #FF00FF;"); // Bright pink background
//        setPrefSize(800, 600); // Force large size
//
//        // Add unmistakable test content
//        Label testLabel = new Label("VISIT BUTTON CONTENT IS VISIBLE!");
//        testLabel.setStyle("-fx-font-size: 40px; -fx-text-fill: white;");
//        getChildren().add(testLabel);
//
//        System.out.println("DEBUG: Guaranteed-visible VisitButton created");
//
//        this.getChildren().addAll(mainButton, loveInterestButtons);
//    }
//
//
//    private void setupMainButton() {
//        mainButton = new Button("Visit");
//        mainButton.setStyle("-fx-background-color: magenta;" +
//                "-fx-text-fill: white;" +
//                "-fx-font-size: 24px;" +
//                "-fx-font-weight: bold;" +
//                "-fx-padding: 20 40;" +
//                "-fx-background-radius: 15;");
//    }
//
//    private void setupLoveInterestButtons() {
//        loveInterestButtons = new HBox(10);
//        loveInterestButtons.setAlignment(Pos.CENTER);
//        loveInterestButtons.setPadding(new Insets(10));
//        loveInterestButtons.setStyle("-fx-background-color: rgba(173, 216, 230, 0.7);" +
//                "-fx-background-radius: 15;");
//
//        // Create buttons for each love interest
//        String[] loveInterests = {"LI1", "LI2", "LI3", "LI4"};
//
//        for (String li : loveInterests) {
//            Button liButton = new Button();
//
//            try {
//                InputStream imageStream = getClass().getResourceAsStream("/" + li + ".png");
//                if (imageStream != null) {
//                    Image image = new Image(imageStream);
//                    ImageView imageView = new ImageView(image);
//                    imageView.setFitWidth(80);
//                    imageView.setFitHeight(80);
//                    liButton.setGraphic(imageView);
//                } else {
//                    System.err.println("Image not found: /" + li + ".png");
//                    liButton.setText(li); // Fallback to text if image not found
//                }
//            } catch (Exception e) {
//                System.err.println("Error loading image for " + li + ": " + e.getMessage());
//                liButton.setText(li); // Fallback to text if error
//            }
//
//            liButton.setStyle("-fx-background-color: transparent;" +
//                    "-fx-background-radius: 50%;");
//
//            liButton.setOnAction(e -> {
//                LoveInterestPage page = new LoveInterestPage(primaryStage, li);
//                primaryStage.setScene(new Scene(page));
//            });
//
//            loveInterestButtons.getChildren().add(liButton);
//        }
//    }
//
//    private void setupHoverEffects() {
//        mainButton.setOnMouseEntered(e -> {
//            loveInterestButtons.setVisible(true);
//        });
//
//        this.setOnMouseExited(e -> {
//            if (!loveInterestButtons.isHover() && !mainButton.isHover()) {
//                loveInterestButtons.setVisible(false);
//            }
//        });
//
//        loveInterestButtons.setOnMouseExited(e -> {
//            if (!this.isHover()) {
//                loveInterestButtons.setVisible(false);
//            }
//        });
//    }
//}