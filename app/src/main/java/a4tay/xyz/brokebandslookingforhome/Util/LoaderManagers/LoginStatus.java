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

import a4tay.xyz.brokebandslookingforhome.Util.OnDataSendToActivity;
import a4tay.xyz.brokebandslookingforhome.Util.QueryUtils;

import static a4tay.xyz.brokebandslookingforhome.TabActivity.inBand;
import static a4tay.xyz.brokebandslookingforhome.TabActivity.loggedIn;


/**
 * Created by johnkonderla on 3/21/17.
 */

public class LoginStatus extends AsyncTask<String, Object, String> {

    private Activity myActivity;
    private String mCallingName;
    private ProgressDialog statusDialog;
    private static final String MY_PREFS = "harbor-preferences";
    private static final String USER_KEY = "userKey";
    private static final String BAND_ID = "bandID";
    private static final String BAND_NAME = "bandName";
    private OnDataSendToActivity dataSendToActivity;
    private String submittedUN;
    private final static String LOG_TAG = LoginStatus.class.getSimpleName();

    public LoginStatus(Activity activity) {

        myActivity = activity;
    }

    public LoginStatus(Activity activity, String callingName) {
        myActivity = activity;
        mCallingName = callingName;
        dataSendToActivity = (OnDataSendToActivity)activity;

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

        Log.d(LOG_TAG, url[0]);
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
            int bandID = -1;
            String bandName = "nothing";
            try {
                userOb = new JSONObject(response);
                submittedUN = userOb.optString("fanName");
                Log.d(LOG_TAG,"submittedUN: " + submittedUN);


                bandID = userOb.optInt("bandID");
                bandName = userOb.optString("bandName");
                Log.d(LOG_TAG, "bandName: " + bandName + " bandID: " + bandID);

            } catch (JSONException e) {
                submittedUN = "";
                Log.e(LOG_TAG,"JSON Error: ", e);
            }
            SharedPreferences sharedPreferences = myActivity.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(USER_KEY,submittedUN);
            if(bandID > 0 && !bandName.equals("nothing")) {
                editor.putInt(BAND_ID,bandID);
                editor.putString(BAND_NAME, bandName);
                inBand = bandID;
            }
            // Commit the edits!
            editor.apply();
            loggedIn = true;
            if(mCallingName != null) {
                dataSendToActivity.sendData(new String[] {mCallingName, String.valueOf(loggedIn)});
            }

        } else {
            responseOutcome = "Login unsuccessful...";
            loggedIn = false;
            if(mCallingName != null) {
                dataSendToActivity.sendData(new String[] {mCallingName, String.valueOf(loggedIn)});
            }
        }
        Toast.makeText(myActivity.getApplicationContext(),responseOutcome,Toast.LENGTH_LONG).show();
        statusDialog.dismiss();

    }
}
