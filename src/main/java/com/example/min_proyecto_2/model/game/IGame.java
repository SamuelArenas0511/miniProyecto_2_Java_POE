package com.example.min_proyecto_2.model.game;

import javafx.scene.control.TextField;

public interface IGame {


    void setMatchedNumbers(int[][] matchedNumbers);
    void setMatrix(int[][] matrix);
    void numberMatchedIn(int i, int j);
    void updateMatchedNumbers(TextField[][] txt);
    boolean verifyWinner();
    boolean isNumberCorrect(String number, int i, int j);
    boolean checkNumberFoolProof(String number);
    boolean checkMaximumNumberOfCharacters(String txt, int cantidadCaracteres);
}
