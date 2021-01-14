package sample.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Message {

    private final StringProperty id;
    private final StringProperty mess;

    public Message(Integer id, String mess) {
        this.id = new SimpleStringProperty(id + "");
        this.mess = new SimpleStringProperty(mess);
    }

    public StringProperty getId() {
        return this.id;
    }

    public StringProperty getMess() {
        return this.mess;
    }
}
