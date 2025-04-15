module org.example.g12_storybasedgame {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.g12_storybasedgame to javafx.fxml;
    exports org.example.g12_storybasedgame;
}