package sample.DataBase;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {
    private SimpleStringProperty name;
    private SimpleStringProperty password;
    private SimpleIntegerProperty type;


    public User() {
        name = new SimpleStringProperty();
        password = new SimpleStringProperty();
        type = new SimpleIntegerProperty();
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public int getType() {
        return type.get();
    }

    public void setType(int type) {
        this.type.set(type);
    }
}
