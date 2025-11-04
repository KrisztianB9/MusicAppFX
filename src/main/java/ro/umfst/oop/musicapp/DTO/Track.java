package ro.umfst.oop.musicapp.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Track {
    @JsonProperty("name")
    private String name;
    @JsonProperty("artist")
    private Artist artist;

    public String getName() {
        return name;
    }

    public Artist getArtist() {
        return artist;
    }
}
