package a4tay.xyz.brokebandslookingforhome.Util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import a4tay.xyz.brokebandslookingforhome.R;

/**
 * Created by johnkonderla on 3/26/17.
 */

public abstract class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.HomeHolder> {

    public class HomeHolder extends RecyclerView.ViewHolder  {

        public TextView name;
        public TextView location;
        public ImageView photo;
        public HomeHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.tv_home_name);
            location = (TextView) view.findViewById(R.id.tv_home_city);
            photo = (ImageView) view.findViewById(R.id.iv_home_photo);
        }
    }


    public abstract Home getItem(int position);
}
