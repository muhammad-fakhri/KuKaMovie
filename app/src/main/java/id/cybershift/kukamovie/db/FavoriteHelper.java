package id.cybershift.kukamovie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.provider.BaseColumns._ID;
import static id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns.ID_FROM_API;
import static id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns.TABLE_FAVORITE;

public class FavoriteHelper {
    private static final String DATABASE_TABLE = TABLE_FAVORITE;
    private static DatabaseHelper dataBaseHelper;
    private static FavoriteHelper INSTANCE;

    private static SQLiteDatabase database;

    private FavoriteHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public Cursor getAllFavoriteItem() {
        Log.d("FAKHRI PROVIDER", "BERHASIL QUERY DATA");
        return database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC");
    }

    public Cursor getFavoriteItemById(String id) {
        Log.d("FAKHRI PROVIDER", "BERHASIL QUERY DATA DENGAN ID");
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor getAllFavoriteMovie() {
        Log.d("FAKHRI PROVIDER", "BERHASIL QUERY DATA MOVIE");
        return database.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE type='movie'", null);
    }

    public Cursor getAllFavoriteTVShow() {
        Log.d("FAKHRI PROVIDER", "BERHASIL QUERY DATA TV SHOW");
        return database.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE type='tv'", null);
    }

    public boolean checkExistOrNot(int idFromAPI) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE id_api=" + idFromAPI, null);
        return cursor.getCount() > 0;
    }

    public long insertFavorite(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int deleteFavorite(String id) {
        return database.delete(TABLE_FAVORITE, ID_FROM_API + " = '" + id + "'", null);
//        return database.delete(DATABASE_TABLE, ID_FROM_API + " = ?", new String[]{id});
    }

}
