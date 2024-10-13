package com.example.min_proyecto_2.model.game;

import com.example.min_proyecto_2.model.matrixcreator.MatrixCreator;

public class Game implements IGame{
    
    int[][] matchedNumbers;
    int[][] matrix;
    int score;
    int attempts;
    int hintRowPosition = 0;
    int hintColumnPosition = 0;
    
    public Game() {
        matchedNumbers = new int[6][6];
        matrix = new int[6][6];
    }
    @Override
    public void setMatchedNumbers(int[][] matchedNumbers) {
        this.matchedNumbers = matchedNumbers;
    }

    @Override
    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    @Override
    public void numberMatchedIn(int i, int j) {
        matchedNumbers[i][j] = 1;
    }

    @Override
    public boolean verifyWinner(){
        for(int i = 0; i < matchedNumbers.length; i++){
            for(int j = 0; j < matchedNumbers[i].length; j++){
                if(matchedNumbers[i][j] == 0){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean isNumberCorrect(String number, int i, int j){
        return number.equals(String.valueOf(matrix[i][j]));
    }

    @Override
    public boolean checkNumberFoolProof(String number){
        return number.matches("[1-6]");
    }

    @Override
    public boolean checkMaximumNumberOfCharacters(String txt, int cantidadCaracteres){
        return txt.length() > cantidadCaracteres;
    }

    public void setScore(int score){
        this.score = score;
    }

    public int getScore(){
        return score;
    }

    public void setAttempts(int attempts){
        this.attempts = attempts;
    }

    public int getAttempts(){
        return attempts;
    }

    public boolean checkLostGame(){
        if (attempts >= 3) {
            return true;
        }
        return false;
    }

    public void generateHint(){
        int randomNumberForRow;
        int randomNumberForColumn;
        do{
            randomNumberForRow = MatrixCreator.generateRandomNumberInRange(0,5);
            randomNumberForColumn = MatrixCreator.generateRandomNumberInRange(0,5);
        }while(matchedNumbers[randomNumberForRow][randomNumberForColumn] == 1);

        this.hintRowPosition = randomNumberForRow;
        this.hintColumnPosition = randomNumberForColumn;



    }

    public int getHintRowPosition() {
        return hintRowPosition;
    }

    public int getHintColumnPosition() {
        return hintColumnPosition;
    }

    public int getNumberFromArray(int i, int j){
        return matrix[i][j];
    }
}
