package sample;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Model.Message;
import sample.View.*;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private Stage mainWindowStage;
    private String user;
    private String pass;
    private String address;
    boolean wrongAddress;

    private ObservableList<Message> messData;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Login");
        this.wrongAddress = false;
        this.address = "localhost";

        initRootLayout();

        showLoginOverview();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/RootLayout.fxml"));
            rootLayout = loader.load();
            RootLayoutController controller = loader.getController();
            controller.setMain(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLoginOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/LoginOverview.fxml"));
            AnchorPane personOverview = loader.load();

            rootLayout.setCenter(personOverview);

            LoginOverviewController controller = loader.getController();
            controller.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] userCheck(String user, String pass) {
        var output = new String[4];
        try {
            var socket = new Socket(address, 110);
            var writer = new PrintWriter(socket.getOutputStream(), true);
            var scanner = new Scanner(socket.getInputStream());
            output[0] = scanner.nextLine();
            writer.println("USER " + user);
            output[1] = scanner.nextLine();
            writer.println("PASS " + pass);
            output[2] = scanner.nextLine();
            writer.println("QUIT");
            output[3] = scanner.nextLine();
            scanner.close();
            writer.close();
            socket.close();
        } catch (SocketException e) {
            this.wrongAddress();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    public void wrongAddress() {
        wrongAddress = true;
    }

    public void setMainWindowStage(Stage mainWindowStage) {
        this.mainWindowStage = mainWindowStage;
    }

    public void openMainWindow() {
        try {
            messData = FXCollections.observableArrayList();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/MainWindowOverview.fxml"));
            AnchorPane page = loader.load();
            MainWindowOverviewController controller = loader.getController();
            controller.setMain(this);
            setMainWindowStage(new Stage());
            mainWindowStage.setTitle("Mail");
            Scene scene = new Scene(page);
            mainWindowStage.setScene(scene);
            controller.setStage(mainWindowStage);
            mainWindowStage.show();
            this.primaryStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initUser(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public ObservableList<Message> getMessData() {
        return messData;
    }

    public void getMessages() {
        try {
            var socket = new Socket(address, 110);
            var writer = new PrintWriter(socket.getOutputStream(), true);
            var scanner = new Scanner(socket.getInputStream());
            writer.println("USER " + user);
            scanner.nextLine();
            writer.println("PASS " + pass);
            scanner.nextLine();
            writer.println("LIST");
            var ids = new ArrayList<Integer>();
            String reply;
            while (!(reply = scanner.nextLine()).equals(".")) {
                String[] words = reply.split(" ");
                if (!words[0].equals("+OK")) ids.add(Integer.parseInt(words[0]));
            }

            for (Integer id : ids) {
                writer.println("RETR " + id);
                StringBuilder mess = new StringBuilder();
                reply = scanner.nextLine();
                do {
                    if (!reply.contains("+OK") && (!reply.contains("Received:"))) mess.append(reply).append(" ");
                } while (!(reply = scanner.nextLine()).equals("."));
                messData.add(new Message(id, mess.toString()));
            }

            writer.println("QUIT");
            scanner.nextLine();
            scanner.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeAddress() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/SettingsOverview.fxml"));
            AnchorPane page = loader.load();
            SettingsOverviewController controller = loader.getController();
            controller.setMain(this);
            Stage settingsWindowStage = new Stage();
            settingsWindowStage.setTitle("Server settings");
            settingsWindowStage.initModality(Modality.WINDOW_MODAL);
            settingsWindowStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            settingsWindowStage.setScene(scene);
            controller.setStage(settingsWindowStage);
            settingsWindowStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openMessageInputWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/MessageInputWindowOverview.fxml"));
            AnchorPane page = loader.load();
            MessageInputWindowOverviewController controller = loader.getController();
            controller.setMain(this);
            Stage messageInputWindowStage = new Stage();
            messageInputWindowStage.setTitle("Message input");
            messageInputWindowStage.initModality(Modality.WINDOW_MODAL);
            messageInputWindowStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            messageInputWindowStage.setScene(scene);
            controller.setSenderFieldText(this.getUser());
            controller.setStage(messageInputWindowStage);
            messageInputWindowStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteMessage(int id) {
        try {
            var socket = new Socket(address, 110);
            var writer = new PrintWriter(socket.getOutputStream(), true);
            var scanner = new Scanner(socket.getInputStream());
            writer.println("USER " + user);
            scanner.nextLine();
            writer.println("PASS " + pass);
            scanner.nextLine();
            writer.println("DELE " + id);
            scanner.nextLine();
            writer.println("QUIT");
            scanner.nextLine();
            scanner.close();
            writer.close();
            socket.close();
            mainWindowStage.close();
            this.openMainWindow();
        } catch (SocketException e) {
            this.wrongAddress();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> openMessage(int id) {
        ArrayList<String> reply = new ArrayList<>();
        try {
            var socket = new Socket(address, 110);
            var writer = new PrintWriter(socket.getOutputStream(), true);
            var scanner = new Scanner(socket.getInputStream());
            writer.println("USER " + user);
            scanner.nextLine();
            writer.println("PASS " + pass);
            scanner.nextLine();
            writer.println("RETR " + id);
            String line = scanner.nextLine();
            do {
                if (!line.contains("+OK")) reply.add(line);
            } while (!(line = scanner.nextLine()).equals("."));
            writer.println("QUIT");
            scanner.nextLine();
            scanner.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reply;
    }

    public void sendMessage(String recipient, String mess, String domain) {
        try {
            var socket = new Socket(address, 25);
            var writer = new PrintWriter(socket.getOutputStream(), true);
            var scanner = new Scanner(socket.getInputStream());
            writer.println("HELO " + domain);
            scanner.nextLine();
            writer.println("MAIL FROM:<" + user + "@shttp.srv>");
            scanner.nextLine();
            writer.println("RCPT TO:<" + recipient + ">");
            scanner.nextLine();
            writer.println("DATA");
            scanner.nextLine();
            writer.println(mess);
            scanner.nextLine();
            writer.println(".");
            scanner.nextLine();
            writer.println("QUIT");
            scanner.nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean getWrongAddress() {
        return wrongAddress;
    }

    public String getUser() {
        return this.user;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
