package ro.umfst.oop.musicapp.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Tracks {
    @JsonProperty("track")
    private List<Track> trackList;
    public List<Track> getTrackList() {
        return trackList;
    }
}
