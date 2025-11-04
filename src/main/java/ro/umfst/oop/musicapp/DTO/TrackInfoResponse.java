package ro.umfst.oop.musicapp.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

//DTO
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackInfoResponse {
    @JsonProperty("track")
    public TrackInfo track;
}
