package ro.umfst.oop.musicapp.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse {
    @JsonProperty("tracks")
    private Tracks tracks;

    public Tracks getTracks() {
        return tracks;
    }
}
