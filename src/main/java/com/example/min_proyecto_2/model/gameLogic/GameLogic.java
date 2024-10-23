package com.example.min_proyecto_2.model.gameLogic;

import com.example.min_proyecto_2.model.AGame;
import com.example.min_proyecto_2.model.matrixcreator.MatrixCreator;
import javafx.scene.control.TextField;

import java.util.Stack;

/**
 * The {@code GameLogic} class manages the game logic for a Sudoku game.
 * It tracks the current state of the Sudoku grid, the matched numbers, score, attempts, and provides methods for handling user input,
 * verifying correct answers, and providing hints. It also manages undo operations by keeping a stack of previous game states.
 *
 * This class supports a 6x6 Sudoku grid and uses stacks to implement undo functionality for user actions.
 *
 * It provides functionality to:
 * - Check if a player's input matches the correct solution.
 * - Track the player's score and attempts.
 * - Generate hints for players.
 * - Undo player actions.
 *
 * The game ends when either all numbers are matched or when attempts run out.
 *
 * @author Nicolas Cordoba
 * @author Samuel Arenas
 */
public class GameLogic extends AGame {

    /**
     * Tracks the positions of correctly matched numbers in the Sudoku grid.
     * A value of 1 indicates the number in that position has been correctly matched, while 0 means it hasn't.
     */
    private int[][] matchedNumbers;

    /**
     * Stores the positions of numbers that have been used for scoring, to avoid re-scoring the same number.
     */
    private int[][] scoredNumbers;

    /**
     * Stores the solution matrix for the Sudoku game.
     * This matrix holds the correct numbers that need to be matched.
     */
    private int[][] matrix;

    /**
     * A stack that keeps track of previous game states for the undo functionality.
     * Each element is a 2D array representing the grid at a previous point in time.
     */
    private Stack<int[][]> unDoStackAction;

    /**
     * A stack that keeps track of previous style states (e.g., color, text) for undo functionality.
     * Each element is a 2D array of styles for each text field.
     */
    private Stack<String[][]> unDoStackActionStyle;

    /**
     * The player's current score in the game.
     */
    private int score;

    /**
     * The remaining number of attempts the player has to complete the Sudoku puzzle.
     */
    private int attempts;

    /**
     * The row position for the generated hint.
     */
    private int hintRowPosition = 0;

    /**
     * The column position for the generated hint.
     */
    private int hintColumnPosition = 0;

    /**
     * Constructor for {@code GameLogic}.
     * Initializes the matched numbers, scored numbers, and undo stacks.
     */
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
