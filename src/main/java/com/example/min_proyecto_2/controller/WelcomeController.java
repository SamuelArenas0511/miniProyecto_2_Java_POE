package com.example.min_proyecto_2.controller;

import com.example.min_proyecto_2.model.font.Fonts;
import com.example.min_proyecto_2.view.GameStage;
import com.example.min_proyecto_2.view.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.w3c.dom.Text;
import org.w3c.dom.css.RGBColor;

import java.io.IOException;
import java.io.InputStream;

/**
 * Controller class for the welcome screen of the game.
 * Manages user interactions with the welcome stage,
 * including starting a new game and continuing an existing game.
 *
 * @author Samuel Arenas
 * @author Nicolas Cordoba
 */
public class WelcomeController {
    /** Button to continue a previous game. */
    @FXML
    private Button btnContinueGame;

    /** Button to start a new game. */
    @FXML
    private Button btnNewGame;

    /** Label to display the time of the current game. */
    @FXML
    private Label lbTime;

    /** Layout for the welcome screen. */
    @FXML
    private GridPane gridPaneWelcome;

    /** Reference to the game stage. */
    private GameStage gameStage;

    /** Counter to track the number of times the controller has been initialized. */
    private static int counterNumber = 0;

    /**
     * Initializes the controller after its root element has been processed.
     * Sets up the UI components and checks the status of the current game.
     *
     * @throws IOException If an I/O error occurs during initialization.
     */
    @FXML
    public void initialize() throws IOException {
        btnContinueGame.setDisable(true);
        btnContinueGame.setFont(new Fonts(25, "semibold").getFont());
        lbTime.setFont(new Fonts(10, "semibold").getFont());
        btnNewGame.setFont(new Fonts(25, "semibold").getFont());
        if(counterNumber > 0) {
            if(GameStage.getInstance().getGameController().getIsGameFinished()){
                btnContinueGame.setDisable(true);
                lbTime.setText("Time: 00:00");
            }else {
                btnContinueGame.setDisable(false);
                lbTime.setText("Time: " + GameStage.getInstance().getGameController().getGameTime());
            }
        }
        counterNumber++;
    }

    /**
     * Handles the action when the play button is clicked.
     * Starts a new game or initializes the game stage if it doesn't exist.
     *
     * @param event The action event triggered by clicking the button.
     * @throws IOException If an I/O error occurs while handling the event.
     */
    @FXML
    public void handlePlayButton(ActionEvent event) throws IOException {
        if(GameStage.getInstance() == null){
            GameStage.getInstance();
        }else{
            GameStage.deleteInstance();
            GameStage.getInstance();
        }
        WelcomeStage.deleteInstance();

    }

    /**
     * Handles the action when the continue button is clicked.
     * Resumes the previously saved game if it exists.
     *
     * @param event The action event triggered by clicking the button.
     * @throws IOException If an I/O error occurs while handling the event.
     */
    @FXML
    public void handleContinueButton(ActionEvent event) throws IOException {
        if(GameStage.getInstance() == null){
            GameStage.getInstance();
        }else{

            GameStage.getInstance().show();
            GameStage.getInstance().getGameController().continueGame();

        }
        WelcomeStage.deleteInstance();

    }

    /**
     * Handles mouse entered event on the continue button.
     * Changes the style of the continue button to indicate hover.
     *
     * @param event The mouse event triggered when the cursor enters the button.
     */
    @FXML
    void onHandleEnteredBtnContinue(MouseEvent event) {
        btnContinueGame.setStyle("-fx-background-color: #992937; -fx-background-radius: 100; -fx-border-radius: 100; -fx-border-width: 10; -fx-text-fill:  white");
        lbTime.setTextFill(Color.rgb(255,255,255,0.7));
    }

    /**
     * Handles mouse exited event on the continue button.
     * Resets the style of the continue button when the cursor exits.
     *
     * @param event The mouse event triggered when the cursor exits the button.
     */
    @FXML
    void onHandleExitedBtnContinue(MouseEvent event) {
        btnContinueGame.setStyle("-fx-background-color: #b62f35; -fx-background-radius: 100; -fx-border-radius: 100; -fx-border-width: 10; -fx-text-fill:  white");
        lbTime.setTextFill(Color.rgb(255,255,255,0.65));
    }

    /**
     * Handles mouse entered event on the new game button.
     * Changes the style of the new game button to indicate hover.
     *
     * @param event The mouse event triggered when the cursor enters the button.
     */
    @FXML
    void onHandleEnteredBtnNewGame(MouseEvent event) {
        btnNewGame.setStyle("-fx-background-color: #fafafa ; -fx-background-radius: 100; -fx-border-radius: 100; -fx-border-width: 10; -fx-text-fill:  #b62f35");
    }

    /**
     * Handles mouse exited event on the new game button.
     * Resets the style of the new game button when the cursor exits.
     *
     * @param event The mouse event triggered when the cursor exits the button.
     */
    @FXML
    void onHandleExitedBtnNewGame(MouseEvent event) {
        btnNewGame.setStyle("-fx-background-color: white; -fx-background-radius: 100; -fx-border-radius: 100; -fx-border-width: 10; -fx-text-fill:  #b62f35");
    }

}
