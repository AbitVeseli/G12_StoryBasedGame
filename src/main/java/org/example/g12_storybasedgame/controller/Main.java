package org.example.g12_storybasedgame.controller;

import javafx.application.Application;
import org.example.g12_storybasedgame.view.homescreen.Homescreen;
import org.example.g12_storybasedgame.view.menu.MenuManager;

public class Main {
    public static void main(String[] args) {
        Application.launch(Homescreen.class, args); // Starta via MenuManager
    }
}