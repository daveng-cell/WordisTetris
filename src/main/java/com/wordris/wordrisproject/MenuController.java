package com.wordris.wordrisproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuController {

    @FXML private Button playButton;
    @FXML private Button infoButton;
    @FXML private Button quitButton;

    @FXML
    protected void onPlay() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/wordris/wordrisproject/game.fxml")
            );
            Scene gameScene = new Scene(loader.load());

            Stage stage = (Stage) playButton.getScene().getWindow();
            stage.setScene(gameScene);
            stage.setTitle("Wordris");

            GameController controller = loader.getController();
            controller.initGame(stage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML //when  the info button is pressed it generates this window below
    protected void onInfo() {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(infoButton.getScene().getWindow());
        popup.setTitle("About Wordris");
        popup.setResizable(false);

        VBox content = new VBox(14);
        content.getStyleClass().add("popup-root");
        content.setPrefWidth(360);

        Label title = new Label("WORDRIS");
        title.getStyleClass().add("popup-title");

        Label byLabel = new Label("BY:");
        byLabel.getStyleClass().add("popup-label");
        Label byText = new Label("Bleeeeeeeeehhhh");
        byText.getStyleClass().add("popup-text");

        Label aboutLabel = new Label("ABOUT:");
        aboutLabel.getStyleClass().add("popup-label");
        Label aboutText = new Label("Wordris is a word-building puzzle game. Drop letter blocks to form valid words and score points.");
        aboutText.getStyleClass().add("popup-text");
        aboutText.setWrapText(true);
        aboutText.setMaxWidth(300);

        Label rulesLabel = new Label("RULES:");
        rulesLabel.getStyleClass().add("popup-label");
        Label rulesText = new Label(
            "1. Use arrow keys to move blocks.\n" +
            "2. Press SPACE to drop a block.\n" +
            "3. Form valid words horizontally.\n" +
            "4. Longer words = more points.\n" +
            "5. Press C to swap with reserve."
        );
        rulesText.getStyleClass().add("popup-text");
        rulesText.setWrapText(true);
        rulesText.setMaxWidth(300);

        Button closeBtn = new Button("CLOSE");
        closeBtn.getStyleClass().add("btn-secondary");
        closeBtn.setMaxWidth(Double.MAX_VALUE);
        closeBtn.setOnAction(e -> popup.close());

        content.getChildren().addAll(
            title, byLabel, byText,
            aboutLabel, aboutText,
            rulesLabel, rulesText,
            closeBtn
        );

        Scene popupScene = new Scene(content);
        popupScene.getStylesheets().add(
            getClass().getResource("/com/wordris/wordrisproject/styles.css").toExternalForm()
        );
        popup.setScene(popupScene);
        popup.showAndWait();
    }

    @FXML //closes the application on press
    protected void onQuit() {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }
}