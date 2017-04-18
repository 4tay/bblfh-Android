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

/**
 * Created by johnkonderla on 3/21/17.
 */

public class LoginCreate extends AsyncTask<String, Object, String> {

    private Activity myActivity;
    private ProgressDialog statusDialog;
    private static final String MY_PREFS = "harbor-preferences";
    private static final String USER_KEY = "userKey";
    private static final String NAME_KEY = "nameKey";
    private static final String PASS_KEY = "passKey";
    private static final String SALT = "$2a$10$759xZSepASleX1bXBhoCDu";
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

            String paramPW = "fanPass";
            String unencryptedPass = params.optString(paramPW);
            publishProgress("Encrypting password...");
            String hashed = BCrypt.hashpw(unencryptedPass,SALT);
            hashed = hashed.replace("$","q");
            hashed = hashed.replace("/","r");
            hashed = hashed.replace(".","1");
            params.put(paramPW, hashed);

            SharedPreferences sharedPreferences = myActivity.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(USER_KEY,params.optString("fanName"));
            editor.putString(NAME_KEY, params.optString("fanEmail"));
            editor.putString(PASS_KEY, hashed);
            // Commit the edits!
            editor.apply();
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
            responseOutcome = "Login successful!!!";
            loggedIn = true;
            Intent intent = new Intent();
            intent.setClass(myActivity.getApplicationContext(), TabActivity.class);
            myActivity.startActivity(intent);
        } else {
            responseOutcome = "Login unsuccessful...";
            loggedIn = false;
        }
        Toast.makeText(myActivity.getApplicationContext(),responseOutcome,Toast.LENGTH_LONG).show();
        statusDialog.dismiss();

    }
}
