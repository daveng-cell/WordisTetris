module com.wordris.wordrisproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.wordris.wordrisproject to javafx.fxml;
    exports com.wordris.wordrisproject;
}