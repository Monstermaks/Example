package sample.View;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;

public class SettingsOverviewController {

    @FXML
    private TextField addressField;

    private Main main;
    private Stage stage;

    @FXML
    private void initialize() {
        addressField.setText("localhost");
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleSetAddress() {
        main.setAddress(addressField.getText());
        stage.close();
    }

    @FXML
    private void handleSetAsDefault() {
        main.setAddress("localhost");
        addressField.setText("localhost");
        stage.close();
    }
}
