package ro.umfst.oop.musicapp.model;
//valasz strukturaja
public class Song {
    private final String title;
    private final String artist;
    private final String genre;
    private final long streamCount;
    private final Source source;

    public Song(String title, String artist, String genre, long streamCount, Source source) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.streamCount = streamCount;
        this.source = source;

    }

    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getGenre() { return genre; }
    public long getStreamCount() { return streamCount; }
    public Source getSource() {
        return source;
    }
}
