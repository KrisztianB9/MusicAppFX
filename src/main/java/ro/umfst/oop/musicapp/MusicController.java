package ro.umfst.oop.musicapp;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;

public class MusicController implements Initializable {

    @FXML
    private ComboBox<String> selectGenreBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectGenreBox.getItems().add("RNB");
        selectGenreBox.getItems().add("Rock");
        selectGenreBox.getItems().add("Hip-Hop");
        selectGenreBox.getItems().add("Jazz");
        selectGenreBox.getItems().add("80s");

    }

    @FXML
    private void gotSelected(ActionEvent event) {
        String genre = selectGenreBox.getSelectionModel().getSelectedItem();

    }
}
