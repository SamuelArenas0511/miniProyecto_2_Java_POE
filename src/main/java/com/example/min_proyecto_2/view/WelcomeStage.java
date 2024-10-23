package com.example.min_proyecto_2.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The {@code WelcomeStage} class represents the initial welcome screen for the Sudoku game.
 * It loads the FXML layout, sets the stage properties such as the window icon and title, and shows the welcome screen.
 * This class implements a Singleton pattern to ensure only one instance of the welcome screen is created.
 *
 * @author Nicolas Cordoba
 * @author Samuel Arenas
 */
public class WelcomeStage extends Stage {

    /**
     * Constructor for {@code WelcomeStage}.
     * It loads the welcome screen layout from the FXML file and sets the scene,
     * window title, and icon.
     *
     * @throws IOException If the FXML file cannot be loaded.
     */
    public WelcomeStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/min_proyecto_2/welcome-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        this.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/min_proyecto_2/image/icon.png")));
        setResizable(false);
        setTitle("Sudoku");
        setScene(scene);
        show();
    }

    /**
     * Holder class to implement the Singleton pattern.
     * This inner static class holds the unique instance of {@code WelcomeStage}.
     */
    private static class WelcomeStageHolder {
        /**
         * The unique instance of {@code WelcomeStage}.
         */
        private static WelcomeStage INSTANCE;
    }

    /**
     * Returns the singleton instance of {@code WelcomeStage}.
     * If the instance does not exist, it is created.
     *
     * @return The singleton instance of {@code WelcomeStage}.
     * @throws IOException If an error occurs while creating the welcome stage.
     */
    public static WelcomeStage getInstance() throws IOException {
        WelcomeStage.WelcomeStageHolder.INSTANCE =
                WelcomeStage.WelcomeStageHolder.INSTANCE != null ?
                        WelcomeStage.WelcomeStageHolder.INSTANCE : new WelcomeStage();
        return WelcomeStage.WelcomeStageHolder.INSTANCE;
    }

    /**
     * Deletes the current instance of {@code WelcomeStage}.
     * This method closes the window and sets the instance to {@code null}, allowing a new one to be created later.
     */
    public static void deleteInstance() {
        WelcomeStage.WelcomeStageHolder.INSTANCE.close();
        WelcomeStage.WelcomeStageHolder.INSTANCE = null;
    }
}
