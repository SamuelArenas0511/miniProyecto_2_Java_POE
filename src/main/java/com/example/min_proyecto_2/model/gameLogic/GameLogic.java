package com.example.min_proyecto_2.model.gameLogic;

import com.example.min_proyecto_2.model.matrixcreator.MatrixCreator;
import javafx.scene.control.TextField;

import java.util.Stack;

public class GameLogic extends AGameLogic {
    
    private int[][] matchedNumbers;
    private int[][] scoredNumbers;
    private int[][] matrix;
    private Stack<int[][]> unDoStackAction;
    private Stack<String[][]> unDoStackActionStyle;
    private int score;
    private int attempts;
    private int hintRowPosition = 0;
    private int hintColumnPosition = 0;
    
    public GameLogic() {
        matchedNumbers = new int[6][6];
        matrix = new int[6][6];
        unDoStackAction = new Stack<>();
        unDoStackActionStyle = new Stack<>();
        scoredNumbers = new int[6][6];
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
    public void numberMatchedIn(int i, int j){
        matchedNumbers[i][j] = 1;
    }

    @Override
    public void updateMatchedNumbers(TextField[][] txt) {
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[i].length; j++) {
                if(txt[i][j].getText().equals(String.valueOf(matrix[i][j]))) {
                    matchedNumbers[i][j] = 1;
                }else{
                    matchedNumbers[i][j] = 0;
                }
            }
        }
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

    public void setScore(int score, int i, int j){
        for(int k = 0; k < matchedNumbers.length; k++){
            for(int l = 0; l < matchedNumbers[i].length; l++){
                System.out.print(String.valueOf(scoredNumbers[k][l]) + "\t");
            }
            System.out.println();
        }
        System.out.println();
        if(scoredNumbers[i][j] == 0) {
            this.score = score;
            scoredNumbers[i][j] = 1;
        }
    }

    public void restartScore(){
        score = 0;
        for(int k = 0; k < matchedNumbers.length; k++){
            for (int l = 0; l < matchedNumbers[k].length; l++){
                scoredNumbers[k][l] = 0;
            }
        }
        for(int k = 0; k < matchedNumbers.length; k++){
            for (int l = 0; l < matchedNumbers[k].length; l++){
                matchedNumbers[k][l] = 0;
            }
        }
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
        if (attempts == 0) {
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

    public void unDoStackAdd(TextField[][] textFields) {
        int[][] copyTextFields = new int[6][6];
        String[][] copyTextFieldsStyle = new String[6][6];
        for(int i = 0; i < textFields.length; i++){
            for(int j = 0; j < textFields[i].length; j++){
                if(!textFields[i][j].getText().equals("")) {
                    try {
                        Integer.parseInt(textFields[i][j].getText());
                    } catch (NumberFormatException e) {
                        copyTextFields[i][j] = 0;
                        continue;
                    }
                    copyTextFields[i][j] = Integer.parseInt(textFields[i][j].getText());
                }
                else{
                    copyTextFields[i][j] = 0;
                }
                copyTextFieldsStyle[i][j] = textFields[i][j].getStyle();
            }
        }
        unDoStackAction.add(copyTextFields);
        unDoStackActionStyle.add(copyTextFieldsStyle);
    }

    public void unDoStackPop(){
        unDoStackAction.pop();
        unDoStackActionStyle.pop();
    }

    public Stack<int[][]> getUnDoStackAction(){
        return unDoStackAction;
    }

    public Stack<String[][]> getUnDoStackActionStyle(){
        return unDoStackActionStyle;
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

    public int[][] getMatchedNumbers() {
        return matchedNumbers;
    }

    public int[][] getScoredNumbers(){
        return scoredNumbers;
    }
}
