package com.example.min_proyecto_2.controller;

import com.example.min_proyecto_2.model.font.Fonts;
import com.example.min_proyecto_2.model.game.Game;
import com.example.min_proyecto_2.model.matrixcreator.MatrixCreator;
import com.example.min_proyecto_2.view.GameStage;
import com.example.min_proyecto_2.view.WelcomeStage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class GameController {

    @FXML
    private Label lbNumberHints;

    @FXML
    private Label lbStatusNotes;
    private boolean statusNotes = false;

    @FXML
    private GridPane sudokuGridPane;

    @FXML
    private Button bGoBack;

    @FXML
    private Label lbScore;

    @FXML
    private ImageView ivAttempts;

    @FXML
    private Button btnUndoGame;

    @FXML
    private Button btnNote;

    @FXML
    private Button btnHint;
    private int numberOfHints = 3;

    @FXML
    private Label lbStopWatch;
    private int seconds;
    private Timeline timeline;
    private boolean isStopWatchOn;

    private MatrixCreator matrixCreator;
    private Game game;

    public void initialize() {

        lbStopWatch.setFont(new Fonts(40, "bold").getFont());
        lbScore.setFont(new Fonts(35, "semibold").getFont());
        bGoBack.setFont(new Fonts(21, "bold").getFont());
        lbNumberHints.setFont(new Fonts(12, "semibold").getFont());

        matrixCreator = new MatrixCreator();
        game = new Game();
        game.setAttempts(3);
        isStopWatchOn = false;
        stopWatch();

        matrixCreator.resetMatrix();
        matrixCreator.fillSudokuMatrix();
        matrixCreator.randomStartingNumbers();
        game.setMatchedNumbers(matrixCreator.getStartingNumbers());
        game.setMatrix(matrixCreator.getMatrix());

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                TextField textField = new TextField();
                textField.setStyle("-fx-background-color: TRANSPARENT; -fx-border-color: TRANSPARENT; -fx-text-fill: #916254;");
                textField.setFont(new Fonts(40,"bold").getFont());
                textField.setAlignment(Pos.CENTER);
                if(matrixCreator.getStartingNumbers()[i][j] == 1){
                    textField.setText(matrixCreator.getMatrix()[i][j] + "");
                    textField.setEditable(false);
                }
                onHandleEntryTxt(textField,i,j);
                sudokuGridPane.add(textField, j, i);

            }
        }

    }


    private void onHandleEntryTxt(TextField textField, int i, int j) {
        textField.setOnKeyReleased(event -> {
            if(!statusNotes){
                if(game.checkMaximumNumberOfCharacters(textField.getText(), 1)){
                    textField.setText(textField.getText().substring(0, 1));
                    textField.setText("");
                }
                if(!game.checkNumberFoolProof(textField.getText())){
                    textField.setText("");
                    return;
                }
                if(game.isNumberCorrect(textField.getText(), i, j)) {
                    game.numberMatchedIn(i,j);
                    textField.setEditable(false);
                    textField.setStyle("-fx-background-color: TRANSPARENT; -fx-border-color: TRANSPARENT; -fx-text-fill: #29507D");
                    textField.setFont(new Fonts(40,"bold").getFont());
                    game.setScore(game.getScore() + 100);
                    lbScore.setText(game.getScore() + "");
                    if (game.verifyWinner()){
                        timeline.stop();
                        System.out.println("GANASTE");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("¡Ganaste!");
                        alert.setHeaderText(null);
                        alert.setContentText("¡Felicidades, has ganado! en un tiempo de: " + lbStopWatch.getText() );
                        alert.showAndWait();

                    };
                }else{
                    textField.setStyle("-fx-background-color: TRANSPARENT; -fx-border-color: TRANSPARENT; -fx-text-fill: #7D3434");
                    textField.setFont(new Fonts(40,"bold").getFont());
                    game.setAttempts(game.getAttempts() - 1);
                    ivAttempts.setImage(new Image("C:\\Users\\Windows 10 Pro\\IdeaProjects\\min_Proyecto_2\\src\\main\\resources\\com\\example\\min_proyecto_2\\image\\vidas"+game.getAttempts() + ".png"));
                    if (game.checkLostGame()){
                        timeline.stop();
                        System.out.println("Perdiste");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("¡Perdiste!");
                        alert.setHeaderText(null);
                        alert.setContentText("¡Te has quedado sin intentos, intenta nuevamente " );
                        alert.showAndWait();
                        try {
                            WelcomeStage.getInstance();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        GameStage.deleteInstance();
                    }
                }
            }else{
                System.out.println("Esta activado el modo notas");
                textField.setStyle("-fx-background-color: TRANSPARENT; -fx-border-color: TRANSPARENT; -fx-text-fill: #A1A1A1");
                textField.setFont(new Fonts(40,"bold").getFont());
            }

        });
    }

    private void stopWatch() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateStopWatch()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        isStopWatchOn = true;
    }


    private void updateStopWatch(){
        seconds++;
        int minutes = seconds / 60;
        int secondremainder = seconds % 60;
        lbStopWatch.setText(String.format("%02d:%02d", minutes, secondremainder));
    }


    @FXML
    void onHandleBHint(ActionEvent event) {
        if(numberOfHints > 0){
            game.generateHint();
            numberOfHints -= 1;
            lbNumberHints.setText(numberOfHints + "");
            for (var node : sudokuGridPane.getChildren()) {
                Integer rowIndex = GridPane.getRowIndex(node);
                Integer colIndex = GridPane.getColumnIndex(node);
                rowIndex = (rowIndex == null) ? 0 : rowIndex;
                colIndex = (colIndex == null) ? 0 : colIndex;

                if (rowIndex == game.getHintRowPosition() && colIndex == game.getHintColumnPosition() && node instanceof TextField textField) {
                    textField.setText(game.getNumberFromArray(game.getHintRowPosition(), game.getHintColumnPosition()) + "");
                    game.numberMatchedIn(game.getHintRowPosition(),game.getHintColumnPosition());
                    textField.setEditable(false);
                    textField.setStyle("-fx-background-color: TRANSPARENT; -fx-border-color: TRANSPARENT; -fx-text-fill: #29507D");
                    textField.setFont(new Fonts(40,"bold").getFont());
                    game.setScore(game.getScore() + 100);
                    lbScore.setText(game.getScore() + "");
                    if (game.verifyWinner()){
                        timeline.stop();
                        System.out.println("GANASTE");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("¡Ganaste!");
                        alert.setHeaderText(null);
                        alert.setContentText("¡Felicidades, has ganado! en un tiempo de: " + lbStopWatch.getText() );
                        alert.showAndWait();

                    };
                }
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("¡Sin pistas!");
            alert.setHeaderText(null);
            alert.setContentText("¡Te quedaste sin pistas :C" );
            alert.showAndWait();
        }

    }

    @FXML
    void onHandleBNote(ActionEvent event) {
        if(!statusNotes){
            statusNotes = true;
            lbStatusNotes.setText("On");
        }else{
            statusNotes = false;
            lbStatusNotes.setText("Off");
        }
    }

    @FXML
    void onHandleBUndo(ActionEvent event) {

    }


        public void OnHandleBGoBack(ActionEvent actionEvent) throws IOException {
        WelcomeStage.getInstance();
        GameStage.deleteInstance();
    }
}
