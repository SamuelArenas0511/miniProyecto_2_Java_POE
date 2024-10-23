module com.example.min_proyecto_2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jdk.xml.dom;


    opens com.example.min_proyecto_2 to javafx.fxml;
    opens com.example.min_proyecto_2.controller to javafx.fxml;
    exports com.example.min_proyecto_2;
}