package a4tay.xyz.brokebandslookingforhome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import static a4tay.xyz.brokebandslookingforhome.EventList.loggedIn;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by johnkonderla on 3/22/17.
 */

public class UserFrag extends Fragment {
    private static final String MY_PREFS = "harbor-preferences";
    private static final String USER_KEY = "userKey";
    private static final String NAME_KEY = "nameKey";
    private static final String PASS_KEY = "passKey";
    private TextView userName;
    private TextView userEmail;
    private Button userLogoff;
    private String submittedUN;
    private String submittedEM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.user_info, container, false);

        userName = (TextView) rootView.findViewById(R.id.tv_user_name);
        userEmail = (TextView) rootView.findViewById(R.id.tv_user_email);

        SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS, MODE_PRIVATE);

        submittedUN = prefs.getString(USER_KEY, "");//defining an empty string as the default
        submittedEM = prefs.getString(NAME_KEY, "");//defining an empty string as the default

        if(!submittedEM.equals("")) {
            userName.setText(submittedUN);
            userEmail.setText(submittedEM);
        }

        userLogoff = (Button) rootView.findViewById(R.id.bt_logout_user);

        userLogoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(USER_KEY, "");
                editor.putString(NAME_KEY, "");
                editor.putString(PASS_KEY, "");

                editor.apply();

                loggedIn = false;
                Intent intent = new Intent();
                intent.setClass(getContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });




        return rootView;
    }
}
