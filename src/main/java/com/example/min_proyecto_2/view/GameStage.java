package com.example.min_proyecto_2.view;

import com.example.min_proyecto_2.controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * The {@code GameStage} class represents the game stage where the Sudoku puzzle is played.
 * It loads the game layout from the FXML file, initializes the {@link GameController}, and sets the scene properties.
 * This class implements a Singleton pattern to ensure only one instance of the game stage is created.
 *
 * @author Nicolas Cordoba
 * @author Samuel Arenas
 */
public class GameStage extends Stage {

    /**
     * The controller that manages the game logic and user interactions in the game stage.
     */
    private final GameController gameController;

    /**
     * Constructor for {@code GameStage}.
     * It loads the game screen layout from the FXML file and applies the game stylesheet.
     * The window icon and title are also set.
     *
     * @throws IOException If the FXML file cannot be loaded.
     */
    public GameStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/min_proyecto_2/game-view.fxml"));
        Parent root = loader.load();
        gameController = loader.getController();
        Scene scene = new Scene(root);
        this.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/min_proyecto_2/image/icon.png")));
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/min_proyecto_2/Style/style.css")).toExternalForm());
        setTitle("Sudoku");
        setResizable(false);
        setScene(scene);
        show();
    }

    /**
     * Returns the game controller associated with this stage.
     *
     * @return The {@link GameController} that manages the game logic.
     */
    public GameController getGameController() {
        return gameController;
    }

    /**
     * Holder class to implement the Singleton pattern.
     * This inner static class holds the unique instance of {@code GameStage}.
     */
    private static class GameStageHolder {
        /**
         * The unique instance of {@code GameStage}.
         */
        private static GameStage INSTANCE;
    }

    /**
     * Returns the singleton instance of {@code GameStage}.
     * If the instance does not exist, it is created.
     *
     * @return The singleton instance of {@code GameStage}.
     * @throws IOException If an error occurs while creating the game stage.
     */
    public static GameStage getInstance() throws IOException {
        GameStageHolder.INSTANCE =
                GameStageHolder.INSTANCE != null ?
                        GameStageHolder.INSTANCE : new GameStage();
        return GameStageHolder.INSTANCE;
    }

    /**
     * Deletes the current instance of {@code GameStage}.
     * This method closes the window and sets the instance to {@code null}, allowing a new one to be created later.
     */
    public static void deleteInstance() {
        GameStageHolder.INSTANCE.close();
        GameStageHolder.INSTANCE = null;
    }

    /**
     * Hides the current instance of {@code GameStage} without deleting it.
     * The instance can be shown again without recreating it.
     */
    public void closeInstance() {
        hide();
    }
}
