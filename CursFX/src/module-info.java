module CursFX {
    requires javafx.controls;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.fxml;
    opens sample.Model;
    opens sample;
    opens sample.View;
}