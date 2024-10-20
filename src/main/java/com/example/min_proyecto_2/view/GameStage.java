package com.example.min_proyecto_2.view;

import com.example.min_proyecto_2.controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GameStage extends Stage {

    GameController gameController;

    public GameStage() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/min_proyecto_2/game-view.fxml"));
        Parent root = loader.load();
        gameController = loader.getController();
        Scene scene = new Scene(root);
        this.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/min_proyecto_2/image/icon.png")));
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/min_proyecto_2/Style/style.css")).toExternalForm());
        setTitle("Sudoku");
        setResizable(false);
        setScene(scene);
        show();
    }

    public GameController getGameController(){
        return gameController;
    }
    
    private static class GameStageHolder {
        private static GameStage INSTANCE;
    }

    public static GameStage getInstance() throws IOException{
        GameStageHolder.INSTANCE =
                GameStageHolder.INSTANCE != null ?
                        GameStageHolder.INSTANCE : new GameStage();
        return GameStageHolder.INSTANCE;
    }

    public static void deleteInstance(){
        GameStageHolder.INSTANCE.close();
        GameStageHolder.INSTANCE = null;
    }

    public void closeInstance(){
        hide();
    }




}
