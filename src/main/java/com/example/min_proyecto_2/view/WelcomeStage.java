package com.example.min_proyecto_2.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class WelcomeStage extends Stage {

    public WelcomeStage() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/min_proyecto_2/welcome-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        this.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/min_proyecto_2/image/icon.png")));
        setResizable(false);
        setTitle("Sudoku");
        setScene(scene);
        show();
    }
    
    private static class WelcomeStageHolder{
        private static WelcomeStage INSTANCE;
    }

    public static WelcomeStage getInstance() throws IOException{
        WelcomeStage.WelcomeStageHolder.INSTANCE =
                WelcomeStage.WelcomeStageHolder.INSTANCE != null ?
                        WelcomeStage.WelcomeStageHolder.INSTANCE : new WelcomeStage();
        return WelcomeStage.WelcomeStageHolder.INSTANCE;
    }

    public static void deleteInstance(){
        WelcomeStage.WelcomeStageHolder.INSTANCE.close();
        WelcomeStage.WelcomeStageHolder.INSTANCE = null;
    }




}
