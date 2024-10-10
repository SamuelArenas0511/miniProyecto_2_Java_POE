package com.example.min_proyecto_2.controller;

import com.example.min_proyecto_2.model.MatrixCreator;
import com.example.min_proyecto_2.view.GameStage;
import com.example.min_proyecto_2.view.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Random;

public class GameController {

    @FXML
    private GridPane sudokuGridPane;

    @FXML
    private Button bStartSudoku;

    @FXML
    private Button bGoBack;

    private MatrixCreator matrixCreator;

    public void initialize() {
        matrixCreator = new MatrixCreator();
    }


    @FXML
    public void onHandleBStartSudoku(javafx.event.ActionEvent actionEvent) {
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
                textField.setStyle("-fx-background-color: TRANSPARENT; -fx-border-color: TRANSPARENT; -fx-font-family: fantasy; -fx-font-size: 20");
                textField.setAlignment(Pos.CENTER);
                if(r.nextInt(4) == 0){
                    textField.setText(matrixCreator.getMatrix()[i][j] + "");
                }
                sudokuGridPane.add(textField, j, i);

            }
        }
    }

    public void OnHandleBGoBack(ActionEvent actionEvent) throws IOException {
        WelcomeStage.getInstance();
        GameStage.deleteInstance();
    }
}
