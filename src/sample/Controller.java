package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import sample.DataBase.*;

public class Controller {
    @FXML private TableView<Album> albumTable;
    @FXML private TableView<Artist> artistTable;
    @FXML private TableView<Song> songTable;
    @FXML private TextField nan;
    @FXML private TextField nac;
    @FXML private Label info1;
    @FXML private Label info2;
    @FXML private TextField dan;
    @FXML private TabPane tabPane;
    @FXML private TextField aiName;
    @FXML private TextField aiAName;
    @FXML private TextField aiDate;
    @FXML private ComboBox<String> aiTur;
    @FXML private Label aiInfo;
    @FXML private TextField adName;
    @FXML private Label adInfo;
    @FXML private TextField siName;
    @FXML private TextField siAName;
    @FXML private TextField siTarih;
    @FXML private ComboBox<String> siTur;
    @FXML private TextField siSure;
    @FXML private Label siInfo;
    @FXML private TextField sdName;
    @FXML private Label sdInfo;

    User user = DataSource.getInstance().getUser();

    @FXML
    public void listArtists(){
        if(user.getType()!=2){
            tabPane.setVisible(false);
        }
        Task<ObservableList<Artist>> task = new GetAllArtistsTask();
        artistTable.itemsProperty().bind(task.valueProperty());

        new Thread(task).start();
    }

    @FXML
    public void listAlbumsForArtist(){
        final Artist artist = artistTable.getSelectionModel().getSelectedItem();

        if(artist==null){
            System.out.println("Sanatçı seçilmedi");
            return;
        }

        Task<ObservableList<Album>> task  = new Task<ObservableList<Album>>() {
            @Override
            protected ObservableList<Album> call() throws Exception {
                return FXCollections.observableArrayList(DataSource.getInstance().queryAlbumsByArtistID(artist.getID()));
            }
        };
        albumTable.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
    }

    public void listSongsForAlbum(){
        final Album album = albumTable.getSelectionModel().getSelectedItem();

        if(album == null){
            System.out.println("Albüm seçilmedi");
            return;
        }

        Task<ObservableList<Song>> task = new Task<ObservableList<Song>>() {
            @Override
            protected ObservableList<Song> call() throws Exception {
                return FXCollections.observableArrayList(DataSource.getInstance().querySongsByAlbumID(album.getID()));
            }
        };
        songTable.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
    }

