package id.cybershift.kukamovie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import id.cybershift.kukamovie.entity.Favorite;

import static android.provider.BaseColumns._ID;
import static id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns.ID_FROM_API;
import static id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns.OVERVIEW;
import static id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns.POSTER;
import static id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns.RATE;
import static id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns.TABLE_FAVORITE;
import static id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns.TITLE;
import static id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns.TYPE;
import static id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns.YEAR;

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

    public ArrayList<Favorite> getAllFavoriteItem() {
        ArrayList<Favorite> arrayList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DATABASE_TABLE, null);
        cursor.moveToFirst();
        Favorite favorite;
        if (cursor.getCount() > 0) {
            do {
                favorite = new Favorite();
                favorite.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                favorite.setIdFromAPI(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(ID_FROM_API))));
                favorite.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                favorite.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                favorite.setRate(Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(RATE))));
                favorite.setYear(cursor.getString(cursor.getColumnIndexOrThrow(YEAR)));
                favorite.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                favorite.setType(cursor.getString(cursor.getColumnIndexOrThrow(TYPE)));
                arrayList.add(favorite);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<Favorite> getAllFavoriteMovie() {
        ArrayList<Favorite> arrayList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE type='movie'", null);
        cursor.moveToFirst();
        Favorite favorite;
        if (cursor.getCount() > 0) {
            do {
                favorite = new Favorite();
                favorite.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                favorite.setIdFromAPI(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(ID_FROM_API))));
                favorite.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                favorite.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                favorite.setRate(Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(RATE))));
                favorite.setYear(cursor.getString(cursor.getColumnIndexOrThrow(YEAR)));
                favorite.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                favorite.setType(cursor.getString(cursor.getColumnIndexOrThrow(TYPE)));
                arrayList.add(favorite);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<Favorite> getAllFavoriteTVShow() {
        ArrayList<Favorite> arrayList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE type='tv'", null);
        cursor.moveToFirst();
        Favorite favorite;
        if (cursor.getCount() > 0) {
            do {
                favorite = new Favorite();
                favorite.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                favorite.setIdFromAPI(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(ID_FROM_API))));
                favorite.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                favorite.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                favorite.setRate(Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(RATE))));
                favorite.setYear(cursor.getString(cursor.getColumnIndexOrThrow(YEAR)));
                favorite.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                favorite.setType(cursor.getString(cursor.getColumnIndexOrThrow(TYPE)));
                arrayList.add(favorite);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public boolean checkExistOrNot(int idFromAPI) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE id_api=" + idFromAPI, null);
        if (cursor.getCount() <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public long insertFavorite(Favorite favorite) {
        ContentValues args = new ContentValues();
        args.put(ID_FROM_API, favorite.getIdFromAPI());
        args.put(TITLE, favorite.getTitle());
        args.put(OVERVIEW, favorite.getOverview());
        args.put(RATE, favorite.getRate());
        args.put(YEAR, favorite.getYear());
        args.put(POSTER, favorite.getPoster());
        args.put(TYPE, favorite.getType());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteFavorite(int id) {
        return database.delete(TABLE_FAVORITE, ID_FROM_API + " = '" + id + "'", null);
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " ASC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
