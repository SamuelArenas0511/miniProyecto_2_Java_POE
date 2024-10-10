package com.example.min_proyecto_2.controller;

import com.example.min_proyecto_2.model.MatrixCreator;
import com.example.min_proyecto_2.view.GameStage;
import com.example.min_proyecto_2.view.WelcomeStage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class GameController {

    @FXML
    private GridPane sudokuGridPane;

    @FXML
    private Button bStartSudoku;

    @FXML
    private Button bGoBack;

    @FXML
    private Label lbStatus;

    @FXML
    private Label lbStopWatch;
    private int seconds;

    private MatrixCreator matrixCreator;

    public void initialize() {

        matrixCreator = new MatrixCreator();
    }


    @FXML
    public void onHandleBStartSudoku(javafx.event.ActionEvent actionEvent) {
        seconds = 0;
        stopWatch();
        for (var node : sudokuGridPane.getChildren()) {
            if (node instanceof TextField textField)
                textField.setText("");
        }
        matrixCreator.resetMatrix();
        matrixCreator.fillSudokuMatrix();
        Random r = new Random();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                TextField textField = new TextField();
                textField.setStyle("-fx-background-color: TRANSPARENT; -fx-border-color: TRANSPARENT; -fx-text-fill: #C49C66; -fx-font-weight: bold; -fx-font-family: Tahoma; -fx-font-size: 30");
                textField.setAlignment(Pos.CENTER);
                if(r.nextInt(2) == 0){
                    textField.setText(matrixCreator.getMatrix()[i][j] + "");
                    textField.setEditable(false);
                }
                onHandleEntryTxt(textField,i,j);
                sudokuGridPane.add(textField, j, i);

            }
        }
    }

    private void onHandleEntryTxt(TextField textField, int i, int j) {
        textField.setOnKeyReleased(event -> {
            if(textField.getText().equals(matrixCreator.getMatrix()[i][j] + "") && !textField.getText().isEmpty()) {
                textField.setEditable(false);
                textField.setStyle("-fx-background-color: TRANSPARENT; -fx-border-color: TRANSPARENT; -fx-text-fill: #29507D; -fx-font-weight: bold; -fx-font-family: Tahoma; -fx-font-size: 30");
                lbStatus.setText("Correcto!");
            }else{
                textField.setStyle("-fx-background-color: TRANSPARENT; -fx-border-color: TRANSPARENT; -fx-text-fill: #7D3434; -fx-font-weight: bold; -fx-font-family: Tahoma; -fx-font-size: 30");
                lbStatus.setText("Incorrecto!");
            }
        });
    }

    private void stopWatch() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateStopWatch()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateStopWatch(){
        seconds++;
        int minutes = seconds / 60;
        int secondremainder = seconds % 60;
        lbStopWatch.setText(String.format("%02d:%02d", minutes, secondremainder));
    }


        public void OnHandleBGoBack(ActionEvent actionEvent) throws IOException {
        WelcomeStage.getInstance();
        GameStage.deleteInstance();
    }
}
