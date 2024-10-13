package com.example.min_proyecto_2.controller;

import com.example.min_proyecto_2.model.font.Fonts;
import com.example.min_proyecto_2.view.GameStage;
import com.example.min_proyecto_2.view.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import org.w3c.dom.Text;

import java.io.IOException;

public class WelcomeController {
    @FXML
    private Button btnContinueGame;

    @FXML
    private Button btnNewGame;

    @FXML
    private Label lbTime;

    @FXML
    private GridPane gridPaneWelcome;

    @FXML
    public void initialize() {
        gridPaneWelcome.setStyle("-fx-background-image: url('file:/C:/Users/Windows%2010%20Pro/IdeaProjects/min_Proyecto_2/src/main/resources/com/example/min_proyecto_2/image/background_welcome.png'); " +
                "-fx-background-repeat: no-repeat; " +
                "-fx-background-size: cover;");
        btnContinueGame.setFont(new Fonts(25, "semibold").getFont());
        lbTime.setFont(new Fonts(10, "semibold").getFont());
        btnNewGame.setFont(new Fonts(25, "semibold").getFont());
    }

    @FXML
    public void handlePlayButton(ActionEvent event) throws IOException {
        GameStage.getInstance();
        WelcomeStage.deleteInstance();
    }

}
