package a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers;

import java.util.ArrayList;

import a4tay.xyz.brokebandslookingforhome.Util.Band;
import a4tay.xyz.brokebandslookingforhome.Util.Event;

/**
 * Created by johnkonderla on 4/4/17.
 */
public class Bands {

    private String listName;
    private ArrayList<Band> bands;

    public Bands(String listName,ArrayList<Band> bands) {
        this.listName = listName;
        this.bands = bands;
    }
    public Bands() {

    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public void setBands(ArrayList<Band> bands) {
        this.bands = bands;
    }

    public ArrayList<Band> getBands() {
        return bands;
    }

    public String getListName() {
        return listName;
    }
}