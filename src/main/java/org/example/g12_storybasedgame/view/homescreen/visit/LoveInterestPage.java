package org.example.g12_storybasedgame.view.homescreen.visit;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.InputStream;

public class LoveInterestPage extends BorderPane {
    private final String loveInterestName;
    private final Stage primaryStage;

    public LoveInterestPage(Stage primaryStage, String loveInterestName) {
        this.primaryStage = primaryStage;
        this.loveInterestName = loveInterestName;

        setupUI();
    }

    private void setupUI() {
        // Top bar with a back button
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(15));
        topBar.setAlignment(Pos.TOP_LEFT);
        topBar.setStyle("-fx-background-color: rgba(30, 30, 30, 0.8);");

        Button backButton = new Button("← Back");
        backButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 16px;");
        backButton.setOnAction(e -> returnToCharacterSelection());

        topBar.getChildren().add(backButton);
        this.setTop(topBar);

        // Center content
        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(40));

        // Load character image or fallback
        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(350);  // ⬅ Bildens maxbredd

        String imagePath = "/" + loveInterestName + "_main.png";
        InputStream imageStream = getClass().getResourceAsStream(imagePath);

        if (imageStream != null) {
            imageView.setImage(new Image(imageStream));
        } else {
            System.err.println("Bild saknas: " + imagePath);
            imageView.setImage(new Image(getClass().getResourceAsStream("/default_main.png"))); // Fallback-bild
        }

        // Visa namn i fetstil + beskrivning under (likt HTML <b> + <br>)
        TextFlow descriptionFlow = getDescriptionFlowFor(loveInterestName);
        descriptionFlow.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        descriptionFlow.setMaxWidth(400);

        centerBox.getChildren().addAll(imageView, descriptionFlow);
        this.setCenter(centerBox);

        // Bakgrund
        this.setStyle("-fx-background-color: linear-gradient(to bottom, #1e1e1e, #2e2e2e);");
    }

    /**
     * Returnerar en TextFlow med namnet i fetstil + beskrivning under.
     */
    private TextFlow getDescriptionFlowFor(String name) {
        Text nameText;
        Text descText;

        switch (name) {
            case "LI1" -> {
                nameText = new Text("Judas Blackthorne.\n");
                descText = new Text("A brooding, rebellious student rumored to be connected to dark magic. Distrustful but fiercely loyal if won over.");
            }
            case "LI2" -> {
                nameText = new Text("Dylan T. Ravenwood.\n");
                descText = new Text("Charismatic and from a wealthy lineage. He’s adored by many but hides a calculating nature.");
            }
            case "LI3" -> {
                nameText = new Text("Emilio Valmont.\n");
                descText = new Text("A cheerful, bookish wizard that looks intimidating. He appears to be normal and unbothered by everything around him.");
            }
            case "LI4" -> {
                nameText = new Text("Benny Blade Vi.\n");
                descText = new Text("Mysterious transfer student that appears right after the murder. Friendly but harbors a secret.");
            }
            case "LI5" -> {
                nameText = new Text("Sam.\n");
                descText = new Text("Quiet but deeply caring, always there when you need them.");
            }
            default -> {
                nameText = new Text("Unknown.\n");
                descText = new Text("An intriguing person awaits, but their story is not written yet.");
            }
        }

        // Stil för namn
        nameText.setFont(Font.font("System", FontWeight.BOLD, 20));
        nameText.setStyle("-fx-fill: white;");

        // Stil för beskrivning
        descText.setFont(Font.font(16));
        descText.setStyle("-fx-fill: white;");

        return new TextFlow(nameText, descText);
    }

    /**
     * Tar bort den här sidan från scenstacken (tillbaka till val).
     */
    private void returnToCharacterSelection() {
        StackPane root = (StackPane) this.getScene().getRoot();
        if (root.getChildren().size() > 1) {
            root.getChildren().remove(root.getChildren().size() - 1);
        }
    }
}
