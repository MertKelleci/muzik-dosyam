package sample.DataBase;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Song {
    private SimpleIntegerProperty ID;
    private SimpleStringProperty Ad;
    private SimpleIntegerProperty Tarih;
    private SimpleIntegerProperty Album_ID;
    private SimpleStringProperty Tur;
    private SimpleStringProperty Sure;
    private SimpleIntegerProperty Dinlenme;

    public Song(){
        ID = new SimpleIntegerProperty();
        Ad = new SimpleStringProperty();
        Tarih = new SimpleIntegerProperty();
        Album_ID = new SimpleIntegerProperty();
        Tur = new SimpleStringProperty();
        Sure = new SimpleStringProperty();
        Dinlenme = new SimpleIntegerProperty();
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

    public int getTarih() {
        return Tarih.get();
    }


    public void setTarih(int tarih) {
        this.Tarih.set(tarih);
    }

    public int getAlbum_ID() {
        return Album_ID.get();
    }


    public void setAlbum_ID(int album_ID) {
        this.Album_ID.set(album_ID);
    }

    public String getTur() {
        return Tur.get();
    }


    public void setTur(String tur) {
        this.Tur.set(tur);
    }

    public String getSure() {
        return Sure.get();
    }


    public void setSure(String sure) {
        this.Sure.set(sure);
    }

    public int getDinlenme() {
        return Dinlenme.get();
    }

    public void setDinlenme(int dinlenme) {
        this.Dinlenme.set(dinlenme);
    }
}
