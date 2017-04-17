package a4tay.xyz.brokebandslookingforhome.Util;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import a4tay.xyz.brokebandslookingforhome.R;
import a4tay.xyz.brokebandslookingforhome.ShowDetail;

/**
 * Created by johnkonderla on 4/2/17.
 */

public class HorizontalRecyclerAdapter extends RecyclerView.Adapter<HorizontalRecyclerAdapter.SingleItemRowHolder> {

    private ArrayList<Event> itemsList;
    private Context mContext;

    public HorizontalRecyclerAdapter(Context context, ArrayList<Event> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_list_item, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        Event singleItem = itemsList.get(i);

        holder.tvTitle.setText(singleItem.getEventTitle());
        if (!singleItem.getEventPhoto().equals("")) {
            Picasso.with(mContext).load(singleItem.getEventPhoto()).placeholder(R.drawable.anchor_logo).into(holder.itemImage);
        } else {
            Picasso.with(mContext).load(R.drawable.anchor_logo).into(holder.itemImage);
        }
        holder.eventID = singleItem.getEventID();
        holder.eventType = singleItem.getEventType();
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvTitle;

        protected ImageView itemImage;
        protected int eventType;
        protected int eventID;


        public SingleItemRowHolder(View view) {
            super(view);

            this.tvTitle = (TextView) view.findViewById(R.id.tv_event_item_title);
            this.itemImage = (ImageView) view.findViewById(R.id.iv_event_photo);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent();
                    intent.putExtra("eventID", eventID);
                    intent.putExtra("eventType", eventType);
                    intent.setClass(mContext, ShowDetail.class);
                    mContext.startActivity(intent);

                }
            });


        }

    }
}
