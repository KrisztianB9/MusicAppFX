package ro.umfst.oop.musicapp.controller;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import ro.umfst.oop.musicapp.service.MusicService;
import ro.umfst.oop.musicapp.model.Song;

import java.util.List;

public class MusicController {

    @FXML
    private ChoiceBox<String> genreChoiceBox;

    @FXML
    private HBox songHBox;

    @FXML
    private ProgressIndicator loadingIndicator;

    private final MusicService musicService = new MusicService();

    @FXML
    public void initialize() {
        // Add the genres to the ChoiceBox
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

    private void loadGenreSongs(String genre) {
        songHBox.getChildren().clear();
        loadingIndicator.setVisible(true);

        Task<List<Song>> fetchTask = new Task<>() {
            @Override
            protected List<Song> call() throws Exception {
                return musicService.fetchSongsByGenre(genre);
            }
        };

        fetchTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                loadingIndicator.setVisible(false);
                List<Song> songs = fetchTask.getValue();
                if (songs.isEmpty()) {
                    songHBox.getChildren().add(new Label("No songs found for this genre."));
                } else {
                    for (Song song : songs) {
                        VBox songCard = createSongCard(song);
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
                fetchTask.getException().printStackTrace();
            }
        });

        new Thread(fetchTask).start();
    }

    private VBox createSongCard(Song song) {
        VBox card = new VBox(5); // 5px spacing
        card.setPadding(new Insets(10));
        card.setStyle("-fx-border-color: #c0c0c0; -fx-border-width: 1; -fx-background-color: #ffffff; -fx-border-radius: 5;");
        card.setPrefWidth(170); // Fixed width for each card in the HBox
        card.setPrefHeight(100); // Fixed height

        Label title = new Label(song.getTitle());
        title.setStyle("-fx-font-weight: bold;");
        title.setWrapText(true); // Allow title to wrap if too long

        Label artist = new Label(song.getArtist());
        artist.setWrapText(true);

        Label plays = new Label(String.format("Plays: %,d", song.getStreamCount()));
        plays.setStyle("-fx-font-size: 0.9em; -fx-text-fill: #555;");

        card.getChildren().addAll(title, artist, plays);
        return card;
    }
}
