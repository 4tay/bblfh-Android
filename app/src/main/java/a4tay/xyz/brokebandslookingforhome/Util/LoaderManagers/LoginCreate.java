package a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import a4tay.xyz.brokebandslookingforhome.Util.QueryUtils;

import static a4tay.xyz.brokebandslookingforhome.EventList.loggedIn;

/**
 * Created by johnkonderla on 3/21/17.
 */

public class LoginCreate extends AsyncTask<String, Object, String> {

    private Activity myActivity;
    private ProgressDialog statusDialog;
    private static final String LOG_TAG = LoginCreate.class.getSimpleName();

    public LoginCreate(Activity activity) {

        myActivity = activity;
    }

    protected void onPreExecute() {
        statusDialog = new ProgressDialog(myActivity);
        statusDialog.setMessage("Logging in...");
        statusDialog.setIndeterminate(false);
        statusDialog.setCancelable(false);
        statusDialog.show();
    }
    @Override
    protected String doInBackground(String... args) {
        JSONObject params;
        try {
            params = new JSONObject(args[1]);
        } catch (JSONException e) {
            Log.e(LOG_TAG,"Error with JSON...",e);
            params = new JSONObject();
        }

        publishProgress("Contacting server...");
        String result = QueryUtils.newURL(params,args[0]);
        publishProgress("Dealing with the response...");
        return result;
    }
    @Override
    public void onProgressUpdate(Object... values) {
        statusDialog.setMessage(values[0].toString());

    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);

        String responseOutcome = "";
        if(response.length() > 10) {
            responseOutcome = "Login successful!!!";
            loggedIn = true;
        } else {
            responseOutcome = "Login unsuccessful...";
            loggedIn = false;
        }
        Toast.makeText(myActivity.getApplicationContext(),responseOutcome,Toast.LENGTH_LONG).show();
        statusDialog.dismiss();

    }
}
