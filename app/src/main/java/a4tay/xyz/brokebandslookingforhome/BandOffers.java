package a4tay.xyz.brokebandslookingforhome;

import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import a4tay.xyz.brokebandslookingforhome.Util.Home;
import a4tay.xyz.brokebandslookingforhome.Util.HomeRecyclerAdapter;
import a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers.HomeLoader;

import static a4tay.xyz.brokebandslookingforhome.TabActivity.baseURL;
import static a4tay.xyz.brokebandslookingforhome.TabActivity.inBand;
import static a4tay.xyz.brokebandslookingforhome.TabActivity.loggedIn;
import static android.content.Context.MODE_PRIVATE;

public class BandOffers extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<?>> {


    private static final String MY_PREFS = "harbor-preferences";
    private static final String NAME_KEY = "nameKey";
    private static final String PASS_KEY = "passKey";
    private static final String BAND_KEY = "bandKey";
    public static  String submittedEM;
    public static String submittedPW1;
    private RecyclerView offerRecyclerView;
    private ArrayList<Home> homeList;
    private String url = baseURL + "band/getOffers/";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.band_list_activity, container, false);
        offerRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_band_list);
        if(loggedIn && inBand > 0) {
            SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS, MODE_PRIVATE);

            submittedEM = prefs.getString(NAME_KEY, "");//defining an empty string as the default
            submittedPW1 = prefs.getString(PASS_KEY, ""); //defining an empty string as the default

            url = url + submittedEM + "/" + submittedPW1;

            getLoaderManager().initLoader(2, null, this).forceLoad();
        }

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
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_item, parent, false);
                return new HomeHolder(view);
            }

            @Override
            public void onBindViewHolder(HomeHolder holder, int position) {
                final Home home = homeList.get(position);

                if (home != null) {
                    holder.name.setText(home.getHomeName());
                    holder.location.setText(home.getHomeCity());

                    Picasso.with(getContext()).load(home.getHomePhoto()).placeholder(R.drawable.anchor_logo).into(holder.photo);
                    holder.homeWrap.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getContext(),home.getHomeName(),Toast.LENGTH_SHORT).show();


                            Bundle bundled = new Bundle();
                            //bundled.putString("bandOffer", bands.get(bandSpinner.getSelectedItemPosition()).getOffer());
                            FragmentTransaction ft;

                            //ft = getFragmentManager().beginTransaction();
                            OfferFrag frag = new OfferFrag();
                            frag.setArguments(bundled);
                            //frag.show(ft, LOG_TAG);

                        }
                    });
                }

            }

            @Override
            public int getItemCount() {
                return homeList.size();
            }
        };

        offerRecyclerView.setAdapter(homeRecyclerAdapter);
        offerRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager cardLayoutManager = new LinearLayoutManager(getContext());
        offerRecyclerView.setLayoutManager(cardLayoutManager);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<?>> loader, ArrayList<?> data) {
        homeList = (ArrayList<Home>) data;

        updateUI();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<?>> loader) {

    }

    @Override
    public Loader<ArrayList<?>> onCreateLoader(int id, Bundle args) {

        return new HomeLoader(getContext(), url);

    }

}
