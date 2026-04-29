module com.wordris.wordrisproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.xml.dom;


    opens com.wordris.wordrisproject to javafx.fxml;
    exports com.wordris.wordrisproject;
}