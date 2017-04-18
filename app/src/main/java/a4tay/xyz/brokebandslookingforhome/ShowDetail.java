package a4tay.xyz.brokebandslookingforhome;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import a4tay.xyz.brokebandslookingforhome.Util.Band;
import a4tay.xyz.brokebandslookingforhome.Util.Event;
import a4tay.xyz.brokebandslookingforhome.Util.Home;
import a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers.DetailLoader;
import a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers.GetterAsyncLoader;
import a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers.LoginStatus;
import a4tay.xyz.brokebandslookingforhome.Util.OnDataSendToActivity;
import a4tay.xyz.brokebandslookingforhome.Util.QueryUtils;

//import static a4tay.xyz.brokebandslookingforhome.EventList.activity;
import static a4tay.xyz.brokebandslookingforhome.EventList.activity;
import static a4tay.xyz.brokebandslookingforhome.TabActivity.loggedIn;
import static a4tay.xyz.brokebandslookingforhome.TabActivity.baseURL;


public class ShowDetail extends AppCompatActivity implements OnDataSendToActivity /*implements LoaderManager.LoaderCallbacks<ArrayList<?>>*/ {

    private static final String LOG_TAG = ShowDetail.class.getSimpleName();
    private int type;
    private int id;
    private ArrayList<Band> bands;
    private ArrayList<String> bandNames;
    private ArrayList<Event> events;
    private String url;
    private ImageView detailPhoto;
    private TextView title;
    private TextView offerHome;
    private TextView memberCount;
    private TextView offerDates;
    private TextView bandOffering;
    private ArrayList<Home> homeList;
    private Spinner bandSpinner;
    private static final String MY_PREFS = "harbor-preferences";
    private static final String NAME_KEY = "nameKey";
    private static final String PASS_KEY = "passKey";
    private String submittedEM;
    private String submittedPW1;
    private int sequence = 0;
    private Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        activity = this;
        bandNames = new ArrayList<>();

        bandSpinner = (Spinner) findViewById(R.id.sp_band_dropdown);

        detailPhoto = (ImageView) findViewById(R.id.iv_show_detail_photo);
        title = (TextView) findViewById(R.id.tv_show_detail_title);
        memberCount = (TextView) findViewById(R.id.tv_show_detail_members);
        offerDates = (TextView) findViewById(R.id.tv_show_detail_date);
        offerHome = (TextView) findViewById(R.id.tv_show_detail_offer_home);
        bandOffering = (TextView) findViewById(R.id.tv_show_detail_band_offer);
        offerHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundled = new Bundle();
                bundled.putString("bandOffer", bands.get(bandSpinner.getSelectedItemPosition()).getOffer());
                bundled.putInt("memberCount",bands.get(bandSpinner.getSelectedItemPosition()).getMemberCount());
                bundled.putInt("bandID", bands.get(bandSpinner.getSelectedItemPosition()).getId());
                bundled.putInt("showID", events.get(0).getEventID());
                ArrayList<String> homeNames = new ArrayList<String>();
                ArrayList<Integer> homeIDs = new ArrayList<Integer>();
                for(Home home: homeList) {
                    homeNames.add(home.getHomeName());
                    homeIDs.add(home.getHomeID());
                }
                bundled.putStringArrayList("homeNames", homeNames);
                bundled.putIntegerArrayList("homeIDs", homeIDs);
                FragmentTransaction ft;

