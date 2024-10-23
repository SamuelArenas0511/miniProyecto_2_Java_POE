package com.example.min_proyecto_2.controller;

import com.example.min_proyecto_2.Sounds;
import com.example.min_proyecto_2.model.font.Fonts;
import com.example.min_proyecto_2.model.gameLogic.AGameLogic;
import com.example.min_proyecto_2.model.gameLogic.GameLogic;
import com.example.min_proyecto_2.model.matrixcreator.MatrixCreator;
import com.example.min_proyecto_2.view.GameStage;
import com.example.min_proyecto_2.view.WelcomeStage;
import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class GameController {


    @FXML
    private AnchorPane anchorPaneGame;

    @FXML
    private Label lbNumberHints;

    @FXML
    private Label lbAttempts;

    @FXML
    private Label lbScoreMessage;

    @FXML
    private Label lbUndo;

    @FXML
    private Label lbNotes;

    @FXML
    private Label lbHint;

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
    private GameLogic game;
    private Sounds yeii;

    public void initialize() {

        lbStopWatch.setFont(new Fonts(45, "bold").getFont());
        lbScore.setFont(new Fonts(35, "semibold").getFont());
        bGoBack.setFont(new Fonts(25, "bold").getFont());
        lbNumberHints.setFont(new Fonts(12, "semibold").getFont());
        lbAttempts.setFont(new Fonts(20, "bold").getFont());
        lbScoreMessage.setFont(new Fonts(20, "bold").getFont());
        lbUndo.setFont(new Fonts(20, "bold").getFont());
        lbNotes.setFont(new Fonts(20, "bold").getFont());
        lbHint.setFont(new Fonts(20, "bold").getFont());

        matrixCreator = new MatrixCreator();
        game = new GameLogic();
        textFields = new TextField[6][6];
        isGameFinished = false;
        yeii = new Sounds();
        yeii.loadSound("src/main/resources/com/example/min_proyecto_2/yeii.wav");

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
                textFields[i][j].setStyle("-fx-border-color: TRANSPARENT; -fx-text-fill: #916254;");
                textFields[i][j].setPrefHeight(107);
                textFields[i][j].setPrefWidth(107);
                textFields[i][j].setFont(new Fonts(38,"bold").getFont());
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
                if(handleTextEntered(textField)){
                    return;
                }
                game.updateMatchedNumbers(textFields);
                if(game.isNumberCorrect(event.getCharacter(), i, j)) {
                    styleForCorrectNumber(textField);
                    if(game.getScoredNumbers()[i][j] == 0){
                        applyZoomEffect(lbScore);
                        for (int k = 0; k<250;k++){
                            createConfetti(anchorPaneGame, anchorPaneGame.getLayoutX(), anchorPaneGame.getLayoutY());
                        }
                        yeii.playSound();

                    }
                    game.setScore(game.getScore() + 100, i, j);
                    lbScore.setText(game.getScore() + "");
                    if (game.verifyWinner()){
                        handleGameWon();
                    }
                }else{
                    styleForIncorrectNumber(textField);

                    applyShakeEffect(textField,ivAttempts);
                    game.setAttempts(game.getAttempts() - 1);
                    ivAttempts.setImage(new Image(String.valueOf(getClass().getResource("/com/example/min_proyecto_2/vidas" + game.getAttempts() + ".png"))));
                    if (game.checkLostGame()){
                        handleGameLost();
                    }
                }
            }else if(statusNotes && textField.isEditable()){
                if(handleTextEntered(textField)){
                    return;
                }
                styleForNotes(textField);
            }
            game.unDoStackAdd(textFields);
        });
    }

    private void applyShakeEffect(TextField textField, ImageView imageView) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(50), textField);
        transition.setFromX(0);
        transition.setByX(10);
        transition.setCycleCount(6);
        transition.setAutoReverse(true);
        transition.play();

        TranslateTransition transition2 = new TranslateTransition(Duration.millis(50), imageView);
        transition2.setFromX(0);
        transition2.setByX(10);
        transition2.setCycleCount(6);
        transition2.setAutoReverse(true);
        transition2.play();
    }

    private void applyZoomEffect(Label label) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), label);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.4);
        scaleTransition.setToY(1.4);
        scaleTransition.setCycleCount(2);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
    }

    private void createConfetti(Pane pane, double startX, double startY) {
        Random random = new Random();

        // Crear un círculo pequeño que será el confeti
        Rectangle confetti = new Rectangle(random.nextInt(5)+2,random.nextInt(5)+2, getRandomColor());
        confetti.setLayoutX(startX + random.nextInt(1000)); // Posición inicial cercana al TextField
        confetti.setLayoutY(startY);

        pane.getChildren().add(confetti);

        // Obtener la altura de la ventana (el Pane) para que el confeti caiga hasta el final
        double paneHeight = pane.getHeight();

        // Animación para que el confeti caiga de forma fluida hasta el final de la pantalla
        TranslateTransition transition = new TranslateTransition(Duration.seconds(random.nextDouble(7)+2), confetti);
        transition.setByY(paneHeight - startY); // Caída hasta el final de la pantalla
        transition.setByX(random.nextInt(200) - 25); // Movimiento lateral aleatorio
        transition.setInterpolator(Interpolator.LINEAR); // Movimiento suave y constante
        transition.setOnFinished(e -> pane.getChildren().remove(confetti)); // Eliminar confeti cuando caiga
        transition.play();
    }

    private Color getRandomColor() {
        Random random = new Random();
        return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
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
                        textFields[fil][i].setBackground((new Background(new BackgroundFill(Color.rgb(199, 181, 175, 0.2), CornerRadii.EMPTY, null))));
                        textFields[i][col].setBackground((new Background(new BackgroundFill(Color.rgb(199, 181, 175,0.2), CornerRadii.EMPTY, null))));
                    }
                    int subFil = (fil / 2) * 2;
                    int subcol = (col / 3) * 3;
                    for (int i = subFil; i < subFil + 2; i++) {
                        for (int j = subcol; j < subcol + 3; j++) {
                            textFields[i][j].setBackground((new Background(new BackgroundFill(Color.rgb(199, 181, 175, 0.2), CornerRadii.EMPTY, null))));
                        }
                    }
                    textField.setBackground((new Background(new BackgroundFill(Color.rgb(199, 181, 175, 0.6), CornerRadii.EMPTY, null))));
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
    void onHandleBHint(ActionEvent event) throws IOException {
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
                    styleForCorrectNumber(textField);
                    game.setScore(game.getScore() + 100, game.getHintRowPosition(), game.getHintColumnPosition());
                    lbScore.setText(game.getScore() + "");
                    if (game.verifyWinner()){
                        handleGameWon();
                    };
                }
            }
            game.unDoStackAdd(textFields);
        }else{
            hintAlert();
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
                        textFields[i][j].setStyle(game.getUnDoStackActionStyle().peek()[i][j]);
                    }
                }
            }
            game.updateMatchedNumbers(textFields);
        }
    }

    private void restartGame(){
        for(int i = 0; i < textFields.length; i++){
            for(int j = 0; j < textFields[i].length; j++){
                if(textFields[i][j].isEditable()){
                    textFields[i][j].setText("");
                }
            }
        }
        game.getUnDoStackAction().clear();
        game.getUnDoStackActionStyle().clear();
        game.restartScore();
        game.updateMatchedNumbers(textFields);
        seconds = 0;
        lbStopWatch.setText(String.format("%02d:%02d", 0, 0));
        timeline.play();
        statusNotes = false;
        lbStatusNotes.setText("Off");
        game.setAttempts(3);
        lbScore.setText(String.valueOf(game.getScore()));
        numberOfHints = 3;
        lbNumberHints.setText(numberOfHints + "");
        ivAttempts.setImage(new Image(String.valueOf(getClass().getResource("/com/example/min_proyecto_2/vidas" + game.getAttempts() + ".png"))));

    }


    public void OnHandleBGoBack(ActionEvent actionEvent) throws IOException {
        WelcomeStage.getInstance();
        GameStage.getInstance().closeInstance();
        timeline.stop();
    }
    @FXML
    void OnHandleBGoBack2(MouseEvent event) throws IOException {
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

    public void styleForCorrectNumber(TextField textField){
        textField.setStyle("-fx-border-color: TRANSPARENT; -fx-text-fill: #29507D");
        textField.setFont(new Fonts(38,"bold").getFont());
    }

    public void handleGameWon(){
        timeline.stop();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡VICTORIA!");
        alert.setHeaderText(null);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(
                new Image(this.getClass().getResource("/com/example/min_proyecto_2/image/trofeo.png").toString()));

        alert.setGraphic(new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/min_proyecto_2/image/trofeo.png")))));
        alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/min_proyecto_2/Style/style.css")).toExternalForm());

        Text congratulatoryText = new Text("¡Felicidades, has ganado! \n");
        Text scoreText = new Text("Puntaje Total: " + game.getScore() + "\n");
        Text timeText = new Text("Tiempo Realizado: " + lbStopWatch.getText());

        congratulatoryText.setFill(Color.rgb(48, 47, 47));
        congratulatoryText.setStyle("-fx-font-weight: bold");
        scoreText.setFill(Color.rgb(41, 80, 125));
        timeText.setFill(Color.rgb(41, 80, 125));

        TextFlow textFlow = new TextFlow(congratulatoryText, scoreText, timeText);
        textFlow.setStyle("-fx-padding: 15px;");


        alert.getDialogPane().setContent(textFlow);

        alert.showAndWait();
        isGameFinished = true;
        try {
            GameStage.getInstance().closeInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            WelcomeStage.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleGameLost(){
        timeline.stop();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Perdiste!");
        alert.setHeaderText(null);

        ButtonType customButton = new ButtonType("Intentar Nuevamente", ButtonBar.ButtonData.OK_DONE);

        alert.getButtonTypes().setAll(customButton);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(
                new Image(this.getClass().getResource("/com/example/min_proyecto_2/image/lost.png").toString()));

        alert.setGraphic(new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/min_proyecto_2/image/lost.png")))));
        alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/min_proyecto_2/Style/style.css")).toExternalForm());

        Text titleText = new Text("Juego Finalizado \n");
        Text lostText = new Text("Se han acabado los intentos :( \n");
        Text timeText = new Text("Tiempo Alcanzado: " + lbStopWatch.getText());

        titleText.setFill(Color.BLACK);
        titleText.setStyle("-fx-font-weight: bold");
        lostText.setFill(Color.rgb(48, 47, 47));
        timeText.setFill(Color.rgb(48, 47, 47));

        TextFlow textFlow = new TextFlow(titleText, lostText,timeText);
        textFlow.setStyle("-fx-padding: 15px; -fx-text-alignment: center");


        alert.getDialogPane().setContent(textFlow);

        alert.showAndWait();

        restartGame();
    }

    public boolean handleTextEntered(TextField textField){
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
            return true;
        }
        return false;
    }

    public void styleForIncorrectNumber(TextField textField){
        textField.setStyle("-fx-border-color: TRANSPARENT; -fx-text-fill: #9c4040");
        textField.setFont(new Fonts(38,"bold").getFont());
    }

    public void styleForNotes(TextField textField){
        textField.setStyle("-fx-border-color: TRANSPARENT; -fx-text-fill: #585959");
        textField.setFont(new Fonts(38,"bold").getFont());
    }

    public void hintAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Sin pistas!");
        alert.setHeaderText(null);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(
                new Image(this.getClass().getResource("/com/example/min_proyecto_2/image/sinPista.png").toString()));


        alert.setGraphic(new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/min_proyecto_2/image/sinPista.png")))));
        alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/min_proyecto_2/Style/style.css")).toExternalForm());

        alert.setContentText("¡Te quedaste sin pistas! :(");
        alert.showAndWait();
    }
}
