package a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import a4tay.xyz.brokebandslookingforhome.LoginFrag;
import a4tay.xyz.brokebandslookingforhome.Util.BCrypt;
import a4tay.xyz.brokebandslookingforhome.Util.QueryUtils;

import static a4tay.xyz.brokebandslookingforhome.TabActivity.baseURL;

/**
 * Created by johnkonderla on 4/16/17.
 */

public class PutterAsyncLoader extends AsyncTask<String, Object, String> {

    private Activity myActivity;
    private ProgressDialog statusDialog;

    public PutterAsyncLoader(Activity activity) {

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
            params = new JSONObject(args[0]);
            try {
                args[1] = args[1] + "?" + QueryUtils.getPostDataString(params);

                Log.d("PutterAsyncLoader", args[1]);
            } catch (Exception e) {
                Log.e("PutterAsyncLoader", "errror....", e);
            }

        } catch (JSONException e) {
            Log.e("PutterAsyncLoader", "error with json...", e);
        }

        String result = QueryUtils.postURL(args[1]);

        Log.d("PutterAsyncLoader", result);
        return result;
    }


    @Override
    public void onProgressUpdate(Object... values) {
        statusDialog.setMessage(values[0].toString());

    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);

        statusDialog.dismiss();
    }
}