package ro.umfst.oop.musicapp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

class ApiResponse {
    @JsonProperty("tracks")
    public Tracks tracks;
}

class Tracks {
    @JsonProperty("track")
    public List<Track> trackList;
}

class Track {
    @JsonProperty("name")
    public String name;

    @JsonProperty("playcount")
    public long playcount;

    @JsonProperty("artist")
    public Artist artist;
}

class Artist {
    @JsonProperty("name")
    public String name;
}
