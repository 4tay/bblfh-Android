package a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;

import a4tay.xyz.brokebandslookingforhome.Util.Event;
import a4tay.xyz.brokebandslookingforhome.Util.QueryUtils;

/**
 * Created by johnkonderla on 3/12/17.
 */

public class EventLoader extends AsyncTaskLoader<ArrayList<Event>> {
    private String url;
    private int bandID;
    private static final String LOG_TAG = EventLoader.class.getSimpleName();

    public EventLoader(Context context, String url) {
        super(context);
        this.url = url;
    }
    @Override
    public ArrayList<Event> loadInBackground() {
        Log.d(LOG_TAG,"Loading event...");
        String response = QueryUtils.getJSONFromUrl(url);
        return QueryUtils.makeEventFromJSON(response);
    }

}
