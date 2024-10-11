package com.example.min_proyecto_2.model;

import javafx.scene.control.TextField;

public class Game {
    
    int[][] matchedNumbers;
    int[][] matrix;
    
    public Game() {
        matchedNumbers = new int[6][6];
        matrix = new int[6][6];
    }

    public void setMatchedNumbers(int[][] matchedNumbers) {
        this.matchedNumbers = matchedNumbers;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public void numberMatchedIn(int i, int j) {
        matchedNumbers[i][j] = 1;
    }

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

    public boolean isNumberCorrect(String number, int i, int j){
        return number.equals(String.valueOf(matrix[i][j]));
    }

    public boolean checkNumberFoolProof(String number){
        return number.matches("[1-6]");
    }

    public boolean checkMaximumNumberOfCharacters(String txt, int cantidadCaracteres){
        return txt.length() > cantidadCaracteres;
    }


    
    
}
