package l2y2.developer.rssreader.data;

import android.provider.BaseColumns;

/**
 * Created by Lee on 2017/08/31.
 */

public class RssReaderContract {
    public static final class RssReaderEntry implements BaseColumns {
        public static final String TABLE_NAME = "rssFeed";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RSS_URL = "url";
    }
}
