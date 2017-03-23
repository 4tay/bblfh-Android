package a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import a4tay.xyz.brokebandslookingforhome.Util.QueryUtils;
import a4tay.xyz.brokebandslookingforhome.EventList;

import static a4tay.xyz.brokebandslookingforhome.EventList.loggedIn;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by johnkonderla on 3/21/17.
 */

public class LoginStatus extends AsyncTask<String, Object, String> {

    private Activity myActivity;
    private ProgressDialog statusDialog;
    private static final String MY_PREFS = "harbor-preferences";
    private static final String USER_KEY = "userKey";
    private String submittedUN;
    private final static String LOG_TAG = LoginStatus.class.getSimpleName();

    public LoginStatus(Activity activity) {

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
    protected String doInBackground(String... url) {

        publishProgress("Contacting server...");
        String result = QueryUtils.getJSONFromUrl(url[0]);
        publishProgress("Dealing with the response...");
        return QueryUtils.getLogin(result);
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
            Log.d(LOG_TAG,"response: " + response);
            JSONObject userOb;
            try {
                userOb = new JSONObject(response);
                submittedUN = userOb.getString("fanName");

                Log.d(LOG_TAG,"submittedUN: " + submittedUN);
            } catch (JSONException e) {
                submittedUN = "";
                Log.e(LOG_TAG,"JSON Error: ", e);
            }
            SharedPreferences sharedPreferences = myActivity.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(USER_KEY,submittedUN);
            // Commit the edits!
            editor.apply();
            loggedIn = true;
        } else {
            responseOutcome = "Login unsuccessful...";
            loggedIn = false;
        }
        Toast.makeText(myActivity.getApplicationContext(),responseOutcome,Toast.LENGTH_LONG).show();
        statusDialog.dismiss();

    }
}
