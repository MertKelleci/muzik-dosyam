package sample.DataBase;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Artist {
    private SimpleIntegerProperty ID;
    private SimpleStringProperty Ad;
    private SimpleStringProperty Ulke;

    public Artist(){
        ID = new SimpleIntegerProperty();
        Ad = new SimpleStringProperty();
        Ulke = new SimpleStringProperty();
    }

    public int getID() {
        return ID.get();
    }

    public void setID(int ID) {
        this.ID.set(ID);
    }

    public String getAd() {
        return Ad.get();
    }

    public void setAd(String ad) {
        this.Ad.set(ad);
    }

    public String getUlke() {
        return Ulke.get();
    }

    public void setUlke(String ulke) {
        this.Ulke.set(ulke);
    }
}
