package sample.DataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataSource {

    User user;
    private Connection conn;
    public static final String DB_NAME = "music.db";
    public static final String DB_PATH = "jdbc:sqlite:C:\\Users\\Mert\\Desktop\\Müzik Dosyam Projesi\\MuzikDosyam\\"+DB_NAME;
    public static final String FIND_USER = "SELECT * FROM User WHERE Name = (?) AND Password = (?)";
    public static final String ADD_USER = "INSERT INTO User (Name,Password,Status) VALUES((?), (?), (?))";
    public static final String QUERY_ARTISTS = "SELECT * FROM Sanatci ORDER BY Ad COLLATE NOCASE";
    public static final String QUERY_ALBUMS_BY_ARTIST_ID = "SELECT * FROM Album WHERE Album.Sanatci_ID = (?) ORDER BY Album.Ad COLLATE NOCASE";
    public static final String QUERY_SONGS_BY_ALBUM_ID = "SELECT * FROM Sarki WHERE Sarki.Album_ID = (?) ORDER BY Sarki.Ad COLLATE NOCASE";
    public static final String UPDATE_VIEWED_COUNT = "UPDATE Sarki SET Dinlenme = Dinlenme+1 WHERE Sarki.ID = (?)";
    public static final String FIND_ARTIST = "SELECT * FROM Sanatci WHERE Ad = (?)";
    public static final String ADD_ARTIST = "INSERT INTO Sanatci (Ad, Ulke) VALUES ((?),(?))";
    public static final String DELETE_ARTIST = "DELETE FROM Sanatci WHERE Ad = (?)";
    public static final String ADD_ALBUM = "INSERT INTO Album(Ad, Sanatci_ID, Sarki_Adet, Tarih, Tur) VALUES ((?), (?), (?), (?), (?))";
    public static final String DELETE_ALBUM = "DELETE FROM Album WHERE Ad = (?)";
    public static final String FIND_ALBUM = "SELECT * FROM Album WHERE Ad = (?)";
    public static final String FIND_SONG = "SELECT * FROM Sarki WHERE Ad = (?)";
    public static final String ADD_SONG = "INSERT INTO Sarki(Ad, Tarih, Album_ID, Tur, Sure) VALUES((?), (?), (?), (?), (?))";
    public static final String DELETE_SONG = "DELETE FROM Sarki WHERE Ad = (?)";
    public static final String ALBUM_SONG_COUNT_INCREASE = "UPDATE Album SET Sarki_Adet = Sarki_Adet+1 WHERE ID = (?)";
    public static final String ALBUM_SONG_COUNT_DECREASE = "UPDATE Album SET Sarki_Adet = Sarki_Adet-1 WHERE ID = (?)";

    private PreparedStatement queryAlbumsByArtistID;
    private PreparedStatement findUser;
    private PreparedStatement addUser;
    private PreparedStatement queryArtists;
    private PreparedStatement querySongsByAlbumID;
    private PreparedStatement updateViewedCount;
    private PreparedStatement findArtist;
    private PreparedStatement addArtist;
    private PreparedStatement deleteArtist;
    private PreparedStatement addAlbum;
    private PreparedStatement deleteAlbum;
    private PreparedStatement findAlbum;
    private PreparedStatement findSong;
    private PreparedStatement addSong;
    private PreparedStatement deleteSong;
    private PreparedStatement albumSongCountIncrease;
    private PreparedStatement albumSongCountDecrease;


    private static DataSource instance = new DataSource();
    public static DataSource getInstance(){return instance;}
    private DataSource(){}

    public boolean open(){
        try {
            conn = DriverManager.getConnection(DB_PATH);
            findUser = conn.prepareStatement(FIND_USER);
            addUser = conn.prepareStatement(ADD_USER);
            queryAlbumsByArtistID = conn.prepareStatement(QUERY_ALBUMS_BY_ARTIST_ID);
            queryArtists = conn.prepareStatement(QUERY_ARTISTS);
            querySongsByAlbumID = conn.prepareStatement(QUERY_SONGS_BY_ALBUM_ID);
            updateViewedCount = conn.prepareStatement(UPDATE_VIEWED_COUNT);
            addArtist = conn.prepareStatement(ADD_ARTIST);
            findArtist = conn.prepareStatement(FIND_ARTIST);
            deleteArtist = conn.prepareStatement(DELETE_ARTIST);
            addAlbum = conn.prepareStatement(ADD_ALBUM);
            deleteAlbum = conn.prepareStatement(DELETE_ALBUM);
            findAlbum = conn.prepareStatement(FIND_ALBUM);
            findSong = conn.prepareStatement(FIND_SONG);
            addSong = conn.prepareStatement(ADD_SONG);
            deleteSong = conn.prepareStatement(DELETE_SONG);
            albumSongCountIncrease = conn.prepareStatement(ALBUM_SONG_COUNT_INCREASE);
            albumSongCountIncrease = conn.prepareStatement(ALBUM_SONG_COUNT_DECREASE);
            return true;
        }catch (SQLException e){
            System.out.println("Open'da sıkıntı: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean close(){
        try {
            if(findUser!=null)
                findUser.close();

            if(addUser!=null)
                addUser.close();

            if(queryArtists!=null)
                queryArtists.close();
            
            if(findSong!=null)
                findSong.close();

            if(albumSongCountDecrease!=null)
                albumSongCountDecrease.close();
            
            if(addSong!=null)
                addSong.close();

            if(albumSongCountIncrease!=null)
                albumSongCountIncrease.close();
            
            if(deleteSong!=null)
                deleteSong.close();

            if(findAlbum!=null)
                findAlbum.close();

            if(queryAlbumsByArtistID!=null)
                queryAlbumsByArtistID.close();

            if(findArtist!=null)
                findArtist.close();

            if(addArtist!=null)
                addArtist.close();

            if(deleteArtist!=null)
                deleteArtist.close();

            if(updateViewedCount!=null)
                updateViewedCount.close();

            if(addAlbum!=null)
                addAlbum.close();

            if(deleteAlbum!=null)
                deleteAlbum.close();

            if(querySongsByAlbumID!=null)
                querySongsByAlbumID.close();

            if(conn!=null)
                conn.close();

            return true;
        }catch (SQLException e){
            System.out.println("Close'da sıkıntı: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public User getUser(){
        return user;
    }

    public int getArtistID(String name){
        try {
            findArtist.setString(1, name);
            ResultSet results = findArtist.executeQuery();

            if(results.next())
                return results.getInt(1);
            else
                return -1;
        }catch (SQLException e){
            System.out.println("getArtistID'de sorun: "+e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    public void deleteSong(String ad){
        try {
            conn.setAutoCommit(false);
            deleteSong.setString(1,ad);

            int affectedRows = deleteSong.executeUpdate();

            if(affectedRows == 1)
                conn.commit();
            else
                throw new SQLException("deleteSong'da sorun");
        }catch (Exception e){
            try {
                System.out.println("Rollback uygulanıyor: "+e.getMessage());
                e.printStackTrace();
                conn.rollback();
            }catch (SQLException e2){
                System.out.println("Rollback uygulanamadı: "+e2.getMessage());
                e.printStackTrace();
            }
        }finally {
            try{
                conn.setAutoCommit(true);
            }catch (SQLException e){
                System.out.println("Autocommit açılamadı: "+e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    public int getAlbumID(String name){
        try {
            findAlbum.setString(1,name);
            ResultSet results = findAlbum.executeQuery();
            
            if(results.next())
                return results.getInt(1);
            else
                return -1;
        }catch (SQLException e){
            System.out.println("getAlbumID'de sorun var: "+e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    public boolean findUser(User user){
        try {
            findUser.setString(1,user.getName());
            findUser.setString(2, user.getPassword());

            ResultSet results = findUser.executeQuery();
            if(results.next()){
                this.user = new User();
                this.user.setName(results.getString(2));
                this.user.setPassword(results.getString(3));
                this.user.setType(results.getInt(4));
                return true;
            }

            else{
                return false;
            }

        }catch (SQLException e){
            System.out.println("findUser'da sıkıntı var: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean findSong(Song song){
        try {
            findSong.setString(1,song.getAd());
            ResultSet results = findSong.executeQuery();

            if(results.next())
                return true;
            else
                return false;
        }catch (SQLException e){
            System.out.println("findSong'da sorun: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Song getSong(String Name){
        try {
            findSong.setString(1,Name);
            ResultSet results = findSong.executeQuery();


            if(results.next()){
                Song song = new Song();
                song.setID(results.getInt(1));
                song.setAd(results.getString(2));
                song.setTarih(results.getInt(3));
                song.setAlbum_ID(results.getInt(4));
                song.setTur(results.getString(5));
                song.setDinlenme(results.getInt(6));
                return song;
            }
            else
                return null;
        }catch (SQLException e){
            System.out.println("findSong'da sorun: "+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean albumSongCountIncrease(int ID){
        try{
            albumSongCountIncrease.setInt(1,ID);
            int affectedRows = albumSongCountIncrease.executeUpdate();
            if(affectedRows==1)
                return true;
            else
                throw new SQLException("albumSongCountIncrease'de sorun var");
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean albumSongCountDecrease(String name){
        Song song = getSong(name);
        System.out.println(song.getAd());
        try{
            albumSongCountDecrease.setInt(1,song.getAlbum_ID());
            int affectedRows = albumSongCountDecrease.executeUpdate();
            if(affectedRows==1)
                return true;
            else
                throw new SQLException("albumSongCountDecrease'de sorun var");
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public void addSong(Song song){
        try {
            conn.setAutoCommit(false);
            addSong.setString(1, song.getAd());
            addSong.setInt(2,song.getTarih());
            addSong.setInt(3,song.getAlbum_ID());
            addSong.setString(4,song.getTur());
            addSong.setString(5,song.getSure());

            int affectedRows = addSong.executeUpdate();

            if(affectedRows==1 && albumSongCountIncrease(song.getAlbum_ID())){
                conn.commit();
            }
            else
                throw new SQLException("addSong'da sorun var");
        }catch (Exception e){
            try {
                System.out.println("Rollback uygulanıyor: "+e.getMessage());
                e.printStackTrace();
                conn.rollback();
            }catch (SQLException e2){
                System.out.println("Rollback uygulanamadı: "+e2.getMessage());
                e2.printStackTrace();
            }
        }finally {
            try {
                conn.setAutoCommit(true);
            }catch (SQLException e){
                System.out.println("Autocommit açılamadı: "+e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public boolean findArtist(Artist artist){
        try {
            findArtist.setString(1, artist.getAd());
            ResultSet results = findArtist.executeQuery();

            if(results.next())
                return true;
            else
                return false;
        }catch (SQLException e){
            System.out.println("findArtist'de sorun: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean findAlbum(Album album){
        try {
            findAlbum.setString(1,album.getAd());
            ResultSet results = findAlbum.executeQuery();

            if(results.next())
                return true;
            else
                return false;
        }catch (SQLException e){
            System.out.println("findAlbum'da sorun: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void addArtist(Artist artist){
        try {
            conn.setAutoCommit(false);
            addArtist.setString(1,artist.getAd());
            addArtist.setString(2, artist.getUlke());

            int affectedRows = addArtist.executeUpdate();

            if(affectedRows == 1){
                conn.commit();
            }else{
                throw new SQLException("addArtist başarısız");
            }
        }catch (Exception e){
            System.out.println("addArtist'de sıkıntı: "+e.getMessage());
            e.printStackTrace();

            try {
                System.out.println("Rollback uygulanıyor");
                conn.rollback();
            }catch (SQLException e2){
                System.out.println("Rollback başarısız: "+e2.getMessage());
                e2.printStackTrace();
            }
        }finally {
            try {
                conn.setAutoCommit(true);
            }catch (SQLException e){
                System.out.println("Autocommit açılamadı: "+e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void deleteArtist(Artist artist){
        try{
            conn.setAutoCommit(false);
            deleteArtist.setString(1,artist.getAd());

            int affectedRows = deleteArtist.executeUpdate();

            if(affectedRows == 1){
                conn.commit();
            }else{
                throw new SQLException("deleteArtist başarısız.");
            }
        }catch (Exception e){
            System.out.println("Rollback uygulanıyor");
            try {
                conn.rollback();
            }catch (SQLException e2){
                System.out.println("Rollback başarısız: "+e2.getMessage());
                e2.printStackTrace();
            }
        }finally {
            try{
                conn.setAutoCommit(true);
            }catch (SQLException e){
                System.out.println("Autocommit açılamadı: "+e.getMessage());
                e.printStackTrace();
            }
        }

    }

    public void addUser(User user){

        if(findUser(user)){
            System.out.println("Kullanıcı mevcut");
            return;
        }

        try {
            conn.setAutoCommit(false);
            addUser.setString(1,user.getName());
            addUser.setString(2,user.getPassword());
            addUser.setInt(3,user.getType());

            int affectedRows = addUser.executeUpdate();
            if(affectedRows == 1){
                conn.commit();
                this.user = user;
            }
            else
                throw new SQLException("addUser başarısız");

        }catch (Exception e){
            System.out.println("addUser'da sıkıntı var: "+e.getMessage());
            e.printStackTrace();

            try{
                System.out.println("Rollback uygulanıyor");
                conn.rollback();
            }catch (SQLException e2){
                System.out.println("Rollback uygulanamadı: "+e2.getMessage());
                e2.printStackTrace();
            }
        }finally {
            try {
                conn.setAutoCommit(true);
            }catch (SQLException e){
                System.out.println("addUser autocommit açılamadı: "+e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public boolean updateViewedCount(int ID){
        try {
            updateViewedCount.setInt(1,ID);
            int affectedRows = updateViewedCount.executeUpdate();

            return affectedRows == 1;
        }catch (SQLException e){
            System.out.println("updateViewedCount'da sorun: "+e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

    public List<Artist> queryArtists(){
        try{
            ResultSet results = queryArtists.executeQuery();
            List<Artist> artistList = new ArrayList<>();
            while (results.next()){
                Artist artist = new Artist();
                artist.setID(results.getInt(1));
                artist.setAd(results.getString(2));
                artist.setUlke(results.getString(3));

                artistList.add(artist);
            }

            return artistList;

        }catch (SQLException e){
            System.out.println("queryArtists'de sorun: "+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Album> queryAlbumsByArtistID(int ID){
        try{
            queryAlbumsByArtistID.setInt(1,ID);
            ResultSet results = queryAlbumsByArtistID.executeQuery();
            List<Album> albumList = new ArrayList<>();
            while (results.next()){
                Album album = new Album();
                album.setID(results.getInt(1));
                album.setAd(results.getString(2));
                album.setSanatci_ID(results.getInt(3));
                album.setSarki_Adet(results.getInt(4));
                album.setTarih(results.getInt(5));
                album.setTur(results.getString(6));
                albumList.add(album);
            }

            return albumList;
        }catch(SQLException e){
            System.out.println("queryAlbumsByArtistID'de sorun: "+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Song> querySongsByAlbumID(int ID){
        try{
            querySongsByAlbumID.setInt(1,ID);
            ResultSet results = querySongsByAlbumID.executeQuery();
            List<Song> songList = new ArrayList<>();
            while (results.next()){
                Song song = new Song();
                song.setID(results.getInt(1));
                song.setAd(results.getString(2));
                song.setTarih(results.getInt(3));
                song.setAlbum_ID(results.getInt(4));
                song.setTur(results.getString(5));
                song.setSure(results.getString(6));
                song.setDinlenme(results.getInt(7));
                songList.add(song);
            }

            return songList;
        }catch(SQLException e){
            System.out.println("querySongsByAlbumID'de sorun: "+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void addAlbum(Album album){
        try {
            conn.setAutoCommit(false);
            addAlbum.setString(1,album.getAd());
            addAlbum.setInt(2,album.getSanatci_ID());
            addAlbum.setInt(3,album.getSarki_Adet());
            addAlbum.setInt(4,album.getTarih());
            addAlbum.setString(5,album.getTur());

            int affectedRows = addAlbum.executeUpdate();
            if(affectedRows == 1){
                conn.commit();
            }else{
                throw new SQLException("addAlbum'da sorun var");
            }
        }catch (Exception e){
            System.out.println("Rollback uygulanıyor");
            try {
                conn.rollback();
            }catch (SQLException e2){
                System.out.println("Rollback başarısız: "+e2.getMessage());
                e2.printStackTrace();
            }
        }finally {
            try {
                conn.setAutoCommit(true);
            }catch (SQLException e){
                System.out.println("Autocommit açılamadı: "+e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void deleteAlbum(String ad){
        try {
            conn.setAutoCommit(false);
            deleteAlbum.setString(1,ad);

            int affectedRows = deleteAlbum.executeUpdate();

            if(affectedRows == 1)
                conn.commit();
            else
                throw new SQLException("deleteAlbum'da sorun");
        }catch (Exception e){
            try {
                System.out.println("Rollback uygulanıyor");
                conn.rollback();
            }catch (SQLException e2){
                System.out.println("Rollback uygulanamadı: "+e2.getMessage());
                e.printStackTrace();
            }
        }finally {
            try{
                conn.setAutoCommit(true);
            }catch (SQLException e){
                System.out.println("Autocommit açılamadı: "+e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
