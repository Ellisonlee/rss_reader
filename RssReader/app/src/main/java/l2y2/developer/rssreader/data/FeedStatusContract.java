package l2y2.developer.rssreader.data;

import android.provider.BaseColumns;

/**
 * Created by Lee on 2017/09/12.
 */

public class FeedStatusContract {
    public static final class FeedStatusEntry implements BaseColumns {
        public static final String TABLE_NAME = "feedStatus";
        public static final String COLUMN_FEED_URL_ID = "feedId";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_IS_READ = "is_read";
        public static final String COLUMN_IMAGE_URL = "image";
        public static final String COLUMN_DATE  = "date";
        public static final String COLUMN_AUTHOR  = "author";
    }
}
