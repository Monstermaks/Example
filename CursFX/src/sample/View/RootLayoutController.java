package sample.View;

import javafx.fxml.FXML;
import sample.Main;

public class RootLayoutController {

    private Main main;

    @FXML
    private void initialize() {
    }

    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    private void handleSettings() {
        main.changeAddress();
    }
}
