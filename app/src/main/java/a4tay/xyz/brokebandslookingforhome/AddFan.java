package a4tay.xyz.brokebandslookingforhome;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers.PutManager;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by johnkonderla on 3/18/17.
 */

public class AddFan extends Fragment implements LoaderManager.LoaderCallbacks<String>  {

    private EditText userName;
    private EditText email;
    private EditText passOne;
    private EditText passTwo;
    private Button submitFan;
    private JSONObject userInfo;
    private String url = "http://dev.4tay.xyz:4567/addFan?";
    private static final String MY_PREFS = "harbor-preferences";
    private static final String NAME_KEY = "nameKey";
    private static final String PASS_KEY = "passKey";
    private String submittedEM;
    private String submittedPW1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.add_fan, container, false);

        userName = (EditText) rootView.findViewById(R.id.et_fan_name);
        email = (EditText) rootView.findViewById(R.id.et_fan_email);
        passOne = (EditText) rootView.findViewById(R.id.et_fan_pass1);
        passTwo = (EditText) rootView.findViewById(R.id.et_fan_pass2);
        submitFan = (Button) rootView.findViewById(R.id.bt_submit_fan);

        submitFan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String submittedUN = getText(userName);
                submittedEM = getText(email);
                submittedPW1 = getText(passOne);
                String submittedPW2 = getText(passTwo);
                String paramUN = "fanName";
                String paramEM = "fanEmail";
                String paramPW = "fanPass";
                if (!submittedUN.equals("") && !submittedEM.equals("")) {
                    if(submittedPW1.equals(submittedPW2) && !submittedPW1.equals("")) {
                        userInfo = new JSONObject();
                        try {
                            userInfo.put(paramUN, submittedUN);
                            userInfo.put(paramEM, submittedEM);
                            userInfo.put(paramPW, submittedPW1);
                        } catch (JSONException e) {
                            System.out.println(e.getMessage());
                        }
                    }


                    else {
                        Toast.makeText(getContext(),"password must match",Toast.LENGTH_LONG).show();
                        passOne.setError("Password is required");
                        passTwo.setError("Password must match");
                    }

                    startExport();
                } else if(submittedUN.equals("")) {
                    userName.setError("Name is required");
                } else {
                    email.setError("Email is required");
                }


            }
        });
        return rootView;
    }

    private String getText(EditText getFromIt) {

        if(getFromIt != null) {


            return getFromIt.getText().toString().trim();

        }
        return "";
    }
    private void startExport() {
        getLoaderManager().initLoader(3, null, this).forceLoad();
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        System.out.println(data);
        Toast.makeText(getContext(),data,Toast.LENGTH_LONG).show();
        if(data.equals("Success!")) {
            /**
             * TODO: Save email and password in shared prefs. Write simple method to encrypt data. Look into salt.
             */

            SharedPreferences sharedPreferences = getContext().getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(NAME_KEY, submittedEM);
            editor.putString(PASS_KEY,submittedPW1);

            // Commit the edits!
            editor.apply();
        } else {
            /**
             * TODO: Handle failure to save information. I'll have to give a reason why...
             */
        }

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new PutManager(getContext(),userInfo,url);
    }
}
