package com.example.min_proyecto_2.controller;

import com.example.min_proyecto_2.model.font.Fonts;
import com.example.min_proyecto_2.model.game.Game;
import com.example.min_proyecto_2.model.matrixcreator.MatrixCreator;
import com.example.min_proyecto_2.view.GameStage;
import com.example.min_proyecto_2.view.WelcomeStage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
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
    private boolean isGameFinished;

    private TextField[][] textFields;

    private MatrixCreator matrixCreator;
    private Game game;

    public void initialize() {

        lbStopWatch.setFont(new Fonts(40, "bold").getFont());
        lbScore.setFont(new Fonts(35, "semibold").getFont());
        bGoBack.setFont(new Fonts(21, "bold").getFont());
        lbNumberHints.setFont(new Fonts(12, "semibold").getFont());

        matrixCreator = new MatrixCreator();
        game = new Game();
        textFields = new TextField[6][6];
        isGameFinished = false;

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
                textFields[i][j] = new TextField();
                textFields[i][j].setBackground((new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, null))));
                textFields[i][j].setStyle("-fx-border-color: TRANSPARENT; -fx-text-fill: #101343");
                textFields[i][j].setFont(new Fonts(40,"bold").getFont());
                textFields[i][j].setAlignment(Pos.CENTER);
                if(matrixCreator.getStartingNumbers()[i][j] == 1){
                    textFields[i][j].setText(matrixCreator.getMatrix()[i][j] + "");
                    textFields[i][j].setEditable(false);
                    textFields[i][j].setDisable(false);
                }
                onHandleEntryTxt(textFields[i][j],i,j);
                onHandlePressedTxt(textFields[i][j],i,j);
                focusedProperty(textFields[i][j], i , j);
                sudokuGridPane.add(textFields[i][j], j, i);

            }
        }
        game.unDoStackAdd(textFields);

    }

    private void onHandlePressedTxt(TextField textField, int i, int j) {
        textField.setOnKeyPressed((KeyEvent event) -> {
            if(!event.getCode().isDigitKey()){
                if (event.getCode() == KeyCode.BACK_SPACE && textField.getCaretPosition() == 0) {
                    game.unDoStackAdd(textFields);
                }
            }
        });
    }


    private void onHandleEntryTxt(TextField textField, int i, int j) {
        textField.setOnKeyTyped(event -> {
            if(!statusNotes && textField.isEditable()){

                if(game.checkMaximumNumberOfCharacters(textField.getText(), 1)){
                    if(textField.getText().substring(0,1).equals(textField.getText().substring(1,2))){
                        textField.setText("");
                    }else {
                        textField.setText(textField.getText().substring(1, 2));
                        textField.positionCaret(1);
                    }
                }

                if(!game.checkNumberFoolProof(textField.getText())){
                    textField.setText("");
                    game.updateMatchedNumbers(textFields);
                    game.unDoStackAdd(textFields);
                    return;
                }

                game.updateMatchedNumbers(textFields);
                System.out.println(String.valueOf(event.getCode()));

                if(game.isNumberCorrect(event.getCharacter(), i, j)) {
                    textField.setStyle("-fx-border-color: TRANSPARENT; -fx-text-fill: #29677d");
                    textField.setFont(new Fonts(40,"bold").getFont());
                    game.setScore(game.getScore() + 100, i, j);
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
                        textField.setStyle("-fx-border-color: TRANSPARENT; -fx-text-fill: #9c4040");
                        textField.setFont(new Fonts(40,"bold").getFont());
                        game.setAttempts(game.getAttempts() - 1);
                        ivAttempts.setImage(new Image(String.valueOf(getClass().getResource("/com/example/min_proyecto_2/vidas" + game.getAttempts() + ".png"))));

                        if (game.checkLostGame()){
                            timeline.stop();
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("¡Perdiste!");
                            alert.setHeaderText(null);
                            alert.setContentText("¡Te has quedado sin intentos, intenta nuevamente " );
                            alert.showAndWait();
                            isGameFinished = true;
                            try {
                                WelcomeStage.getInstance();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            GameStage.deleteInstance();

                        }
                    }
            }else if(statusNotes && textField.isEditable()){
                if(game.checkMaximumNumberOfCharacters(textField.getText(), 6)){
                    if(textField.getText().substring(0,1).equals(textField.getText().substring(1,2))){
                        textField.setText("");
                    }else {
                        textField.setText(textField.getText().substring(1, 2));
                        textField.positionCaret(textField.getText().length() - 1);
                    }
                }
                if(!game.checkNumberFoolProof(textField.getText())){
                    textField.setText("");
                    game.unDoStackAdd(textFields);
                    return;
                }
                textField.setStyle("-fx-border-color: TRANSPARENT; -fx-text-fill: rgba(124,78,23,0.65)");
                textField.setFont(new Fonts(40,"bold").getFont());
            }
            game.unDoStackAdd(textFields);
        });
    }

    private void stopWatch() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateStopWatch()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        isStopWatchOn = true;
    }

    public void continueGame() {
        timeline.play();
        isStopWatchOn = true;
    }


    private void updateStopWatch(){
        seconds++;
        int minutes = seconds / 60;
        int secondremainder = seconds % 60;
        lbStopWatch.setText(String.format("%02d:%02d", minutes, secondremainder));
    }

    public void focusedProperty(TextField textField, int fil, int col) {
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    for(int i = 0; i < textFields.length; i++){
                        textFields[fil][i].setBackground((new Background(new BackgroundFill(Color.rgb(153, 41, 55, 0.2), CornerRadii.EMPTY, null))));
                        textFields[i][col].setBackground((new Background(new BackgroundFill(Color.rgb(153, 41, 55,0.2), CornerRadii.EMPTY, null))));
                    }
                    int subFil = (fil / 2) * 2;
                    int subcol = (col / 3) * 3;
                    for (int i = subFil; i < subFil + 2; i++) {
                        for (int j = subcol; j < subcol + 3; j++) {
                            textFields[i][j].setBackground((new Background(new BackgroundFill(Color.rgb(153, 41, 55, 0.2), CornerRadii.EMPTY, null))));
                        }
                    }
                    textField.setBackground((new Background(new BackgroundFill(Color.rgb(153, 41, 55, 0.5), CornerRadii.EMPTY, null))));
                } else {
                    for(int i = 0; i < textFields.length; i++){
                        textFields[fil][i].setBackground((new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, null))));
                        textFields[i][col].setBackground((new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, null))));
                    }
                    int subFil = (fil / 2) * 2;
                    int subcol = (col / 3) * 3;
                    for (int i = subFil; i < subFil + 2; i++) {
                        for (int j = subcol; j < subcol + 3; j++) {
                            textFields[i][j].setBackground((new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, null))));
                        }
                    }
                }
            }
        });
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
                    textField.setBackground((new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, null))));
                    textField.setStyle("-fx-border-color: TRANSPARENT; -fx-text-fill: #29677d");
                    textField.setFont(new Fonts(40,"bold").getFont());
                    game.setScore(game.getScore() + 100, game.getHintRowPosition(), game.getHintColumnPosition());
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
            game.unDoStackAdd(textFields);
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
        if(game.getUnDoStackAction().size() > 1) {
            game.unDoStackPop();
            for (int i = 0; i < textFields.length; i++) {
                for (int j = 0; j < textFields[i].length; j++) {
                    if(game.getUnDoStackAction().peek()[i][j] == 0){
                        textFields[i][j].setText("");
                    }
                    else {
                        textFields[i][j].setText(String.valueOf(game.getUnDoStackAction().peek()[i][j]));
                        if(game.getUnDoStackAction().peek()[i][j] == matrixCreator.getMatrix()[i][j] && matrixCreator.getStartingNumbers()[i][j] == 0){
                            textFields[i][j].setStyle("-fx-background-color: TRANSPARENT; -fx-border-color: TRANSPARENT; -fx-text-fill: #29677d");
                            textFields[i][j].setFont(new Fonts(40,"bold").getFont());
                        }
                    }
                }
            }
            game.updateMatchedNumbers(textFields);
        }
    }


        public void OnHandleBGoBack(ActionEvent actionEvent) throws IOException {
        WelcomeStage.getInstance();
        GameStage.getInstance().closeInstance();
        timeline.stop();
    }

    public String getGameTime() {
        return lbStopWatch.getText();
    }

    public boolean getIsGameFinished() {
        return isGameFinished;
    }
}
