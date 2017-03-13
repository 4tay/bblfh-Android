package a4tay.xyz.brokebandslookingforhome.Util;

/**
 * Created by johnkonderla on 3/13/17.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import a4tay.xyz.brokebandslookingforhome.R;

/**
 * Created by johnkonderla on 3/12/17.
 */

public abstract class BandRecyclerAdapter extends RecyclerView.Adapter<BandRecyclerAdapter.BandHolder> {

    public class BandHolder extends RecyclerView.ViewHolder  {

        public TextView title;
        public TextView time;
        public BandHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.tv_event_item_title);
            time = (TextView) view.findViewById(R.id.tv_event_item_date);
        }
    }


    public abstract Band getItem(int position);
}
