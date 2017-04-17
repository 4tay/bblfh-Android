package a4tay.xyz.brokebandslookingforhome.Util;

/**
 * Created by johnkonderla on 3/13/17.
 */

public class Band {
    private String name;
    private String genre;
    private int id;
    private String photo;
    private int memberCount;
    private String offer;
    private int homeConfirmed;

    public Band(String name, String genre, int id) {
        this.name = name;
        this.genre = genre;
        this.id = id;
    }
    public Band(String name, String genre, String photo, int id) {
        this.name = name;
        this.genre = genre;
        this.id = id;
        this.photo = photo;
    }

    public String getGenre() {
        return genre;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public int getId() {
        return id;
    }

    public int getHomeConfirmed() {
        return homeConfirmed;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public String getOffer() {
        return offer;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public void setHomeConfirmed(int homeConfirmed) {
        this.homeConfirmed = homeConfirmed;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }
}
