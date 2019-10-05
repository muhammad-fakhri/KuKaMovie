package id.cybershift.kukamovie.helper;

import android.database.Cursor;

import java.util.ArrayList;

import id.cybershift.kukamovie.entity.Favorite;

import static android.provider.BaseColumns._ID;
import static id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns.ID_FROM_API;
import static id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns.OVERVIEW;
import static id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns.POSTER;
import static id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns.RATE;
import static id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns.TITLE;
import static id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns.TYPE;
import static id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns.YEAR;

public class MappingHelper {
    public static ArrayList<Favorite> mapFavoriteCursorToArrayList(Cursor favoritesCursor) {
        ArrayList<Favorite> favoriteList = new ArrayList<>();
        Favorite favorite;
        while (favoritesCursor.moveToNext()) {
            favorite = new Favorite();
            favorite.setId(favoritesCursor.getInt(favoritesCursor.getColumnIndexOrThrow(_ID)));
            favorite.setIdFromAPI(Integer.parseInt(favoritesCursor.getString(favoritesCursor.getColumnIndexOrThrow(ID_FROM_API))));
            favorite.setTitle(favoritesCursor.getString(favoritesCursor.getColumnIndexOrThrow(TITLE)));
            favorite.setOverview(favoritesCursor.getString(favoritesCursor.getColumnIndexOrThrow(OVERVIEW)));
            favorite.setRate(Double.parseDouble(favoritesCursor.getString(favoritesCursor.getColumnIndexOrThrow(RATE))));
            favorite.setYear(favoritesCursor.getString(favoritesCursor.getColumnIndexOrThrow(YEAR)));
            favorite.setPoster(favoritesCursor.getString(favoritesCursor.getColumnIndexOrThrow(POSTER)));
            favorite.setType(favoritesCursor.getString(favoritesCursor.getColumnIndexOrThrow(TYPE)));
            favoriteList.add(favorite);
        }
        return favoriteList;
    }
}
