module com.example.ood {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    requires java.sql;
    requires java.desktop;


    opens com.example.ood to javafx.fxml;
    exports com.example.ood;
}