    public void deleteSong(){
        if(sdName.getText().isEmpty()){
            sdInfo.setText("Lütfen silinecek şarkının ismini giriniz");
            sdName.clear();
            return;
        }

        Song flag = new Song();
        flag.setAd(sdName.getText());

        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                return DataSource.getInstance().findSong(flag);
            }
        };

        task.setOnSucceeded(e->{
            if(task.valueProperty().get()){
                DataSource.getInstance().deleteSong(sdName.getText());
                sdName.clear();
                songTable.refresh();
                return;
            }else{
                sdInfo.setText("Silmek istediğiniz şarkı listede yoktur");
                sdName.clear();
                return;
            }
        });

        new Thread(task).start();
    }

    public void newSong(){
        if(siName.getText().isEmpty() || siAName.getText().isEmpty() || siSure.getText().isEmpty() || siTarih.getText().isEmpty()){
            siInfo.setText("Lütfen şarkı bilgilerini eksiksiz giriniz");
            siName.clear();
            siAName.clear();
            siSure.clear();
            siTarih.clear();
            return;
        }

        Song flag = new Song();
        flag.setAd(siName.getText());
        if(DataSource.getInstance().getAlbumID(siAName.getText())==-1){
            siInfo.setText("Şarkının bulunduğu albüm listede yoktur!");
            siName.clear();
            siAName.clear();
            siSure.clear();
            siTarih.clear();
            return;
        }else{
            flag.setAlbum_ID(DataSource.getInstance().getAlbumID(siAName.getText()));
        }
        flag.setTur(siTur.getValue());
        flag.setTarih(Integer.parseInt(siTarih.getText()));

        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                return !DataSource.getInstance().findSong(flag);
            }
        };
        task.setOnSucceeded(e->{
            if(task.valueProperty().get()){
                siName.clear();
                siAName.clear();
                siSure.clear();
                siTarih.clear();
                DataSource.getInstance().addSong(flag);
                songTable.refresh();
            }else{
                siInfo.setText("Şarkı halihazırda bulunmakta!");
                siName.clear();
                siAName.clear();
                siSure.clear();
                siTarih.clear();
            }
        });
        new Thread(task).start();
    }

    public void updateSongView(){
        final Song song = songTable.getSelectionModel().getSelectedItem();

        if(song == null){
            System.out.println("Şarkı seçilmedi");
        }

        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                return DataSource.getInstance().updateViewedCount(song.getID());
            }
        };

        task.setOnSucceeded(e -> {
            if(task.valueProperty().get()){
                song.setDinlenme(song.getDinlenme()+1);
                songTable.refresh();
            }
        });

        new Thread(task).start();
    }

    @FXML
    public void newArtist(){
        if(nac.getText().isEmpty() || nan.getText().isEmpty()){
            info1.setText("Lütfen sanatçı bilgilerini eksiksiz girin!");
            nac.clear();
            nan.clear();
            return;
        }
        Artist flag = new Artist();
        flag.setAd(nan.getText());
        flag.setUlke(nac.getText());

        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                return !(DataSource.getInstance().findArtist(flag));
            }
        };
        task.setOnSucceeded(e->{
            if(task.valueProperty().get()){
                nac.clear();
                nan.clear();
                DataSource.getInstance().addArtist(flag);
                artistTable.refresh();
               listArtists();
            }else{
                info1.setText("Sanatçı hali hazırda bulunmakta.");
                nac.clear();
                nan.clear();
                return;
            }
        });

        new Thread(task).start();

    }

    @FXML
    public void newAlbum(){
        if(aiName.getText().isEmpty() || aiAName.getText().isEmpty() || aiDate.getText().isEmpty() || aiTur.getValue().isEmpty()){
            aiInfo.setText("Lütfen sanatçı bilgilerini eksiksiz girin");
            aiName.clear();
            aiAName.clear();
            aiDate.clear();
            return;
        }

        Album flag = new Album();
        flag.setAd(aiName.getText());
        if(DataSource.getInstance().getArtistID(aiAName.getText())==-1){
            aiInfo.setText("Albüm sahini sanatçı listede yoktur!");
            aiName.clear();
            aiAName.clear();
            aiDate.clear();
            return;
        }else{
            flag.setSanatci_ID(DataSource.getInstance().getArtistID(aiAName.getText()));
        }
        flag.setTur(aiTur.getValue());
        flag.setTarih(Integer.parseInt(aiDate.getText()));

        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                return !DataSource.getInstance().findAlbum(flag);
            }
        };
        task.setOnSucceeded(e->{
            if(task.valueProperty().get()){
                aiName.clear();
                aiAName.clear();
                aiDate.clear();
                DataSource.getInstance().addAlbum(flag);
                albumTable.refresh();
            }else{
                aiInfo.setText("Album halihazırda bulunmakta");
                aiName.clear();
                aiAName.clear();
                aiDate.clear();
            }
        });

        new Thread(task).start();

    }

    @FXML
    public void deleteAlbum(){
        if(adName.getText().isEmpty()){
            adInfo.setText("Lütfen silinecek albümün ismini giriniz");
            adName.clear();
            return;
        }

        Album flag = new Album();
        flag.setAd(adName.getText());

        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                return DataSource.getInstance().findAlbum(flag);
            }
        };

        task.setOnSucceeded(e->{
            if(task.valueProperty().get()){
                DataSource.getInstance().deleteAlbum(adName.getText());
                adName.clear();
                albumTable.refresh();
                return;
            }else{
                adInfo.setText("Silmek istediğiniz album listede yoktur!");
                adName.clear();
                return;
            }
        });

        new Thread(task).start();
    }

    @FXML
    public void deleteArtist(){
        if(dan.getText().isEmpty()){
            info2.setText("Lütfen sanatçı adı giriniz!");
            dan.clear();
            return;
        }

        Artist flag = new Artist();
        flag.setAd(dan.getText());

        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                return DataSource.getInstance().findArtist(flag);
            }
        };

        task.setOnSucceeded(e->{
            if(task.valueProperty().get()){
                dan.clear();
                DataSource.getInstance().deleteArtist(flag);
                artistTable.refresh();
                listArtists();
            }else{
                info2.setText("Sanatçı bulunamadı!");
                dan.clear();
                return;
            }
        });

        new Thread(task).start();
    }
}


class GetAllArtistsTask extends Task{
    @Override
    public ObservableList<Artist> call(){
        return FXCollections.observableArrayList(DataSource.getInstance().queryArtists());
    }
}
