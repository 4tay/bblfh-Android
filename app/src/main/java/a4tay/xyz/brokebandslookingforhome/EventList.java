package a4tay.xyz.brokebandslookingforhome;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.LoaderManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import a4tay.xyz.brokebandslookingforhome.Util.Event;
import a4tay.xyz.brokebandslookingforhome.Util.EventRecyclerAdapter;
import a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers.EventLoader;
import a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers.LoginStatus;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by johnkonderla on 3/12/17.
 */

public class EventList extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Event>>{

    private final static String LOG_TAG = EventList.class.getSimpleName();
    private ArrayList<Event> eventList;
    RecyclerView eventRecyclerView;
    private String url = "http://dev.4tay.xyz:4567/eventsByID/1";

    private static final String MY_PREFS = "harbor-preferences";
    private static final String NAME_KEY = "nameKey";
    private static final String PASS_KEY = "passKey";
    private String submittedEM;
    private String submittedPW1;

    public static boolean loggedIn = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(!loggedIn) {
            SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS, MODE_PRIVATE);

            submittedEM = prefs.getString(NAME_KEY, "");//defining an empty string as the default
            submittedPW1 = prefs.getString(PASS_KEY, ""); //defining an empty string as the default

            String loginUrl = "http://dev.4tay.xyz:4567/fanLogin/";

            loginUrl = loginUrl + submittedEM + "/" + submittedPW1;

            Log.d(LOG_TAG,loginUrl);
            new LoginStatus(getActivity()).execute(new String[]{loginUrl});
        }


        final View rootView = inflater.inflate(R.layout.event_list_activity, container, false);
        eventRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_event_list);
        getLoaderManager().initLoader(1,null,this).forceLoad();

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
    private void updateUI() {

        EventRecyclerAdapter eventRecyclerAdapter = new EventRecyclerAdapter() {
            @Override
            public Event getItem(int position) {
                return eventList.get(position);
            }

            @Override
            public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item,parent,false);
                return new EventHolder(view);
            }

            @Override
            public void onBindViewHolder(EventHolder holder, int position) {
                final Event event = eventList.get(position);

                if(event != null) {
                    holder.title.setText(event.getEventTitle());
                    holder.time.setText(event.getEventDate());
                }

            }

            @Override
            public int getItemCount() {
                if(eventList != null && eventList.size() > 0){
                    return eventList.size();
                } else {
                    return 0;
                }
            }
        };

        eventRecyclerView.setAdapter(eventRecyclerAdapter);
        eventRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager cardLayoutManager = new LinearLayoutManager(getContext());
        eventRecyclerView.setLayoutManager(cardLayoutManager);
    }

    @Override
    public Loader<ArrayList<Event>> onCreateLoader(int id, Bundle args) {


        return new EventLoader(getContext(),url);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Event>> loader, ArrayList<Event> data) {
        eventList = data;

        Log.d(LOG_TAG,"eventList is " + String.valueOf(eventList.size()) + " long!");
        updateUI();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Event>> loader) {
        eventList = new ArrayList<>();

    }
}
