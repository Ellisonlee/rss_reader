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

import l2y2.developer.rssreader.data.RssReaderContract;

/**
 * Created by Lee on 2017/09/01.
 */

public class RssUrlListAdapter extends RecyclerView.Adapter<RssUrlListAdapter.RssUrlListAdapterViewHolder>{

    private final RssUrlListAdapterOnClickHandler mClickHandler;
    private Cursor mCursor;

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
        if (!mCursor.moveToPosition(position))
            return;
        String rssTitle = mCursor.getString(mCursor.getColumnIndex(RssReaderContract.RssReaderEntry.COLUMN_TITLE));
        holder.rssUrlTextView.setText(rssTitle);
        long id = mCursor.getLong(mCursor.getColumnIndex(RssReaderContract.RssReaderEntry._ID));
        holder.itemView.setTag(id);
    }

    public void setData(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
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
            if(!mCursor.moveToPosition(adapterPosition))
                return;
            String rssUrl = mCursor.getString(mCursor.getColumnIndex(RssReaderContract.RssReaderEntry.COLUMN_RSS_URL));
            mClickHandler.onClick(rssUrl);
        }
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }

    public interface RssUrlListAdapterOnClickHandler {
        void onClick(String rssUrl);
    }
}
