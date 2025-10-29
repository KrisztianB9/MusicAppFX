package ro.umfst.oop.musicapp;

public class MusicService {

    private static final String API_KEY = "1500f7f6f11eff90b85c5635100f0fb6";
    private static final String METHOD="tag.gettoptracks";
    private static final String BASE_URL = "http://ws.audioscrobbler.com/2.0/?method="+METHOD+"&api_key="+API_KEY+"&format=json";

}
