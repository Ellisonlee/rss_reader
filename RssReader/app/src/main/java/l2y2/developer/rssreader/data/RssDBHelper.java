package l2y2.developer.rssreader.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lee on 2017/08/31.
 */

public class RssDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "rssFeed.db";
    private static final int DATABASE_VERSION = 2;

    public RssDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_RSS_TABLE =
            "CREATE TABLE " + RssReaderContract.RssReaderEntry.TABLE_NAME + " (" +
                RssReaderContract.RssReaderEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RssReaderContract.RssReaderEntry.COLUMN_DATE + " TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, "                 +
                RssReaderContract.RssReaderEntry.COLUMN_TITLE + " VARCHAR(128) NOT NULL, " +
                RssReaderContract.RssReaderEntry.COLUMN_RSS_URL + " TEXT NOT NULL" + ");";
        final String SQL_CREATE_FEED_TABLE =
            "CREATE TABLE " + FeedStatusContract.FeedStatusEntry.TABLE_NAME + " (" +
                    FeedStatusContract.FeedStatusEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FeedStatusContract.FeedStatusEntry.COLUMN_DATE + " TIMESTAMP NOT NULL, "  +
                    FeedStatusContract.FeedStatusEntry.COLUMN_TITLE + " VARCHAR(128) NOT NULL, " +
                    FeedStatusContract.FeedStatusEntry.COLUMN_URL + " TEXT NOT NULL, " +
                    FeedStatusContract.FeedStatusEntry.COLUMN_FEED_URL_ID + " INTEGER(10) NOT NULL, " +
                    FeedStatusContract.FeedStatusEntry.COLUMN_AUTHOR + " TEXT NOT NULL, " +
                    FeedStatusContract.FeedStatusEntry.COLUMN_IMAGE_URL + " TEXT NOT NULL, " +
                    FeedStatusContract.FeedStatusEntry.COLUMN_IS_READ + " BOOLEAN NOT NULL DEFAULT FALSE" +
                    ");";
        db.execSQL(SQL_CREATE_RSS_TABLE);
        db.execSQL(SQL_CREATE_FEED_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RssReaderContract.RssReaderEntry.TABLE_NAME);
        onCreate(db);
    }
}