                ft = getFragmentManager().beginTransaction();
                OfferFrag frag = new OfferFrag();
                frag.setArguments(bundled);
                frag.show(ft, LOG_TAG);

            }
        });

        Intent intent = getIntent();
        if(intent != null) {
            type = intent.getIntExtra("eventType", 0);
            id = intent.getIntExtra("eventID", 0);
        }
        switch (type) {
            case 2:
                url = baseURL + "band/" + id;
                break;
            default:
                url = baseURL + "show/" + id;
        }

        SharedPreferences prefs = getApplicationContext().getSharedPreferences(MY_PREFS, MODE_PRIVATE);

        submittedEM = prefs.getString(NAME_KEY, "");//defining an empty string as the default
        submittedPW1 = prefs.getString(PASS_KEY, ""); //defining an empty string as the default

        if(!loggedIn) {
            String loginUrl = baseURL + "fan/login/";

            loginUrl = loginUrl + submittedEM + "/" + submittedPW1;

            Log.d(LOG_TAG,loginUrl);
            new LoginStatus(activity).execute(new String[]{loginUrl});
            new GetterAsyncLoader(activity,"event").execute(url);
            //getSupportLoaderManager().initLoader(5, null, this).forceLoad();
        } else {
            //getSupportLoaderManager().initLoader(5, null, this).forceLoad();
            new GetterAsyncLoader(activity,"event").execute(url);
        }

        final ActionBar aBar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textView = (TextView) viewActionBar.findViewById(R.id.tv_action_bar);
        textView.setText("Deets!");
        aBar.setCustomView(viewActionBar, params);
        aBar.setDisplayShowCustomEnabled(true);
        aBar.setDisplayShowTitleEnabled(false);
        aBar.setDisplayHomeAsUpEnabled(true);
        aBar.setLogo(R.drawable.anchor_logo);

    }

    @Override
    public void sendData(String[] asyncReturn) {
        Log.d(LOG_TAG,"sendData");


        if (asyncReturn[1] == null || asyncReturn[1].equals("")) {
            asyncReturn[1] = "Comm failure...";
            Log.e(LOG_TAG, asyncReturn[1]);
        }
        if (asyncReturn[1].substring(0, 1).equals("{")) {

            switch (type) {
            case 2:
                Log.d(LOG_TAG, "Bands are parsing?? with type: " + type);
                Log.d(LOG_TAG, "full result " + asyncReturn[1]);
                bands = QueryUtils.getBandList(asyncReturn[1]);

                Log.d(LOG_TAG, "how many bands?? " + bands.size());

                for(int i = 0; i < bands.size(); i++) {
                    bandNames.add(bands.get(i).getName());
                }
                ArrayAdapter<String> bandAdapter = new ArrayAdapter<>(getApplicationContext(),
                        R.layout.spinner_style,
                        bandNames);
                bandSpinner.setAdapter(bandAdapter);
                bandOffering.setText(bands.get(0).getOffer());
                memberCount.setText("Harbor " + bands.get(0).getMemberCount() + " members");

                bandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        bandOffering.setText(bands.get(position).getOffer());
                        memberCount.setText("Harbor " + bands.get(position).getMemberCount() + " members");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                break;
            case 3:
                homeList = QueryUtils.getFanHomes(asyncReturn[1]);

                Log.d(LOG_TAG, homeList.get(0).getHomeName());
                url = baseURL + "band/bandFromShow/" + events.get(0).getEventID();
                type = 2;
                if(sequence == 1) {
                    sequence++;
                    Log.d(LOG_TAG, "getting bands...");
//                    getSupportLoaderManager().initLoader(5, null, this).forceLoad();
                    new GetterAsyncLoader(activity,"event").execute(url);
                    Log.d(LOG_TAG, String.valueOf(sequence));
                }
                break;

            default:
                events = QueryUtils.makeEventFromJSON(asyncReturn[1]);
                if(events.get(0).getEventPhoto() != null && !events.get(0).getEventPhoto().equals("")) {
                    Picasso.with(getApplicationContext()).load(events.get(0).getEventPhoto()).placeholder(R.drawable.anchor_logo).into(detailPhoto);
                }
                title.setText(events.get(0).getEventTitle());
                offerDates.setText(events.get(0).getEventDate());

                url = baseURL + "home/userHomes/" + submittedEM + "/" + submittedPW1;
                type = 3;
                if(sequence == 0) {
                    sequence++;
                    Log.d(LOG_TAG, "getting homes...");
                    Log.d(LOG_TAG, String.valueOf(sequence));
                    new GetterAsyncLoader(activity,"event").execute(url);
                }
        }

        } else {
            Log.d(LOG_TAG, asyncReturn[0]);
            Toast.makeText(getApplicationContext(), asyncReturn[1], Toast.LENGTH_LONG).show();
        }

    }

}
