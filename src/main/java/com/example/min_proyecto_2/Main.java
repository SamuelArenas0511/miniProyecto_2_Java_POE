package com.example.min_proyecto_2;

import com.example.min_proyecto_2.view.GameStage;
import com.example.min_proyecto_2.view.WelcomeStage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class that launches the Sudoku game in JavaFX.
 * This class extends {@link javafx.application.Application} and serves as the entry point of the Sudoku game.
 * It initializes the welcome screen where the user can start the game.
 *
 * @author Nicolas Cordoba
 * @author Samuel Arenas
 */
public class Main extends Application {

    /**
     * Main method that launches the JavaFX Sudoku application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the primary stage of the application.
     * This initializes the welcome screen, from which the user can proceed to the game.
     *
     * @param primaryStage The primary stage for this application.
     * @throws IOException If an error occurs while loading the welcome screen.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        WelcomeStage.getInstance();
    }
}