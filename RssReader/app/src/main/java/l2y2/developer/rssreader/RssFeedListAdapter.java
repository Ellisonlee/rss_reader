package l2y2.developer.rssreader;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by CF0621 on 2017/08/31.
 */

public class RssFeedListAdapter
        extends RecyclerView.Adapter<RssFeedListAdapter.FeedModelViewHolder> {

    private List<RssFeedModel> mRssFeedModels;

    public static class FeedModelViewHolder extends RecyclerView.ViewHolder {
        private View rssFeedView;

        public FeedModelViewHolder(View v) {
            super(v);
            rssFeedView = v;
        }
    }

    public RssFeedListAdapter(List<RssFeedModel> rssFeedModels) {
        mRssFeedModels = rssFeedModels;
    }

    @Override
    public FeedModelViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rss_feed, parent, false);
        FeedModelViewHolder holder = new FeedModelViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(FeedModelViewHolder holder, int position) {
        final RssFeedModel rssFeedModel = mRssFeedModels.get(position);
        ((TextView)holder.rssFeedView.findViewById(R.id.pubDate))
                .setText(rssFeedModel.date);
        TextView title = (TextView) holder.rssFeedView.findViewById(R.id.titleText);
        String linkUrl = "<a href=\"" + rssFeedModel.link + "\">"+ rssFeedModel.title+"</a> ";
        if (Build.VERSION.SDK_INT >= 24) {
            title.setText(Html.fromHtml(linkUrl, Build.VERSION.SDK_INT));
        } else {
            title.setText(Html.fromHtml(linkUrl));
        }
        title.setMovementMethod(LinkMovementMethod.getInstance());
        //TODO handle the description text if it have some garbled
        ((TextView)holder.rssFeedView.findViewById(R.id.descriptionText))
                .setText(rssFeedModel.description);

    }

    @Override
    public int getItemCount() {
        return mRssFeedModels.size();
    }
}
