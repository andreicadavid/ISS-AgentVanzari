module com.example.program_agentvanzari {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires junit;

    opens com.example.program_agentvanzari to javafx.fxml;
    exports com.example.program_agentvanzari;

    opens com.example.program_agentvanzari.Domain;
    opens com.example.program_agentvanzari.Repository;
    opens com.example.program_agentvanzari.Service;
}