package a4tay.xyz.brokebandslookingforhome.Util;

import java.util.ArrayList;

/**
 * Created by johnkonderla on 4/2/17.
 */

public class Events {

    private String locationName;
    private ArrayList<Event> events;

    public Events(String locationName,ArrayList<Event> events) {
        this.locationName = locationName;
        this.events = events;
    }
    public Events() {

    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public String getLocationName() {
        return locationName;
    }
}
