package a4tay.xyz.brokebandslookingforhome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers.HomeCreate;
import a4tay.xyz.brokebandslookingforhome.Util.QueryUtils;

public class AddHome extends Fragment {

    private static final String LOG_TAG = AddHome.class.getSimpleName();
    private static final String url = "http://dev.4tay.xyz:4567/addHome?";
    private EditText homeName;
    private EditText homeAddress;
    private EditText homeAddressTwo;
    private EditText homeZip;
    private EditText homeCity;
    private Spinner homeState;
    private Spinner homeCountry;
    private EditText homePhoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_add_home, container, false);
        //Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        homeName = (EditText) rootView.findViewById(R.id.et_home_name);
        homeAddress = (EditText) rootView.findViewById(R.id.et_home_address);
        homeAddressTwo = (EditText) rootView.findViewById(R.id.et_home_address_two);
        homeZip = (EditText) rootView.findViewById(R.id.et_home_zip);
        homeCity = (EditText) rootView.findViewById(R.id.et_home_city);
        homeState = (Spinner) rootView.findViewById(R.id.sp_home_state);
        homeCountry = (Spinner) rootView.findViewById(R.id.sp_home_country);
        homePhoto = (EditText) rootView.findViewById(R.id.et_home_photo);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameSubmit = QueryUtils.getText(homeName);
                String addressSubmit = QueryUtils.getText(homeAddress);
                String addressTwoSubmit = QueryUtils.getText(homeAddressTwo);
                int zipSubmit = Integer.parseInt(QueryUtils.getText(homeZip));
                String citySubmit = QueryUtils.getText(homeCity);
                String stateSubmit = "NC";
                String countrySubmit = "US";
                String photoSubmit = QueryUtils.getText(homePhoto);
                float latSubmit = Float.parseFloat("1.000");
                float lngSubmit = Float.parseFloat("1.000");

                if(!nameSubmit.equals("") && !addressSubmit.equals("") && zipSubmit > 0
                        && !citySubmit.equals("")) {
                    String nameKey = "homeName";
                    String addressKey = "homeAddress";
                    String addressTwoKey = "homeAddressTwo";
                    String zipKey = "homeZip";
                    String cityKey = "homeCity";
                    String stateKey = "homeState";
                    String countryKey = "homeCountry";
                    String photoKey = "homePhoto";
                    String latKey = "homeLat";
                    String lngKey = "homeLng";

                    try {
                        JSONObject fullOb = new JSONObject();
                        fullOb.put(nameKey, nameSubmit);
                        fullOb.put(addressKey, addressSubmit);
                        fullOb.put(addressTwoKey, addressTwoSubmit);
                        fullOb.put(zipKey, zipSubmit);
                        fullOb.put(cityKey, citySubmit);
                        fullOb.put(stateKey, stateSubmit);
                        fullOb.put(countryKey, countrySubmit);
                        fullOb.put(photoKey, photoSubmit);
                        fullOb.put(latKey, latSubmit);
                        fullOb.put(lngKey, lngSubmit);

                        new HomeCreate(getActivity()).execute(new String[] {url, fullOb.toString()});

                    } catch (JSONException e) {
                        Log.d(LOG_TAG, "Error with JSON...", e);
                    }
                } else {
                    Toast.makeText(getContext(), "Something was required...",Toast.LENGTH_LONG).show();
                }



            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                rootView.findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        Intent intent = new Intent();
                        switch (item.getItemId()) {
                            case R.id.events:
                                Toast.makeText(getContext(),"First",Toast.LENGTH_LONG).show();
                                intent.setClass(getContext(),TabActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.offers:
                                Toast.makeText(getContext(),"Second",Toast.LENGTH_LONG).show();
                                intent.setClass(getContext(),HomeTabs.class);
                                startActivity(intent);
                                break;
                            case R.id.homes:
                                Toast.makeText(getContext(),"Third",Toast.LENGTH_LONG).show();
                                break;
                        }
                        return false;
                    }
                });

        return rootView;
    }



}
