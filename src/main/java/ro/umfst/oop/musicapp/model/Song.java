package ro.umfst.oop.musicapp.model;

public class Song {
    private final String title;
    private final String artist;
    private final String genre;
    private final long streamCount;

    public Song(String title, String artist, String genre, long streamCount) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.streamCount = streamCount;
    }

    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getGenre() { return genre; }
    public long getStreamCount() { return streamCount; }
}
