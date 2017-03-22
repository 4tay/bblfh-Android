package a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONObject;

import a4tay.xyz.brokebandslookingforhome.Util.QueryUtils;

/**
 * Created by johnkonderla on 3/18/17.
 */

public class PutManager extends AsyncTaskLoader<String> {

    JSONObject params;
    String url;
    private final static String LOG_TAG = PutManager.class.getSimpleName();

    public PutManager(Context context, JSONObject params,String url) {
        super(context);
        this.params = params;
        this.url = url;
    }
    @Override
    public String loadInBackground() {
        Log.d(LOG_TAG,"Writing to the db......");
        return QueryUtils.newURL(params,url);
    }
}
