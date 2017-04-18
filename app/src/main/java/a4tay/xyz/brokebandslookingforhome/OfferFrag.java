package a4tay.xyz.brokebandslookingforhome;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import a4tay.xyz.brokebandslookingforhome.Util.Event;
import a4tay.xyz.brokebandslookingforhome.Util.Events;
import a4tay.xyz.brokebandslookingforhome.Util.Home;
import a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers.DetailLoader;
import a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers.HomeLoader;
import a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers.LoginStatus;
import a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers.PutManager;
import a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers.PutterAsyncLoader;

import static a4tay.xyz.brokebandslookingforhome.EventList.activity;
import static a4tay.xyz.brokebandslookingforhome.TabActivity.loggedIn;
import static a4tay.xyz.brokebandslookingforhome.TabActivity.baseURL;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by johnkonderla on 4/16/17.
 */

public class OfferFrag extends DialogFragment{

    private static final String MY_PREFS = "harbor-preferences";
    private static final String NAME_KEY = "nameKey";
    private static final String PASS_KEY = "passKey";
    private String submittedEM;
    private String submittedPW1;
    private static View rootView;
    private ImageView closeButton;
    private Spinner homeSpinner;
    private TextView memberCount;
    private TextView perkOffer;
    private TextView acceptOffer;
    private String offer;
    private int members;
    private int bandID;
    private int showID;
    private TextView durationRange;
    private ArrayList<String> homeNameList;
    private ArrayList<Integer> homeIDs;
    private String url = baseURL + "show/offerHome";
    JSONObject userInfo;
    private final static String LOG_TAG = OfferFrag.class.getSimpleName();
    @Override
    public void onCreate(Bundle savedInstanceStates) {
        super.onCreate(savedInstanceStates);
        setStyle(DialogFragment.STYLE_NORMAL, 0);

        homeNameList = getArguments().getStringArrayList("homeNames");
        homeIDs = getArguments().getIntegerArrayList("homeIDs");
        offer = getArguments().getString("bandOffer");
        bandID = getArguments().getInt("bandID");
        showID = getArguments().getInt("showID");
        members = getArguments().getInt("memberCount");

        SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS, MODE_PRIVATE);

        submittedEM = prefs.getString(NAME_KEY, "");//defining an empty string as the default
        submittedPW1 = prefs.getString(PASS_KEY, ""); //defining an empty string as the default

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            d.getWindow().setLayout(width, height);
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activty_offer_frag, container, false);
        root.setForegroundGravity(Gravity.END);

        closeButton = (ImageView) root.findViewById(R.id.iv_offer_detail_close);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        homeSpinner = (Spinner) root.findViewById(R.id.sp_offer_home_select);

        ArrayAdapter<String> homeAdapter = new ArrayAdapter<>(getContext(),
                R.layout.spinner_style,
                homeNameList);
        homeSpinner.setAdapter(homeAdapter);

        memberCount = (TextView) root.findViewById(R.id.tv_offer_member_count);

        memberCount.setText(String.valueOf(members));

        perkOffer = (TextView) root.findViewById(R.id.tv_offer_perks);

        perkOffer.setText(offer);

        durationRange = (TextView) root.findViewById(R.id.tv_offer_date_range);

        acceptOffer = (TextView) root.findViewById(R.id.tv_offer_accept);

        acceptOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject fullOb = new JSONObject();

                    fullOb.put("userName", submittedEM);
                    fullOb.put("password", submittedPW1);
                    fullOb.put("homeID", homeIDs.get(homeSpinner.getSelectedItemPosition()));
                    fullOb.put("bandID", bandID);
                    fullOb.put("showID", showID);

                    Log.d(LOG_TAG, url);
                    new PutterAsyncLoader(getActivity()).execute(new String[] {String.valueOf(fullOb), url});
                    Log.d(LOG_TAG, "after putter...");
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "My json failed..", e);
                }

            }
        });

        return root;
    }




}
