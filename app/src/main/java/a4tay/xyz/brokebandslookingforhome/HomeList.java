package a4tay.xyz.brokebandslookingforhome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.util.ArrayList;
import a4tay.xyz.brokebandslookingforhome.Util.Home;
import a4tay.xyz.brokebandslookingforhome.Util.HomeRecyclerAdapter;
import a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers.HomeLoader;
import com.squareup.picasso.Picasso;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by johnkonderla on 3/26/17.
 */

public class HomeList extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Home>> {

    private final static String LOG_TAG = HomeList.class.getSimpleName();
    private ArrayList<Home> homeList;
    RecyclerView homeRecyclerView;
    private String url = "http://dev.4tay.xyz:4567/homes/";

    private static final String MY_PREFS = "harbor-preferences";
    private static final String NAME_KEY = "nameKey";
    private static final String PASS_KEY = "passKey";
    private String submittedEM;
    private String submittedPW1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.event_list_activity, container, false);
        homeRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_event_list);

        SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS, MODE_PRIVATE);

        submittedEM = prefs.getString(NAME_KEY, "");//defining an empty string as the default
        submittedPW1 = prefs.getString(PASS_KEY, ""); //defining an empty string as the default

        url = url + submittedEM + "/" + submittedPW1;

        Log.d(LOG_TAG,url);

        getLoaderManager().initLoader(6, null, this).forceLoad();


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

        HomeRecyclerAdapter homeRecyclerAdapter = new HomeRecyclerAdapter() {
            @Override
            public Home getItem(int position) {
                return homeList.get(position);
            }

            @Override
            public HomeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_item,parent,false);
                return new HomeHolder(view);
            }

            @Override
            public void onBindViewHolder(HomeHolder holder, int position) {
                final Home home = homeList.get(position);

                if(home != null) {
                    holder.name.setText(home.getHomeName());
                    holder.location.setText(home.getHomeCity());
                    Picasso.with(getContext()).load(home.getHomePhoto()).placeholder(R.drawable.anchor_logo).into(holder.photo);
                }

            }

            @Override
            public int getItemCount() {
                if(homeList != null && homeList.size() > 0){
                    return homeList.size();
                } else {
                    return 0;
                }
            }
        };

        homeRecyclerView.setAdapter(homeRecyclerAdapter);
        homeRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager cardLayoutManager = new LinearLayoutManager(getContext());
        homeRecyclerView.setLayoutManager(cardLayoutManager);
    }


    @Override
    public Loader<ArrayList<Home>> onCreateLoader(int id, Bundle args) {


        return new HomeLoader(getContext(),url );
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Home>> loader, ArrayList<Home> data) {

        homeList = data;

        Log.d(LOG_TAG,"homeList is " + String.valueOf(homeList.size()) + " long!");
        updateUI();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Home>> loader) {
        homeList = new ArrayList<>();

    }

}
