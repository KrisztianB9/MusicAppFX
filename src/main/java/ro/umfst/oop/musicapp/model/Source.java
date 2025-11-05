package ro.umfst.oop.musicapp.model;

public class Source {
    private final String provider;
    private final String country;

    public Source(String provider, String country) {
        this.provider = provider;
        this.country = country;
    }

    public String getProvider() {
        return provider;
    }
    public String getCountry() {
        return country;
    }
}
