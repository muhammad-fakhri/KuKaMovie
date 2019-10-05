package id.cybershift.kukamovie.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "id.cybershift.kukamovie";
    private static final String SCHEME = "content";

    private DatabaseContract() {
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    public static final class FavoriteColumns implements BaseColumns {
        public static final String TABLE_FAVORITE = "favorite";
        public static final String ID_FROM_API = "id_api";
        public static final String TITLE = "title";
        public static final String OVERVIEW = "overview";
        public static final String YEAR = "year";
        public static final String RATE = "rate";
        public static final String POSTER = "poster";
        public static final String TYPE = "type";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_FAVORITE)
                .build();
    }
}
