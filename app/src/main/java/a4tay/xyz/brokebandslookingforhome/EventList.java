package a4tay.xyz.brokebandslookingforhome;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import a4tay.xyz.brokebandslookingforhome.Util.Event;
import a4tay.xyz.brokebandslookingforhome.Util.Events;
import a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers.DetailLoader;
import a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers.GetterAsyncLoader;
import a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers.LoginStatus;
import a4tay.xyz.brokebandslookingforhome.Util.QueryUtils;
import a4tay.xyz.brokebandslookingforhome.Util.VerticalRecyclerAdapter;

import static a4tay.xyz.brokebandslookingforhome.TabActivity.baseURL;
import static a4tay.xyz.brokebandslookingforhome.TabActivity.loggedIn;
import static a4tay.xyz.brokebandslookingforhome.TabActivity.submittedEM;
import static a4tay.xyz.brokebandslookingforhome.TabActivity.submittedPW1;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by johnkonderla on 3/12/17.
 */

public class EventList extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<?>>{

    private final static String LOG_TAG = EventList.class.getSimpleName();
    RecyclerView eventRecyclerView;

    private static String showURL = baseURL + "show/inTownWithoutConfirmation/";
    private static String bandURL = baseURL + "fanToBandByGenre/";


    private static View rootView;
    private static VerticalRecyclerAdapter adapter;
    private static ArrayList<Events> allEvents;

    private static int sequence = 0;
    static Activity activity;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        allEvents = new ArrayList<>();


        activity = getActivity();

        if(loggedIn) {
            showURL = showURL + submittedEM + "/" + submittedPW1;
            bandURL = bandURL + submittedEM + "/" + submittedPW1;
            getLoaderManager().initLoader(5, null, this).forceLoad();
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
        sequence = 0;
        if(loggedIn) {
            getList();
        }
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<?>> loader, ArrayList<?> data) {
        Events events = new Events();
        events.setEvents((ArrayList<Event>) data);
        allEvents.add(events);
        sequence++;
        adapter.notifyItemInserted(sequence);
        if(sequence < 3) {
            getLoaderManager().initLoader(5, null, this).forceLoad();
        }

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<?>> loader) {

    }

    @Override
    public Loader<ArrayList<?>> onCreateLoader(int id, Bundle args) {

        return new DetailLoader(getContext(),showURL, 1);
    }

    private void getList() {
        showURL = showURL + submittedEM + "/" + submittedPW1;
        bandURL = bandURL + submittedEM + "/" + submittedPW1;
        getLoaderManager().initLoader(5, null, this).forceLoad();
    }

    public void dealWithResponse(String str) {
        if(loggedIn) {
            getList();
            Log.d(LOG_TAG, "populate list!");
        }
    }
}
