package com.wordris.wordrisproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WordApplication extends Application {
    @Override
    public void start(Stage stage) {
        Board board = new Board();
        Scene scene = new Scene(board.getVisualBoard());
        stage.setTitle("Wordris");
        stage.setScene(scene);
        stage.show();
    }
}
