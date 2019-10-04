package id.cybershift.kukamovie.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.dicoding.picodiploma.myviewmodel";
    private static final String SCHEME = "content";

    private DatabaseContract() {
    }

    public static final class FavoriteColumns implements BaseColumns {
        public static String TABLE_FAVORITE = "favorite";
        static String ID_FROM_API = "id_api";
        static String TITLE = "title";
        static String OVERVIEW = "overview";
        static String RATE = "rate";
        static String YEAR = "year";
        static String POSTER = "poster";
        static String TYPE = "type";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_FAVORITE)
                .build();
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
}
