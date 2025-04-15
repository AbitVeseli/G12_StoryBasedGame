module org.example.g12_storybasedgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    opens org.example.g12_storybasedgame to javafx.fxml;
    exports org.example.g12_storybasedgame;
}