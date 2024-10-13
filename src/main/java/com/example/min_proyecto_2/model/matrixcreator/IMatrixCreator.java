package com.example.min_proyecto_2.model.matrixcreator;

import java.util.Random;

public interface IMatrixCreator {
    public void fillSudokuMatrix();

    void showMatrix();

    void resetMatrix();

    boolean verifyNumber(int[][] tablet, int fil, int col, int num);

    void randomStartingNumbers();

    int[][] getStartingNumbers();

    static int generateRandomNumberInRange(int min, int max) {
        return 0;
    }

    int[][] getMatrix();
}
