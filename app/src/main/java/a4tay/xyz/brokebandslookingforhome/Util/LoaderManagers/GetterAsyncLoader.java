package a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers;

/**
 * Created by johnkonderla on 4/1/17.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import a4tay.xyz.brokebandslookingforhome.Util.OnDataSendToActivity;
import a4tay.xyz.brokebandslookingforhome.Util.QueryUtils;


/**
 * Created by johnkonderla on 3/21/17.
 */

public class GetterAsyncLoader extends AsyncTask<String, Object, String> {

    private Activity myActivity;
    private static final String MY_PREFS = "harbor-preferences";
    private static final String USER_KEY = "userKey";
    private String submittedUN;
    private final static String LOG_TAG = GetterAsyncLoader.class.getSimpleName();
    private OnDataSendToActivity dataSendToActivity;
    private String fragName;

    public GetterAsyncLoader(Activity activity, String fragName) {

        myActivity = activity;
        this.fragName = fragName;
        dataSendToActivity = (OnDataSendToActivity)activity;
    }

    protected void onPreExecute() {

    }
    @Override
    protected String doInBackground(String... url) {

        String result = QueryUtils.getJSONFromUrl(url[0]);

        Log.d(LOG_TAG, url[0]);
        Log.d(LOG_TAG, result);
        return result;
    }
    @Override
    public void onProgressUpdate(Object... values) {

    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);

        Log.d(LOG_TAG,"onPostExecute");
        dataSendToActivity.sendData(new String[] {fragName,response});

    }
}
