package l2y2.developer.rssreader;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Lee on 2017/09/01.
 */

public class RssUrlListAdapter extends RecyclerView.Adapter<RssUrlListAdapter.RssUrlListAdapterViewHolder>{

    private HashMap<String, String> mMapData;
    private final RssUrlListAdapterOnClickHandler mClickHandler;

    public RssUrlListAdapter(RssUrlListAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }
    @Override
    public RssUrlListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.item_rss_url_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new RssUrlListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RssUrlListAdapterViewHolder holder, int position) {
        List keys = new ArrayList(mMapData.keySet());
        String rssTitle = (String) keys.get(position);
        holder.rssUrlTextView.setText(rssTitle);
    }

    public void setData(HashMap<String, String> map) {
        mMapData = map;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mMapData.size();
    }

    public class RssUrlListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView rssUrlTextView;
        public RssUrlListAdapterViewHolder(View itemView) {
            super(itemView);
            rssUrlTextView = (TextView) itemView.findViewById(R.id.rssUrlTitleText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            List values = new ArrayList(mMapData.values());
            String rssUrl = (String) values.get(adapterPosition);
            mClickHandler.onClick(rssUrl);
        }
    }



    public interface RssUrlListAdapterOnClickHandler {
        void onClick(String rssUrl);
    }
}
