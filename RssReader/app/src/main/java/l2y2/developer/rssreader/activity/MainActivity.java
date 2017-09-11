package l2y2.developer.rssreader.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;

import l2y2.developer.rssreader.R;
import l2y2.developer.rssreader.RssUrlListAdapter;
import l2y2.developer.rssreader.data.RssDBHelper;
import l2y2.developer.rssreader.data.RssReaderContract;

public class MainActivity extends AppCompatActivity implements RssUrlListAdapter.RssUrlListAdapterOnClickHandler {
    private RecyclerView mRecyclerView;
    private RssUrlListAdapter mUrlAdapter;

    private SQLiteDatabase mDb;
    private TextView mErrorMessageDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mErrorMessageDisplay = (TextView) findViewById(R.id.error_message_display);
        mRecyclerView = (RecyclerView) findViewById(R.id.urlRecyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mUrlAdapter = new RssUrlListAdapter(this);
        mRecyclerView.setAdapter(mUrlAdapter);
        RssDBHelper dbHelper = new RssDBHelper(this);
        mDb = dbHelper.getWritableDatabase();
        loadRssUrlData();
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                long id = (long) viewHolder.itemView.getTag();
                removeGuest(id);
                loadRssUrlData();
            }
        }).attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onRestart() {
        loadRssUrlData();
        super.onRestart();
    }

    private void loadRssUrlData() {
        showTitleDataView();
        HashMap<String, String> map = new HashMap<String, String>();
        Cursor cursor = getAllFeeds();
        if (cursor != null) {
            showTitleDataView();
            mUrlAdapter.setData(cursor);
        } else {
            showErrorMessage();
        }
    }

    private void showTitleDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(String rssUrl) {
        Context context = this;
        Class rssFeedClass = RssFeedActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, rssFeedClass);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, rssUrl);
        startActivity(intentToStartDetailActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_feed) {
            Context context = this;
            Class addFeedClass = AddFeedActivity.class;
            Intent intent = new Intent(context, addFeedClass);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Cursor getAllFeeds() {
        return mDb.query(
                RssReaderContract.RssReaderEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                RssReaderContract.RssReaderEntry._ID,
                null
        );
    }

    private boolean removeGuest(long id) {
        return mDb.delete(RssReaderContract.RssReaderEntry.TABLE_NAME, RssReaderContract.RssReaderEntry._ID + "=" + id, null) > 0;
    }
}
