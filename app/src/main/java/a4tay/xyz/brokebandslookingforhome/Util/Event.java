package a4tay.xyz.brokebandslookingforhome.Util;

/**
 * Created by johnkonderla on 3/12/17.
 */

public class Event {
    private String eventTitle;
    private String eventDate;
    private String[] eventLineup;

    public Event(String title, String date, String[] lineup) {
        eventTitle = title;
        eventDate = date;
        eventLineup = lineup;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public String[] getEventLineup() {
        return eventLineup;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public void setEventLineup(String[] eventLineup) {
        this.eventLineup = eventLineup;
    }
}
