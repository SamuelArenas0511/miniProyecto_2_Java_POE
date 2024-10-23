package com.example.min_proyecto_2.model.gameLogic;

import com.example.min_proyecto_2.model.Game;
import javafx.scene.control.TextField;

public class AGameLogic implements Game {

    @Override
    public void fillSudokuMatrix() {

    }

    @Override
    public void showMatrix() {

    }

    @Override
    public void resetMatrix() {

    }

    @Override
    public boolean verifyNumber(int[][] tablet, int fil, int col, int num) {
        return false;
    }

    @Override
    public void randomStartingNumbers() {

    }

    @Override
    public int[][] getStartingNumbers() {
        return new int[0][];
    }

    @Override
    public int[][] getMatrix() {
        return new int[0][];
    }

    @Override
    public void setMatchedNumbers(int[][] matchedNumbers) {

    }

    @Override
    public void setMatrix(int[][] matrix) {

    }

    @Override
    public void numberMatchedIn(int i, int j) {

    }

    @Override
    public void updateMatchedNumbers(TextField[][] txt) {

    }

    @Override
    public boolean verifyWinner() {
        return false;
    }

    @Override
    public boolean isNumberCorrect(String number, int i, int j) {
        return false;
    }

    @Override
    public boolean checkNumberFoolProof(String number) {
        return false;
    }

    @Override
    public boolean checkMaximumNumberOfCharacters(String txt, int cantidadCaracteres) {
        return false;
    }
}
