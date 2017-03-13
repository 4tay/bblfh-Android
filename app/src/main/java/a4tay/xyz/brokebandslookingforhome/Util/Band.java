package a4tay.xyz.brokebandslookingforhome.Util;

/**
 * Created by johnkonderla on 3/13/17.
 */

public class Band {
    private String name;
    private String genre;
    private int id;

    public Band(String name, String genre, int id) {
        this.name = name;
        this.genre = genre;
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
