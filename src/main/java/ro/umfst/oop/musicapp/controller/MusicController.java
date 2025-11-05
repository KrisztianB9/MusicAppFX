package ro.umfst.oop.musicapp.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import ro.umfst.oop.musicapp.model.Song;
import ro.umfst.oop.musicapp.model.Source;
import ro.umfst.oop.musicapp.service.MusicService;

import java.util.List;
// osztaly a giu inicializalasara
public class MusicController {

    @FXML
    private ChoiceBox<String> genreChoiceBox;

    @FXML
    private HBox songHBox;

    @FXML
    private ProgressIndicator loadingIndicator;

    private MusicService musicService;

    public MusicController() {

        this.musicService = new MusicService();
    }

    // betolti a choiceboxot, listener a kivalasztott mufaj eszrevetelere
    @FXML
    public void initialize() {
        genreChoiceBox.getItems().addAll("rnb", "rock", "hip-hop", "jazz", "80s");

        genreChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (newValue != null) {
                            loadGenreSongs(newValue);
                        }
                    }
                }
        );
    }


    // betolti az api-tol kapott zeneket, sorba allitja oket. Threaden indul, hogy ne fagyjon be a gui
    private void loadGenreSongs(String genre) {
        songHBox.getChildren().clear();
        songHBox.setPadding(new Insets(10));
        loadingIndicator.setVisible(true);

        Task<List<Song>> fetchTask = new Task<List<Song>>() {
            @Override
            protected List<Song> call() throws Exception {
                return musicService.getRankedPlaylists(List.of(genre));
            }
        };

        fetchTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                loadingIndicator.setVisible(false);
                List<Song> songs = fetchTask.getValue();

                List<String> backgroundColors = List.of(
                        "#fff6e0",
                        "#f9f9f9",
                        "#fff0e6"
                );

                if (songs.isEmpty()) {
                    songHBox.getChildren().add(new Label("No songs found for this genre."));
                } else {
                    for (int i = 0; i < songs.size(); i++) {
                        Song song = songs.get(i);
                        String color = (i < backgroundColors.size()) ? backgroundColors.get(i) : "#ffffff";

                        VBox songCard = createSongCard(song, color);
                        HBox.setMargin(songCard, new Insets(10));
                        songHBox.getChildren().add(songCard);
                    }
                }
            }
        });

        fetchTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                loadingIndicator.setVisible(false);
                songHBox.getChildren().add(new Label("Error: Could not load songs."));
                if (fetchTask.getException() != null) {
                    fetchTask.getException().printStackTrace();
                }
            }
        });

        new Thread(fetchTask).start();
    }
    // 3 kartya a HBox-ban a guiban, amiben a zenek tulajdonsagai vannak
    private VBox createSongCard(Song song, String backgroundColor) {
        VBox card = new VBox(5);
        card.setPrefWidth(180);
        card.setPadding(new Insets(10));

        final String baseStyle = "-fx-background-color: " + backgroundColor + "; " +
                "-fx-border-color: #e0e0e0; " +
                "-fx-border-radius: 8; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0.5, 0, 2);";

        final String hoverStyle = "-fx-background-color: " + backgroundColor + "; " +
                "-fx-border-color: #cccccc; " +
                "-fx-border-radius: 8; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 15, 0.6, 0, 4);";

        card.setStyle(baseStyle);

        card.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                card.setStyle(hoverStyle);
            }
        });
        card.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                card.setStyle(baseStyle);
            }
        });
        // zene infok
        Label title = new Label(song.getTitle());
        title.setFont(Font.font("System", FontWeight.BOLD, 14));
        title.setWrapText(true);

        Label artist = new Label(song.getArtist());
        artist.setFont(Font.font("System", 12));
        artist.setWrapText(true);

        Label plays = new Label(String.format("Plays: %,d", song.getStreamCount()));
        plays.setFont(Font.font("System", 10));
        plays.setStyle("-fx-text-fill: #666666;");

        // uj source egy labelben
        Source source1 = song.getSource();

        String sourceText = source1.getProvider() + " (" + source1.getCountry() + ")";
        Label sourceLabel = new Label(sourceText);

        sourceLabel.setFont(Font.font("System", 10));
        sourceLabel.setStyle("-fx-text-fill: #888888; -fx-font-style: italic;");

        card.getChildren().addAll(title, artist, plays, sourceLabel);
        return card;
    }
}

