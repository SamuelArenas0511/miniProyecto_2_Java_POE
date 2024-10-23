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

public class WelcomeController {
    @FXML
    private Button btnContinueGame;

    @FXML
    private Button btnNewGame;

    @FXML
    private Label lbTime;

    @FXML
    private GridPane gridPaneWelcome;
    private GameStage gameStage;

    private static int counterNumber = 0;

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

    @FXML
    void onHandleEnteredBtnContinue(MouseEvent event) {
        btnContinueGame.setStyle("-fx-background-color: #992937; -fx-background-radius: 100; -fx-border-radius: 100; -fx-border-width: 10; -fx-text-fill:  white");
        lbTime.setTextFill(Color.rgb(255,255,255,0.7));
    }

    @FXML
    void onHandleExitedBtnContinue(MouseEvent event) {
        btnContinueGame.setStyle("-fx-background-color: #b62f35; -fx-background-radius: 100; -fx-border-radius: 100; -fx-border-width: 10; -fx-text-fill:  white");
        lbTime.setTextFill(Color.rgb(255,255,255,0.65));
    }

    @FXML
    void onHandleEnteredBtnNewGame(MouseEvent event) {
        btnNewGame.setStyle("-fx-background-color: #fafafa ; -fx-background-radius: 100; -fx-border-radius: 100; -fx-border-width: 10; -fx-text-fill:  #b62f35");
    }

    @FXML
    void onHandleExitedBtnNewGame(MouseEvent event) {
        btnNewGame.setStyle("-fx-background-color: white; -fx-background-radius: 100; -fx-border-radius: 100; -fx-border-width: 10; -fx-text-fill:  #b62f35");
    }


}
