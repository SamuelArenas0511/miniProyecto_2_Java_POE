package com.example.min_proyecto_2.model.matrixcreator;

import java.util.Random;

public class MatrixCreator implements IMatrixCreator {

    private final int[][] matrix = new int[6][6];

    private final int[][] startingNumbers = new int[6][6];

    public MatrixCreator(){resetMatrix();}

    @Override
    public void fillSudokuMatrix() {

        int intentos, num;
        int max_int = 100;
        Random r = new Random();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                intentos = 0;
                do {
                    num = r.nextInt(6)+ 1;
                    intentos++;
                    if (intentos > max_int) {
                        System.out.println("Demasiados intentos, reiniciando tablero.");
                        showMatrix();
                        resetMatrix();
                        i = 0;
                        j = -1;
                        break;
                    }
                } while (!verifyNumber(matrix, i, j, num));
                if (intentos <= max_int) {
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
    public boolean verifyNumber(int[][] tablet, int fil, int col, int num) {
        for (int i = 0; i < tablet.length; i++) {
            if (tablet[fil][i] == num && i != col) {
                return false;
            }
        }
        for (int i = 0; i < tablet.length; i++) {
            if (tablet[i][col] == num && i != fil) {
                return false;
            }
        }
        int subFil = (fil / 2) * 2;
        int subcol = (col / 3) * 3;
        for (int i = subFil; i < subFil + 2; i++) {
            for (int j = subcol; j < subcol + 3; j++) {
                if (tablet[i][j] == num && i != fil && j != col) {
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
        int fil;
        int col;
        int rangofil = 1;
        int rangofcol = 2;
        for(int i = 0; i < 6; i += 2){
            for(int j = 0; j < 6; j += 3){
                for(int k = 0; k < 2; k++) {
                    do {
                        fil = generateRandomNumberInRange(i, i + rangofil);
                        col = generateRandomNumberInRange(j, j + rangofcol);
                    } while (startingNumbers[fil][col] == 1);
                    startingNumbers[fil][col] = 1;
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
