package sample.View;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;

public class MessageInputWindowOverviewController {

    @FXML
    private TextArea messArea;
    @FXML
    private TextField senderField;
    @FXML
    private TextField recipientField;
    @FXML
    private Label errLabel;
    String recipient;
    String mess;
    String domain;

    private Main main;
    private Stage stage;

    @FXML
    private void initialize() {
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setSenderFieldText(String sender) {
        senderField.setText(sender);
    }

    @FXML
    private void handleSend() {
        if (!recipientField.getText().equals("")) {
            recipient = recipientField.getText();
            String[] words = recipient.split("@");
            domain = words[1];
            mess = messArea.getText();
            main.sendMessage(recipient, mess, domain);
            stage.close();
        } else errLabel.setText("Recipient not specified!");
    }
}
