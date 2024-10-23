package com.example.min_proyecto_2.model;

import com.example.min_proyecto_2.model.matrixcreator.MatrixCreator;
import javafx.scene.control.TextField;

import java.util.Stack;

/**
 * The {@code Game} interface defines the essential methods for a Sudoku game implementation.
 * This interface provides a contract for any class that implements it to handle the
 * functionality required for managing a Sudoku game, including matrix creation, validation,
 * and game state management.
 *
 * Classes implementing this interface should provide specific implementations for the methods
 * to manage the game's logic and user interactions.
 *
 * @author Nicolas Córdoba, Samuel Arenas
 */
public interface Game {

    /**
     * Fills the 6x6 Sudoku matrix with random numbers according to Sudoku rules.
     * If it fails to place a number after a certain number of attempts, the matrix is reset and the process starts over.
     * The maximum number of attempts per cell is limited to 100.
     */
    void fillSudokuMatrix();

    /**
     * Displays a matrix in the console for readability and debugging purposes.
     */
    void showMatrix();

    /**
     * Resets the Sudoku matrix by setting all values to 0.
     */
    void resetMatrix();

    /**
     * Verifies whether a number can be placed in the specified position according to Sudoku rules.
     * It checks the row, column, and the 2x3 subgrid for conflicts.
     *
     * @param board The Sudoku matrix.
     * @param row The row where the number is to be placed.
     * @param col The column where the number is to be placed.
     * @param num The number to verify.
     * @return {@code true} if the number can be placed, {@code false} otherwise.
     */
    boolean verifyNumber(int[][] board, int row, int col, int num);

    /**
     * Generates random starting numbers in the matrix.
     * These numbers will be the preset values for the Sudoku puzzle.
     */
    void randomStartingNumbers();

    /**
     * Returns the matrix containing the positions of the starting numbers.
     *
     * @return The 6x6 matrix of starting numbers.
     */
    int[][] getStartingNumbers();

    /**
     * Generates a random number within the specified range.
     *
     * @param min The minimum value of the range.
     * @param max The maximum value of the range.
     * @return A random integer within the specified range.
     */
    static int generateRandomNumberInRange(int min, int max) {
        return 0;
    }

    /**
     * Returns the Sudoku matrix.
     *
     * @return The 6x6 Sudoku matrix.
     */
    int[][] getMatrix();

    /**
     * Sets the matched numbers matrix.
     *
     * @param matchedNumbers The matrix containing matched numbers.
     */
    void setMatchedNumbers(int[][] matchedNumbers);

    /**
     * Sets the solution matrix for the Sudoku game.
     *
     * @param matrix The solution matrix.
     */
    void setMatrix(int[][] matrix);

    /**
     * Marks a number as matched in the specified position of the Sudoku matrix.
     *
     * @param i The row index of the matched number.
     * @param j The column index of the matched number.
     */
    void numberMatchedIn(int i, int j);

    /**
     * Updates the matched numbers matrix based on the player's input.
     *
     * @param txt The 2D array of text fields where players input their answers.
     */
    void updateMatchedNumbers(TextField[][] txt);

    /**
     * Verifies if the player has won the game by matching all numbers correctly.
     *
     * @return {@code true} if the player has won, {@code false} otherwise.
     */
    boolean verifyWinner();

    /**
     * Checks if the player's input for a specific position is correct.
     *
     * @param number The player's input number.
     * @param i The row index of the position.
     * @param j The column index of the position.
     * @return {@code true} if the input matches the correct number, {@code false} otherwise.
     */
    boolean isNumberCorrect(String number, int i, int j);

    /**
     * Checks if the player's input is within the valid range (1-6).
     *
     * @param number The player's input.
     * @return {@code true} if the input is valid, {@code false} otherwise.
     */
    boolean checkNumberFoolProof(String number);

    /**
     * Checks if the player's input exceeds the maximum allowed number of characters.
     *
     * @param txt The player's input.
     * @param cantidadCaracteres The maximum allowed number of characters.
     * @return {@code true} if the input exceeds the maximum, {@code false} otherwise.
     */
    boolean checkMaximumNumberOfCharacters(String txt, int cantidadCaracteres);

    /**
     * Updates the player's score if they correctly match a number.
     *
     * @param score The current score.
     * @param i The row index.
     * @param j The column index.
     */
    void setScore(int score, int i, int j);

    /**
     * Resets the player's score and matched numbers.
     */
    void restartScore();

    /**
     * Returns the player's current score.
     *
     * @return The current score.
     */
    int getScore();

    /**
     * Sets the number of remaining attempts for the player.
     *
     * @param attempts The number of attempts.
     */
    void setAttempts(int attempts);

    /**
     * Returns the number of remaining attempts for the player.
     *
     * @return The number of attempts.
     */
    int getAttempts();

    /**
     * Checks if the player has lost the game by running out of attempts.
     *
     * @return {@code true} if the player has lost, {@code false} otherwise.
     */
    boolean checkLostGame();

    /**
     * Generates a hint by randomly selecting a position in the grid that has not yet been matched.
     */
    void generateHint();

    /**
     * Saves the current state of the game for undo purposes.
     *
     * @param textFields The 2D array of text fields representing the current state of the grid.
     */
    void unDoStackAdd(TextField[][] textFields);

    /**
     * Pops the last state from the undo action and style stacks.
     * This method removes the most recent entry from the undo stacks,
     * effectively reverting the last action taken by the player.
     */
    void unDoStackPop();

    /**
     * Retrieves the stack containing the previous states of the Sudoku input.
     *
     * @return A stack of 2D arrays representing previous states of the input matrix.
     */
    Stack<int[][]> getUnDoStackAction();

    /**
     * Retrieves the stack containing the styles of the previous states of the Sudoku input.
     *
     * @return A stack of 2D arrays of strings representing the styles of the previous input fields.
     */
    Stack<String[][]> getUnDoStackActionStyle();

    /**
     * Gets the row position of the current hint provided to the player.
     *
     * @return The row index of the hint position.
     */
    int getHintRowPosition();

    /**
     * Gets the column position of the current hint provided to the player.
     *
     * @return The column index of the hint position.
     */
    int getHintColumnPosition();

    /**
     * Retrieves the number at the specified position in the Sudoku matrix.
     *
     * @param i The row index of the number to retrieve.
     * @param j The column index of the number to retrieve.
     * @return The number at the specified position in the matrix.
     */
    int getNumberFromArray(int i, int j);

    /**
     * Gets the current state of matched numbers in the Sudoku game.
     *
     * @return A 2D array representing the current state of matched numbers.
     */
    int[][] getMatchedNumbers();

    /**
     * Gets the current state of scored numbers in the Sudoku game.
     *
     * @return A 2D array representing the current state of scored numbers.
     */
    int[][] getScoredNumbers();

}
