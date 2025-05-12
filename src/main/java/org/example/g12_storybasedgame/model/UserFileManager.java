package org.example.g12_storybasedgame.model;

import org.example.g12_storybasedgame.model.User;

import java.io.*;
import java.util.HashMap;

public class UserFileManager {
    private static final String FILNAMN = "users.txt";

    public static HashMap<String, User> laddaAnvändare() {
        HashMap<String, User> users = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILNAMN))) {
            String rad;
            while ((rad = br.readLine()) != null) {
                String[] delar = rad.split(",");
                if (delar.length == 2) {
                    users.put(delar[0], new User(delar[0], delar[1]));
                }
            }
        } catch (IOException e) {
            System.out.println("Användarfilen kunde inte läsas: " + e.getMessage());
        }

        return users;
    }

    public static void sparaAnvändare(String username, String password) {
        try (FileWriter fw = new FileWriter(FILNAMN, true)) {
            fw.write(username + "," + password + "\n");
        } catch (IOException e) {
            System.out.println("Kunde inte spara användaren: " + e.getMessage());
        }
    }
}