package a4tay.xyz.brokebandslookingforhome.Util;

/**
 * Created by johnkonderla on 3/26/17.
 */

public class Home {


    private int homeID;
    private int fanID;
    private String homeName;
    private String homeAddress;
    private String homeAddressTwo;
    private int homeZip;
    private String homeCity;
    private String homeState;
    private String homeCountry;
    private float homeLat;
    private float homeLng;
    private String homePhoto;


    public Home(int homeID, int fanID, String homeName, String homeAddress, String homeAddressTwo,
                int homeZip, String homeCity, String homeState, String homeCountry, float homeLat, float homeLng,
                String homePhoto) {
        this.homeID = homeID;
        this.fanID = fanID;
        this.homeName = homeName;
        this.homeAddress = homeAddress;
        this.homeAddressTwo = homeAddressTwo;
        this.homeZip = homeZip;
        this.homeCity = homeCity;
        this.homeState = homeState;
        this.homeCountry = homeCountry;
        this.homeLat = homeLat;
        this.homeLng = homeLng;
        this.homePhoto = homePhoto;
    }
    public Home() {

    }

    public String getHomeName() {
        return homeName;
    }

    public String getHomeCity() {
        return homeCity;
    }
    public String getHomePhoto() {
        return homePhoto;
    }
}
