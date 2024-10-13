package com.example.min_proyecto_2.model.game;

public class Game implements IGame{
    
    int[][] matchedNumbers;
    int[][] matrix;
    
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

    
}
