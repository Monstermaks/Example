package sample.View;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ConfirmOverviewController {

    private MessageOverviewController main;
    private Stage stage;
    private int id;

    @FXML
    private void initialize() {
    }

    public void setMain(MessageOverviewController main) {
        this.main = main;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setId(int id){this.id = id;}

    @FXML
    private void handleConfirm() {
        stage.close();
        main.getStage().close();
        main.getMain().deleteMessage(this.id);
    }

    @FXML
    private void handleCancel() {
        stage.close();
    }
}
