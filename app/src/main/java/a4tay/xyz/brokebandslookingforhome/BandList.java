package a4tay.xyz.brokebandslookingforhome;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

import a4tay.xyz.brokebandslookingforhome.Util.Band;
import a4tay.xyz.brokebandslookingforhome.Util.BandRecyclerAdapter;
import a4tay.xyz.brokebandslookingforhome.Util.EventRecyclerAdapter;
import a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers.BandLoader;

/**
 * Created by johnkonderla on 3/12/17.
 */

public class BandList extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Band>> {
    private final static String LOG_TAG = BandList.class.getSimpleName();

    private String url = "http://192.168.1.98:4567/bands";
    //private String url = "http://dev.4tay.xyz:4567/bands";
    private ArrayList<Band> bandList;
    private RecyclerView bandRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.event_list_activity, container, false);
        bandRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_event_list);
        getLoaderManager().initLoader(2, null, this).forceLoad();

        return rootView;

    }

    private void updateUI() {
        BandRecyclerAdapter bandRecyclerAdapter = new BandRecyclerAdapter() {
            @Override
            public Band getItem(int position) {
                return bandList.get(position);
            }

            @Override
            public BandHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item, parent, false);
                return new BandHolder(view);
            }

            @Override
            public void onBindViewHolder(BandHolder holder, int position) {
                final Band band = bandList.get(position);

                if (band != null) {
                    holder.title.setText(band.getName());
                    holder.time.setText(band.getGenre());
                }

            }

            @Override
            public int getItemCount() {
                if (bandList != null && bandList.size() > 0) {
                    return bandList.size();
                } else {
                    return 0;
                }
            }
        };

        bandRecyclerView.setAdapter(bandRecyclerAdapter);
        bandRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager cardLayoutManager = new LinearLayoutManager(getContext());
        bandRecyclerView.setLayoutManager(cardLayoutManager);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Band>> loader, ArrayList<Band> data) {
        bandList = data;

        Log.d(LOG_TAG,"bandList is " + String.valueOf(bandList.size()) + " long!");
        updateUI();

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Band>> loader) {

    }

    @Override
    public Loader<ArrayList<Band>> onCreateLoader(int id, Bundle args) {
        return new BandLoader(getContext(),url);
    }
}
