package ro.umfst.oop.musicapp.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.umfst.oop.musicapp.DTO.*;
import ro.umfst.oop.musicapp.model.Song;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicService {

    private static final String API_KEY = "1500f7f6f11eff90b85c5635100f0fb6";
    private static final String BASE_URL = "http://ws.audioscrobbler.com/2.0/";

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Song> getRankedPlaylists(List<String> genres) {
        List<Song> allSongs = new ArrayList<>();
        for (String genre : genres) {
            allSongs.addAll(fetchSongsByGenre(genre));
        }

        // We sort the songs here, as we only have one list
        allSongs.sort(Comparator.comparingLong(Song::getStreamCount).reversed());

        return allSongs;
    }

    /**
     * Step 1: Fetches the top 3 tracks for a given genre.
     */
    public List<Song> fetchSongsByGenre(String genre) {
        List<Song> songList = new ArrayList<>();

        try {
            // We use URLEncoder to safely handle genre names like "hip-hop"
            String encodedGenre = URLEncoder.encode(genre, StandardCharsets.UTF_8);
            String url = String.format("%s?method=tag.gettoptracks&tag=%s&api_key=%s&format=json&limit=3",
                    BASE_URL, encodedGenre, API_KEY);

            String jsonResponse = sendRequest(url);

            // Parse the JSON response using our DTOs
            ApiResponse apiResponse = objectMapper.readValue(jsonResponse, ApiResponse.class);

            if (apiResponse != null && apiResponse.getTracks() != null) {
                // Step 2: For each track found, get its detailed info (for the playcount)
                for (Track track : apiResponse.getTracks().getTrackList()) {
                    try {
                        long playCount = fetchPlayCount(track.getArtist().getName(), track.getName());

                        songList.add(new Song(
                                track.getName(),
                                track.getArtist().getName(),
                                genre,
                                playCount
                        ));
                    } catch (Exception e) {
                        System.err.println("Hiba a(z) '" + track.getName() + "' dal adatainak lekérésekor: " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Hiba történt a(z) '" + genre + "' műfaj lekérdezése közben: " + e.getMessage());
        }
        return songList;
    }

    /**
     * Step 2: Fetches the detailed info for a single track to get its playcount.
     */
    private long fetchPlayCount(String artist, String trackName) throws Exception {
        // Encode artist and track names for the URL
        String encodedArtist = URLEncoder.encode(artist, StandardCharsets.UTF_8);
        String encodedTrack = URLEncoder.encode(trackName, StandardCharsets.UTF_8);

        String url = String.format("%s?method=track.getInfo&api_key=%s&artist=%s&track=%s&format=json",
                BASE_URL, API_KEY, encodedArtist, encodedTrack);

        String jsonResponse = sendRequest(url);

        // Parse the response using our TrackInfo DTOs
        TrackInfoResponse infoResponse = objectMapper.readValue(jsonResponse, TrackInfoResponse.class);

        if (infoResponse != null && infoResponse.track != null) {
            return infoResponse.track.playcount;
        }
        return 0; // Return 0 if playcount is not found
    }

    /**
     * A helper method to send an HTTP request and return the body as a String.
     */
    private String sendRequest(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("HTTP Error " + response.statusCode() + " for URL: " + url);
        }
        return response.body();
    }
}
