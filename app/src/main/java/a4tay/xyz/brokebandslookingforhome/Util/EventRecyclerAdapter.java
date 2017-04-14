package a4tay.xyz.brokebandslookingforhome.Util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import a4tay.xyz.brokebandslookingforhome.R;

/**
 * Created by johnkonderla on 3/12/17.
 */

public abstract class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.EventHolder> {

    public class EventHolder extends RecyclerView.ViewHolder  {

        public ImageView photo;
        public TextView title;
        public TextView time;
        public EventHolder(View view) {
            super(view);

            photo = (ImageView) view.findViewById(R.id.iv_event_photo);
            title = (TextView) view.findViewById(R.id.tv_event_item_title);
            time = (TextView) view.findViewById(R.id.tv_event_item_date);
        }
    }


    public abstract Event getItem(int position);
}
