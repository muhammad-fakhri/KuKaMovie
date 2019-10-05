package id.cybershift.kukamovie.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns;

import static id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns.TABLE_FAVORITE;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_FAVORITE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_FAVORITE,
            FavoriteColumns._ID,
            FavoriteColumns.ID_FROM_API,
            FavoriteColumns.TITLE,
            FavoriteColumns.OVERVIEW,
            FavoriteColumns.RATE,
            FavoriteColumns.YEAR,
            FavoriteColumns.POSTER,
            FavoriteColumns.TYPE
    );
    private static String DATABASE_NAME = "dbkukamovie";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
        onCreate(sqLiteDatabase);
    }
}
