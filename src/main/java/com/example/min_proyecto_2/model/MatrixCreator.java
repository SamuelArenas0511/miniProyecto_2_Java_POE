package com.example.min_proyecto_2.model;

import java.util.Random;

public class MatrixCreator {

    private final int[][] matrix = new int[6][6];

    public MatrixCreator(){resetMatrix();}

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
                        showmatrix();
                        resetMatrix();
                        i = 0;
                        j = -1;
                        break;
                    }
                } while (!numberVerification(matrix, i, j, num));
                if (intentos <= max_int) {
                    matrix[i][j] = num;
                }
            }
        }

        showmatrix();
    }

    public void showmatrix() {
        String show= "";
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                show += matrix[i][j] + " ";
            }
            show += "\n";
        }
        System.out.println(show);
    }

    public void resetMatrix() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    public boolean numberVerification(int[][] tablet, int fil, int col, int num) {
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

    public int[][] getMatrix() {
        return matrix;
    }
}
