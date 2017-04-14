package a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import a4tay.xyz.brokebandslookingforhome.LoginFrag;
import a4tay.xyz.brokebandslookingforhome.TabActivity;
import a4tay.xyz.brokebandslookingforhome.Util.BCrypt;
import a4tay.xyz.brokebandslookingforhome.Util.QueryUtils;

/**
 * Created by johnkonderla on 3/25/17.
 */

public class EncryptLoader extends AsyncTask<String, Object, String> {

    private Activity myActivity;
    private ProgressDialog statusDialog;
    private static final String SALT = "$2a$10$759xZSepASleX1bXBhoCDu";
    private static final String MY_PREFS = "harbor-preferences";
    private static final String NAME_KEY = "nameKey";
    private static final String PASS_KEY = "passKey";

    public EncryptLoader(Activity activity) {

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

        String submittedEM = url[0];
        publishProgress("Got email....");
        String submittedPW1 = url[1];
        publishProgress("Got password....");

        publishProgress("Encrypting password...");
        String hashed = BCrypt.hashpw(submittedPW1,SALT);

        hashed = hashed.replace("/","r");
        hashed = hashed.replace(".","1");
        hashed = hashed.replace("$","q");
        publishProgress("Got finished password encryption!!");

        String loginUrl = "http://192.168.1.66:8080/Harbor/api/fan/login/";

        publishProgress("About to start writing preferences");
        SharedPreferences sharedPreferences = myActivity.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NAME_KEY, submittedEM);
        publishProgress("Wrote email to shared prefs");
        editor.putString(PASS_KEY, hashed);
        publishProgress("Wrote password to shared prefs");

        // Commit the edits!
        editor.apply();
        publishProgress("committed to shared prefs");
        return loginUrl + submittedEM + "/" + hashed;
    }

    @Override
    public void onProgressUpdate(Object... values) {
        statusDialog.setMessage(values[0].toString());

    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);

        LoginFrag.queryForLogin(response);
        statusDialog.dismiss();
    }
}
