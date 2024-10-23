package com.example.min_proyecto_2.model;

import javafx.scene.control.TextField;

public interface Game {

    void fillSudokuMatrix();
    void showMatrix();
    void resetMatrix();
    boolean verifyNumber(int[][] tablet, int fil, int col, int num);
    void randomStartingNumbers();
    int[][] getStartingNumbers();
    static int generateRandomNumberInRange(int min, int max) {
        return 0;
    }
    int[][] getMatrix();
    void setMatchedNumbers(int[][] matchedNumbers);
    void setMatrix(int[][] matrix);
    void numberMatchedIn(int i, int j);
    void updateMatchedNumbers(TextField[][] txt);
    boolean verifyWinner();
    boolean isNumberCorrect(String number, int i, int j);
    boolean checkNumberFoolProof(String number);
    boolean checkMaximumNumberOfCharacters(String txt, int cantidadCaracteres);
}
