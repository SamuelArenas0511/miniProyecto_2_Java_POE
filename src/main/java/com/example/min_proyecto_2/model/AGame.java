package com.example.min_proyecto_2.model;

import javafx.scene.control.TextField;

import java.util.Stack;

/**
 * Abstract class that implements the Game interface for creating and managing a Sudoku matrix.
 * This class serves as a base for the Sudoku game logic and matrix creation.
 * 
 * @author Nicolas Córdoba, Samuel Arenas
 */
public class AGame implements Game {

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
    public boolean verifyNumber(int[][] board, int row, int col, int num) {
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

    @Override
    public void setScore(int score, int i, int j) {

    }

    @Override
    public void restartScore() {

    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public void setAttempts(int attempts) {

    }

    @Override
    public int getAttempts() {
        return 0;
    }

    @Override
    public boolean checkLostGame() {
        return false;
    }

    @Override
    public void generateHint() {

    }

    @Override
    public void unDoStackAdd(TextField[][] textFields) {

    }

    @Override
    public void unDoStackPop() {

    }

    @Override
    public Stack<int[][]> getUnDoStackAction() {
        return null;
    }

    @Override
    public Stack<String[][]> getUnDoStackActionStyle() {
        return null;
    }

    @Override
    public int getHintRowPosition() {
        return 0;
    }

    @Override
    public int getHintColumnPosition() {
        return 0;
    }

    @Override
    public int getNumberFromArray(int i, int j) {
        return 0;
    }

    @Override
    public int[][] getMatchedNumbers() {
        return new int[0][];
    }

    @Override
    public int[][] getScoredNumbers() {
        return new int[0][];
    }
}
