package a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;
import a4tay.xyz.brokebandslookingforhome.Util.QueryUtils;
import a4tay.xyz.brokebandslookingforhome.EventList;

import static a4tay.xyz.brokebandslookingforhome.EventList.loggedIn;

/**
 * Created by johnkonderla on 3/21/17.
 */

public class LoginStatus extends AsyncTask<String, Object, String> {

    private Activity myActivity;
    private ProgressDialog statusDialog;

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
            loggedIn = true;
        } else {
            responseOutcome = "Login unsuccessful...";
            loggedIn = false;
        }
        Toast.makeText(myActivity.getApplicationContext(),responseOutcome,Toast.LENGTH_LONG).show();
        statusDialog.dismiss();

    }
}
