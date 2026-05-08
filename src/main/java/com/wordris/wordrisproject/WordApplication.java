package com.wordris.wordrisproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WordApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/com/wordris/wordrisproject/menu.fxml")
        );
        Scene scene = new Scene(loader.load());
        stage.setTitle("Wordris");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
