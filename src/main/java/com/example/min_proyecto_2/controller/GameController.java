package com.example.min_proyecto_2.controller;

import com.example.min_proyecto_2.model.MatrixCreator;
import com.example.min_proyecto_2.view.GameStage;
import com.example.min_proyecto_2.view.WelcomeStage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.IOException;
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
    private Timeline timeline;

    private MatrixCreator matrixCreator;

    public void initialize() {

        matrixCreator = new MatrixCreator();
    }


    @FXML
    public void onHandleBStartSudoku(ActionEvent actionEvent) {
        seconds = 0;
        stopWatch();
        for (var node : sudokuGridPane.getChildren()) {
            if (node instanceof TextField textField)
                textField.setText("");
        }
        matrixCreator.resetMatrix();
        matrixCreator.fillSudokuMatrix();
        matrixCreator.randomStartingNumbers();
        Random r = new Random();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                TextField textField = new TextField();
                textField.setStyle("-fx-background-color: TRANSPARENT; -fx-border-color: TRANSPARENT; -fx-text-fill: #C49C66; -fx-font-weight: bold; -fx-font-family: Tahoma; -fx-font-size: 30");
                textField.setAlignment(Pos.CENTER);
                if(matrixCreator.getStartingNumbers()[i][j] == 1){
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
            if(textField.getText().equals(matrixCreator.getMatrix()[i][j] + "") && !textField.getText().isEmpty() && textField.isEditable()) {
                textField.setEditable(false);
                textField.setStyle("-fx-background-color: TRANSPARENT; -fx-border-color: TRANSPARENT; -fx-text-fill: #29507D; -fx-font-weight: bold; -fx-font-family: Tahoma; -fx-font-size: 30");
                lbStatus.setText("Correcto!");
                if (verificationWinner()){
                    timeline.stop();
                    System.out.println("GANASTE");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("¡Ganaste!");
                    alert.setHeaderText(null);
                    alert.setContentText("¡Felicidades, has ganado! en un tiempo de: " + lbStopWatch.getText() );
                    alert.showAndWait();

                };
            }else{
                textField.setStyle("-fx-background-color: TRANSPARENT; -fx-border-color: TRANSPARENT; -fx-text-fill: #7D3434; -fx-font-weight: bold; -fx-font-family: Tahoma; -fx-font-size: 30");
                lbStatus.setText("Incorrecto!");
            }
        });
    }

    private void stopWatch() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateStopWatch()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    private void updateStopWatch(){
        seconds++;
        int minutes = seconds / 60;
        int secondremainder = seconds % 60;
        lbStopWatch.setText(String.format("%02d:%02d", minutes, secondremainder));
    }

    private boolean verificationWinner(){
        for (var node : sudokuGridPane.getChildren()) {
            if (node instanceof TextField textField){
                if(textField.isEditable()){
                    System.out.println("Aun no ganas");
                    return false;
                }
            }
        }
        return true;
    }


        public void OnHandleBGoBack(ActionEvent actionEvent) throws IOException {
        WelcomeStage.getInstance();
        GameStage.deleteInstance();
    }
}
