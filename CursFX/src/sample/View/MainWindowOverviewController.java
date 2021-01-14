package sample.View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;
import sample.Model.Message;

import java.io.IOException;
import java.util.ArrayList;

public class MainWindowOverviewController {

    @FXML
    private Label userLabel;
    @FXML
    private TableView<Message> messTable;
    @FXML
    private TableColumn<Message, String> messIdColumn;
    @FXML
    private TableColumn<Message, String> messColumn;

    private Main main;
    private Stage stage;
    private int id;

    public MainWindowOverviewController() {
    }

    @FXML
    private void initialize() {
        messIdColumn.setCellValueFactory(cellData -> cellData.getValue().getId());
        messColumn.setCellValueFactory(cellData -> cellData.getValue().getMess());
    }

    public void setMain(Main main) {
        this.main = main;
        userLabel.setText("Active account: " + main.getUser());
        main.getMessages();
        messTable.setItems(main.getMessData());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleWriteMessage() {
        main.openMessageInputWindow();
    }

    @FXML
    private void handleMessSelect() {
        Message mess = messTable.getSelectionModel().getSelectedItem();
        if (mess == null) return;
        String num = mess.getId().toString();
        String[] words = num.split(" ");
        words = words[2].split("\u005D");
        this.id = Integer.parseInt(words[0]);
        ArrayList<String> message = main.openMessage(id);
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/MessageOverview.fxml"));
            AnchorPane page = loader.load();
            MessageOverviewController controller = loader.getController();
            controller.setMain(this);
            Stage messageStage = new Stage();
            messageStage.setTitle("Message");
            messageStage.initModality(Modality.WINDOW_MODAL);
            messageStage.initOwner(stage);
            Scene scene = new Scene(page);
            messageStage.setScene(scene);
            controller.setStage(messageStage);
            controller.setId(id);
            controller.setMess(message);
            messageStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleChangeAccount() {
        stage.close();
        main.start(new Stage());
    }

    public int getId(){
        return id;
    }

    public Main getMain() {
        return main;
    }
}
