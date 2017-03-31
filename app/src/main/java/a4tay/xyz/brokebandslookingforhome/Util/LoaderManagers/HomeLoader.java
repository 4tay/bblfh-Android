package a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import java.util.ArrayList;
import a4tay.xyz.brokebandslookingforhome.Util.Home;
import a4tay.xyz.brokebandslookingforhome.Util.QueryUtils;

/**
 * Created by johnkonderla on 3/26/17.
 */

public class HomeLoader extends AsyncTaskLoader<ArrayList<Home>> {
    private String url;
    private static final String LOG_TAG = HomeLoader.class.getSimpleName();

    public HomeLoader(Context context, String url) {
        super(context);
        this.url = url;
    }
    @Override
    public ArrayList<Home> loadInBackground() {
        Log.d(LOG_TAG,"Loading event...");
        String response = QueryUtils.getJSONFromUrl(url);
        return QueryUtils.getFanHomes(response);
    }

}