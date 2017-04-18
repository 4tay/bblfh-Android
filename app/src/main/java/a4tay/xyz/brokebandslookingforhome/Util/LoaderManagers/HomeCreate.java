package a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import a4tay.xyz.brokebandslookingforhome.TabActivity;
import a4tay.xyz.brokebandslookingforhome.Util.BCrypt;
import a4tay.xyz.brokebandslookingforhome.Util.QueryUtils;

import static a4tay.xyz.brokebandslookingforhome.TabActivity.loggedIn;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by johnkonderla on 3/26/17.
 */

public class HomeCreate extends AsyncTask<String, Object, String> {

    private Activity myActivity;
    private ProgressDialog statusDialog;
    private static final String MY_PREFS = "harbor-preferences";
    private static final String NAME_KEY = "nameKey";
    private static final String PASS_KEY = "passKey";
    private String submittedEM;
    private String submittedPW1;
    private static final String LOG_TAG = HomeCreate.class.getSimpleName();

    public HomeCreate(Activity activity) {

        myActivity = activity;
    }

    protected void onPreExecute() {
        statusDialog = new ProgressDialog(myActivity);
        statusDialog.setMessage("Creating a home...");
        statusDialog.setIndeterminate(false);
        statusDialog.setCancelable(false);
        statusDialog.show();
    }
    @Override
    protected String doInBackground(String... args) {
        JSONObject params;
        try {
            params = new JSONObject(args[1]);

            SharedPreferences prefs = myActivity.getSharedPreferences(MY_PREFS, MODE_PRIVATE);

            submittedEM = prefs.getString(NAME_KEY, "");//defining an empty string as the default
            submittedPW1 = prefs.getString(PASS_KEY, ""); //defining an empty string as the default
            params.put("userName", submittedEM);
            params.put("password", submittedPW1);
            statusDialog.setMessage("got un and pw");


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
        if(response.equals("Success!")) {
            responseOutcome = "Made the home!!!";
        } else {
            responseOutcome = "I failed......";
        }
        Toast.makeText(myActivity.getApplicationContext(),responseOutcome,Toast.LENGTH_LONG).show();
        statusDialog.dismiss();

    }
}