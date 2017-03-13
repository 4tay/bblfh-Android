package a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;

import a4tay.xyz.brokebandslookingforhome.Util.Band;
import a4tay.xyz.brokebandslookingforhome.Util.Event;
import a4tay.xyz.brokebandslookingforhome.Util.QueryUtils;

/**
 * Created by johnkonderla on 3/13/17.
 */

public class BandLoader extends AsyncTaskLoader<ArrayList<Band>> {

    String url;
    private final static String LOG_TAG = BandLoader.class.getSimpleName();

    public BandLoader(Context context, String url) {
        super(context);
        this.url = url;
    }
    @Override
    public ArrayList<Band> loadInBackground() {
        Log.d(LOG_TAG,"Loading event...");
        String response = QueryUtils.getJSONFromUrl(url);
        return QueryUtils.getBandList(response);
    }
}
