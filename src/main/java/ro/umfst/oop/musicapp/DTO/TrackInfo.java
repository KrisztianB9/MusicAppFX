package ro.umfst.oop.musicapp.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackInfo {
    @JsonProperty("name")
    public String name;
    @JsonProperty("playcount")
    public long playcount;
}
