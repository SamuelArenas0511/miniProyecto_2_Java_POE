package com.example.min_proyecto_2.controller;

import com.example.min_proyecto_2.view.GameStage;
import com.example.min_proyecto_2.view.WelcomeStage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class WelcomeController {

    public void handlePlayButton(ActionEvent event) throws IOException {
        GameStage.getInstance();
        WelcomeStage.deleteInstance();
    }
}
