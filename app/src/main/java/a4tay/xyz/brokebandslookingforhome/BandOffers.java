package a4tay.xyz.brokebandslookingforhome;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import a4tay.xyz.brokebandslookingforhome.Util.Home;
import a4tay.xyz.brokebandslookingforhome.Util.HomeRecyclerAdapter;
import a4tay.xyz.brokebandslookingforhome.Util.LoaderManagers.HomeLoader;

public class BandOffers extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<?>> {


    private RecyclerView offerRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.band_list_activity, container, false);
        offerRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_band_list);
        getLoaderManager().initLoader(2, null, this).forceLoad();

        return rootView;
    }


    private void updateUI() {
        HomeRecyclerAdapter homeRecyclerAdapter = new HomeRecyclerAdapter() {
            @Override
            public Home getItem(int position) {
                return null;
            }

            @Override
            public HomeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_item, parent, false);
                return new HomeHolder(view);
            }

            @Override
            public void onBindViewHolder(HomeHolder holder, int position) {
//                final Band band = bandList.get(position);
//
//                if (band != null) {
//                    holder.title.setText(band.getName());
//                    holder.time.setText(band.getGenre());
//                }

            }

            @Override
            public int getItemCount() {
                return 0;
            }
        };

        offerRecyclerView.setAdapter(homeRecyclerAdapter);
        offerRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager cardLayoutManager = new LinearLayoutManager(getContext());
        offerRecyclerView.setLayoutManager(cardLayoutManager);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<?>> loader, ArrayList<?> data) {
        updateUI();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<?>> loader) {

    }

    @Override
    public Loader<ArrayList<?>> onCreateLoader(int id, Bundle args) {

        return new HomeLoader(getContext(), "");

    }

}
