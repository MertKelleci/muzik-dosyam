module JavaFxApplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens sample;
    opens sample.DataBase;
}