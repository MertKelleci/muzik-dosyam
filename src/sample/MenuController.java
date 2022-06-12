package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DataBase.DataSource;
import sample.DataBase.User;


public class MenuController {
    @FXML
    private TextField kka;
    @FXML
    private PasswordField ks;
    @FXML
    private TextField gka;
    @FXML
    private PasswordField gs;
    @FXML
    private ComboBox<String> cbox;
    @FXML
    private Label info;


    @FXML
    public void tamamBas(ActionEvent event) throws Exception{
        if(kka.getText().isEmpty() && gka.getText().isEmpty()){
            info.setText("Lütfen Kullanıcı İsmi Girin");
            kka.clear();
            ks.clear();
            gka.clear();
            gs.clear();
            return;
        }

        if(ks.getText().isEmpty() && gs.getText().isEmpty()){
            info.setText("Lütfen Şifre Girin");
            kka.clear();
            ks.clear();
            gka.clear();
            gs.clear();
            return;
        }

        User user = new User();
        if(!kka.getText().isEmpty())
            user.setName(kka.getText());


        if(!ks.getText().isEmpty())
           user.setPassword(ks.getText());

        if(!gka.getText().isEmpty())
            user.setName(gka.getText());

        if(!gs.getText().isEmpty())
            user.setPassword(gs.getText());

        if(!gka.getText().isEmpty() && !gs.getText().isEmpty()){
            if(!DataSource.getInstance().findUser(user)){
                info.setText("Kullanıcı Bulunamadı, Şifrenizi Kontrol Ediniz!");
                kka.clear();
                ks.clear();
                gka.clear();
                gs.clear();
                return;
            }

        }else if(!kka.getText().isEmpty() && !ks.getText().isEmpty()){
            if(cbox.getValue().equals("Normal Üye"))
                user.setType(0);

            else if(cbox.getValue().equals("Premium Üye"))
                user.setType(1);

            else
                user.setType(2);


            if(DataSource.getInstance().findUser(user)){
                info.setText("Kullanıcı Mevcut, Lütfen Giriş Yapın");
                kka.clear();
                ks.clear();
                gka.clear();
                gs.clear();
                return;
            }else{
                DataSource.getInstance().addUser(user);
            }
        }


        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent mainParent = loader.load();
        Controller controller = loader.getController();
        controller.listArtists();
        Scene mainScene = new Scene(mainParent);

        Stage primaryStage = (Stage) (((Node)event.getSource()).getScene().getWindow());
        primaryStage.setScene(mainScene);
        primaryStage.show();

    }
}
