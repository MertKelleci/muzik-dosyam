package sample.DataBase;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Album {
    private SimpleIntegerProperty ID;
    private SimpleStringProperty Ad;
    private SimpleIntegerProperty Sanatci_ID;
    private SimpleIntegerProperty Sarki_Adet;
    private SimpleIntegerProperty Tarih;
    private SimpleStringProperty Tur;

    public Album(){
        ID = new SimpleIntegerProperty();
        Ad = new SimpleStringProperty();
        Sanatci_ID = new SimpleIntegerProperty();
        Sarki_Adet = new SimpleIntegerProperty();
        Tarih = new SimpleIntegerProperty();
        Tur = new SimpleStringProperty();
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

    public int getSanatci_ID() {
        return Sanatci_ID.get();
    }

    public void setSanatci_ID(int sanatci_ID) {
        this.Sanatci_ID.set(sanatci_ID);
    }

    public int getSarki_Adet() {
        return Sarki_Adet.get();
    }

    public void setSarki_Adet(int sarki_Adet) {
        this.Sarki_Adet.set(sarki_Adet);
    }

    public int getTarih() {
        return Tarih.get();
    }

    public void setTarih(int tarih) {
        this.Tarih.set(tarih);
    }

    public String getTur() {
        return Tur.get();
    }

    public void setTur(String tur) {
        this.Tur.set(tur);
    }
}
