package a4tay.xyz.brokebandslookingforhome;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import a4tay.xyz.brokebandslookingforhome.Util.Event;
import a4tay.xyz.brokebandslookingforhome.Util.EventRecyclerAdapter;
import a4tay.xyz.brokebandslookingforhome.Util.Events;
import a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers.GetterAsyncLoader;
import a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers.LoginStatus;
import a4tay.xyz.brokebandslookingforhome.Util.QueryUtils;
import a4tay.xyz.brokebandslookingforhome.Util.VerticalRecyclerAdapter;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by johnkonderla on 3/12/17.
 */

public class EventList extends Fragment /*implements LoaderManager.LoaderCallbacks<ArrayList<Event>>*/{

    private final static String LOG_TAG = EventList.class.getSimpleName();
    //private ArrayList<Event> eventList;
    RecyclerView eventRecyclerView;
    private static String showURL = "http://dev.4tay.xyz:4567/showsInTownWithoutConfirmation/";
    private static String bandURL = "http://dev.4tay.xyz:4567/fanToBandByGenre/";

    private static final String MY_PREFS = "harbor-preferences";
    private static final String NAME_KEY = "nameKey";
    private static final String PASS_KEY = "passKey";
    private String submittedEM;
    private String submittedPW1;
    private static View rootView;
    private static VerticalRecyclerAdapter adapter;
    private static ArrayList<Events> allEvents;

    private static int sequence = 0;
    static Activity activity;

    public static boolean loggedIn = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        allEvents = new ArrayList<>();
        SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS, MODE_PRIVATE);

        submittedEM = prefs.getString(NAME_KEY, "");//defining an empty string as the default
        submittedPW1 = prefs.getString(PASS_KEY, ""); //defining an empty string as the default
        activity = getActivity();
        if(!loggedIn) {
            String loginUrl = "http://dev.4tay.xyz:4567/fanLogin/";

            loginUrl = loginUrl + submittedEM + "/" + submittedPW1;

            Log.d(LOG_TAG,loginUrl);
            new LoginStatus(activity).execute(new String[]{loginUrl});
            showURL = showURL + submittedEM + "/" + submittedPW1;
            bandURL = bandURL + submittedEM + "/" + submittedPW1;
            new GetterAsyncLoader(activity, "EventList").execute(new String[]{showURL});

        }


        rootView = inflater.inflate(R.layout.event_list_activity, container, false);
        eventRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_event_list);

        RecyclerView my_recycler_view = (RecyclerView) rootView.findViewById(R.id.rv_vert_event_list);

        my_recycler_view.setHasFixedSize(true);

        adapter = new VerticalRecyclerAdapter(activity, allEvents);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));

        my_recycler_view.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(activity,"onResume was called", Toast.LENGTH_SHORT).show();

        //new GetterAsyncLoader(activity, "EventList").execute(new String[]{showURL});
        adapter.notifyDataSetChanged();
    }

    public static void dealWithResponse(String str) {
        switch (sequence) {
            case 0:
                Log.d(LOG_TAG,String.valueOf(sequence) + " " + str);
                allEvents.addAll(QueryUtils.makeEventFromJSON(str));
                Log.d(LOG_TAG, str);
                sequence++;
                adapter.notifyItemInserted(sequence);
                new GetterAsyncLoader(activity, "EventList").execute(new String[]{bandURL});
                break;
            case 1:
                Log.d(LOG_TAG,String.valueOf(sequence) + " " + str);
                allEvents.addAll(QueryUtils.makeBandListWithGenre(str));
                sequence++;
                adapter.notifyItemInserted(sequence);
                new GetterAsyncLoader(activity, "EventList").execute(new String[]{showURL});
                break;
            case 2:
                Log.d(LOG_TAG,String.valueOf(sequence) + " " + str);
                allEvents.addAll(QueryUtils.makeEventFromJSON(str));
                sequence++;
                adapter.notifyItemInserted(sequence);
                new GetterAsyncLoader(activity, "EventList").execute(new String[]{bandURL});
                break;
            case 3:
                Log.d(LOG_TAG,String.valueOf(sequence) + " " + str);
                allEvents.addAll(QueryUtils.makeBandListWithGenre(str));
                sequence++;
                adapter.notifyItemInserted(sequence);
                new GetterAsyncLoader(activity, "EventList").execute(new String[]{showURL});
                break;
            default:
                Log.d(LOG_TAG,String.valueOf(sequence) + " " + str);
                allEvents.addAll(QueryUtils.makeEventFromJSON(str));
                adapter.notifyItemInserted(sequence + 1);
                sequence = 0;
                break;
        }
    }
}
