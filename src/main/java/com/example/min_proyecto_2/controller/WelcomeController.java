package com.example.min_proyecto_2.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class WelcomeController {

    @FXML
    private GridPane gPane;

    @FXML
    private Button btnPlay;
    private int[][] matriz = new int[6][6];
    @FXML

    public void handleOnPlay(javafx.event.ActionEvent actionEvent) {
        reiniciarMatriz(matriz);
        createTablero(matriz);
        Random r = new Random();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                TextField textField = new TextField();
                textField.setStyle("-fx-background-color: TRANSPARENT; -fx-border-color: TRANSPARENT; -fx-font-family: fantasy; -fx-font-size: 20");
                textField.setAlignment(Pos.CENTER);
                if(r.nextInt(4) == 0){
                    textField.setText(matriz[i][j] + "");
                }
                gPane.add(textField, j, i);

            }
        }
        gPane.setGridLinesVisible(true);
    }

    void createTablero(int[][] matriz) {
        String show = "";
        int intentos, num;
        int max_int = 10000;
        Random r = new Random();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                intentos = 0;
                do {
                    num = r.nextInt(6)+ 1;
                    intentos++;
                    if (intentos > max_int) {
                        System.out.println("Demasiados intentos, reiniciando tablero.");
                        reiniciarMatriz(matriz);
                        i = 0;
                        j = -1;
                        break;
                    }
                } while (!verification(matriz, i, j, num));
                if (intentos <= max_int) {
                    matriz[i][j] = num;
                }
            }
        }

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                show += matriz[i][j] + " ";
            }
            show += "\n";
        }
        System.out.println(show);
    }

    void reiniciarMatriz(int[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                matriz[i][j] = 0;
            }
        }
        for (var node : gPane.getChildren()) {
            if (node instanceof TextField textField) {
                textField.setText("");
            }
        }


    }

    boolean verification(int[][] tablet, int fil, int col, int num) {
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

}
