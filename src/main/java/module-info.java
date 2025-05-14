module org.example.g12_storybasedgame {
    requires javafx.controls;
    requires javafx.fxml;

    // Exportera nödvändiga paket
    exports org.example.g12_storybasedgame.controller;
    exports org.example.g12_storybasedgame.view.menu;
    exports org.example.g12_storybasedgame.model;

    // Öppna paket för FXML (om du använder det)
    opens org.example.g12_storybasedgame.view.menu to javafx.fxml;
    exports org.example.g12_storybasedgame.view.components;
    exports org.example.g12_storybasedgame.view.homescreen;
    opens org.example.g12_storybasedgame.view.homescreen to javafx.fxml;
}