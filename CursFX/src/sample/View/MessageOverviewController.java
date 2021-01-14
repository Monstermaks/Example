package sample.View;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;
import java.util.ArrayList;

public class MessageOverviewController {

    @FXML
    private TextArea messArea;
    @FXML
    private TextField senderField;
    private MainWindowOverviewController main;
    private Stage stage;
    private Stage confirmStage;
    private int id;

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleDeleteThisMessage(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/ConfirmOverview.fxml"));
            AnchorPane page = loader.load();
            ConfirmOverviewController controller = loader.getController();
            controller.setMain(this);
            this.confirmStage = new Stage();
            confirmStage.setTitle("Confirm");
            Scene scene = new Scene(page);
            confirmStage.setScene(scene);
            controller.setStage(confirmStage);
            controller.setId(this.id);
            confirmStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMain(MainWindowOverviewController main) {
        this.main = main;
    }

    public void setId(int id){this.id = id;}

    public Main getMain(){return this.main.getMain();}

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage(){return this.stage;}

    public void setMess(ArrayList<String> mess) {
        for (int i = 0; i < mess.size(); i++) {
            if (mess.get(mess.size() - 1 - i).contains("Received:")) {
                String[] words = mess.get(mess.size() - 1 - i).split(" ");
                senderField.setText(words[2]);
            } else messArea.insertText(0, mess.get(mess.size() - 1 - i) + "\n");
        }
    }
}
