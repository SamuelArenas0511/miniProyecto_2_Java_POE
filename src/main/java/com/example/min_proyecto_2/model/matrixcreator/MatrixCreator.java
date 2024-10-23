package com.example.min_proyecto_2.model.matrixcreator;

import com.example.min_proyecto_2.model.AGame;

import java.util.Random;

/**
 * The {@code MatrixCreator} class is responsible for generating a 6x6 Sudoku matrix.
 * It extends {@link AGame} and provides implementations for filling the Sudoku matrix, resetting the matrix,
 * verifying if a number can be placed in a given position, and generating random starting numbers.
 * This class generates a valid Sudoku board by checking row, column, and subgrid constraints.
 *
 * The class uses a backtracking-like approach to place numbers in the matrix with a limited number of attempts.
 * If placement fails after a set number of attempts, the board is reset and the process restarts.
 *
 * This implementation is designed for a 6x6 variant of Sudoku.
 *
 * @author Nicolas Cordoba
 * @author Samuel Arenas
 */
public class MatrixCreator extends AGame {

    /**
     * The 6x6 Sudoku matrix.
     * It stores the generated numbers for the Sudoku board.
     */
    private final int[][] matrix = new int[6][6];

    /**
     * The 6x6 matrix that keeps track of the starting numbers of the Sudoku puzzle.
     * These positions are used to determine which numbers the user is going to see at
     * the start of the game.
     */
    private final int[][] startingNumbers = new int[6][6];

    /**
     * Constructor for {@code MatrixCreator}.
     * It initializes the Sudoku matrix by resetting it.
     */
    public MatrixCreator() {
        resetMatrix();
    }

    @Override
    public void fillSudokuMatrix() {

        int attempts, num;
        int max_attempts = 100;
        Random r = new Random();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                attempts = 0;
                do {
                    num = r.nextInt(6)+ 1;
                    attempts++;
                    if (attempts > max_attempts) {
                        System.out.println("Demasiados intentos, reiniciando tablero.");
                        showMatrix();
                        resetMatrix();
                        i = 0;
                        j = -1;
                        break;
                    }
                } while (!verifyNumber(matrix, i, j, num));
                if (attempts <= max_attempts) {
                    matrix[i][j] = num;
                }
            }
        }

        showMatrix();
    }

    @Override
    public void showMatrix() {
        String show= "";
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                show += matrix[i][j] + " ";
            }
            show += "\n";
        }
        System.out.println(show);
    }

    @Override
    public void resetMatrix() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    @Override
    public boolean verifyNumber(int[][] board, int row, int col, int num) {
        for (int i = 0; i < board.length; i++) {
            if (board[row][i] == num && i != col) {
                return false;
            }
        }
        for (int i = 0; i < board.length; i++) {
            if (board[i][col] == num && i != row) {
                return false;
            }
        }
        int subrow = (row / 2) * 2;
        int subcolumn = (col / 3) * 3;
        for (int i = subrow; i < subrow + 2; i++) {
            for (int j = subcolumn; j < subcolumn + 3; j++) {
                if (board[i][j] == num && i != row && j != col) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void randomStartingNumbers() {
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[i].length; j++) {
                startingNumbers[i][j] = 0;
            }
        }
        int row;
        int column;
        int rangerow = 1;
        int rangefcol = 2;
        for(int i = 0; i < 6; i += 2){
            for(int j = 0; j < 6; j += 3){
                for(int k = 0; k < 2; k++) {
                    do {
                        row = generateRandomNumberInRange(i, i + rangerow);
                        column = generateRandomNumberInRange(j, j + rangefcol);
                    } while (startingNumbers[row][column] == 1);
                    startingNumbers[row][column] = 1;
                }
            }
        }
    }

    @Override
    public int[][] getStartingNumbers() {
        return startingNumbers;
    }


    public static int generateRandomNumberInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    @Override
    public int[][] getMatrix() {
        return matrix;
    }
}
