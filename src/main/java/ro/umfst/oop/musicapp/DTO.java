package ro.umfst.oop.musicapp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;


    @JsonIgnoreProperties(ignoreUnknown = true)
    class Track {
        @JsonProperty("name")
        private String name;

        @JsonProperty("artist")
        private Artist artist;

        public String getName() { return name; }
        public Artist getArtist() { return artist; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    class Artist {
        @JsonProperty("name")
        private String name;

        public String getName() { return name; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    class Tracks {
        @JsonProperty("track")
        private List<Track> trackList;

        public List<Track> getTrackList() { return trackList; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    class ApiResponse {
        @JsonProperty("tracks")
        private Tracks tracks;

        public Tracks getTracks() { return tracks; }
    }


