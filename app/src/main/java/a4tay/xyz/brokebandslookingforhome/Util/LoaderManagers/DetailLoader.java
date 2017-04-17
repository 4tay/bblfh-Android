package a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;

import a4tay.xyz.brokebandslookingforhome.Util.Band;
import a4tay.xyz.brokebandslookingforhome.Util.QueryUtils;

/**
 * Created by johnkonderla on 4/15/17.
 */

public class DetailLoader extends AsyncTaskLoader<ArrayList<?>> {

    String url;
    int type;
    private final static String LOG_TAG = BandLoader.class.getSimpleName();

    public DetailLoader(Context context, String url, int type) {
        super(context);
        this.url = url;
        this.type = type;

    }
    @Override
    public ArrayList<?> loadInBackground() {
        String response = QueryUtils.getJSONFromUrl(url);
        Log.d(LOG_TAG,"detail response: " + response);
        switch (type) {
            case 2:
                Log.d(LOG_TAG,"Loading bands......");
                ArrayList<Band> result = QueryUtils.getBandList(response);
                for(int i = 0; i < result.size(); i ++) {
                    Log.d(LOG_TAG, "loader printing... " + result.get(i).getName());
                }
                return result;
            case 3:
                Log.d(LOG_TAG,"Loading homes......");
                return QueryUtils.getFanHomes(response);
            default:
                Log.d(LOG_TAG,"Loading events......");
                return QueryUtils.makeEventFromJSON(response);
        }
    }
}