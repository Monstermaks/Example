package sample.View;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.Main;

public class LoginOverviewController {

    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errLabel;

    private boolean isUserWrong;

    private Main main;

    public LoginOverviewController() {
    }

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleLogin() {
        errLabel.setText("");
        String login = loginField.getText();
        String pass = passwordField.getText();
        String[] output = main.userCheck(login, pass);
        for (int i = 0; i < 4; i++) {
            String[] words = new String[4];
            if (!main.getWrongAddress()) words = output[i].split(" ");
            if (main.getWrongAddress()) {
                errLabel.setText("Wrong server address!");
                isUserWrong = true;
                break;
            } else if (!words[0].contains("+OK")) {
                errLabel.setText("Wrong username or password!");
                isUserWrong = true;
                break;
            } else isUserWrong = false;
        }
        if (!isUserWrong) {
            main.initUser(login, pass);
            main.openMainWindow();
        }
    }

    public void setMain(Main main) {
        this.main = main;
    }
}