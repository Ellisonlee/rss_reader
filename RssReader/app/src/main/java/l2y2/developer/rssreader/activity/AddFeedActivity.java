package l2y2.developer.rssreader.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import l2y2.developer.rssreader.R;
import l2y2.developer.rssreader.data.RssDBHelper;
import l2y2.developer.rssreader.data.RssReaderContract;

public class AddFeedActivity extends AppCompatActivity {

    private EditText mTitle;
    private EditText mUrl;
    private Button mAdd;
    private SQLiteDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feed);
        mTitle = (EditText)findViewById(R.id.feed_title);
        mUrl = (EditText)findViewById(R.id.feed_url);
        mAdd = (Button) findViewById(R.id.add_url_feed);
        RssDBHelper dbHelper = new RssDBHelper(this);
        mDb = dbHelper.getWritableDatabase();
        initializeAddButton();
    }

    private void initializeAddButton()
    {
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isVaild = checkUrlAndTitle();
                if (isVaild)
                {
                    Log.e("test", "insert a data ");
                    addNewFeedURL(mTitle.getText().toString(), mUrl.getText().toString());
                    finish();
                } else {
                    // TODO ADD a Toast here
                    Log.e("test", "invaild string");
                }
            }
        });
    }

    private long addNewFeedURL(String title, String url) {
        ContentValues cv = new ContentValues();
        cv.put(RssReaderContract.RssReaderEntry.COLUMN_TITLE, title);
        cv.put(RssReaderContract.RssReaderEntry.COLUMN_RSS_URL, url);
        return mDb.insert(RssReaderContract.RssReaderEntry.TABLE_NAME, null, cv);
    }

    private boolean checkUrlAndTitle()
    {
        if(mUrl.getText().length() == 0 || mTitle.getText().length() == 0)
        {
            return  false;
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
