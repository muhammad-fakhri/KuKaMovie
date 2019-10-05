package id.cybershift.kukamovie.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import id.cybershift.kukamovie.db.FavoriteHelper;

import static id.cybershift.kukamovie.db.DatabaseContract.AUTHORITY;
import static id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns.CONTENT_URI;
import static id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns.TABLE_FAVORITE;

public class FavoriteProvider extends ContentProvider {
    private static final int FAVORITE = 1;
    private static final int FAVORITE_ID = 2;
    private static final int FAVORITE_MOVIE = 3;
    private static final int FAVORITE_TVSHOW = 4;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // content://id.cybershift.kukamovie/favorite
        sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITE, FAVORITE);
        // content://id.cybershift.kukamovie/favorite/id
        sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITE + "/#", FAVORITE_ID);
        // content://id.cybershift.kukamovie/favorite/movie
        sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITE + "/movie", FAVORITE_MOVIE);
        // content://id.cybershift.kukamovie/favorite/tvshow
        sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITE + "/tvshow", FAVORITE_TVSHOW);
    }

    private FavoriteHelper favoriteHelper;

    public FavoriteProvider() {
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        favoriteHelper = FavoriteHelper.getInstance(getContext());
        favoriteHelper.open();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case FAVORITE:
                cursor = favoriteHelper.getAllFavoriteItem();
                break;
            case FAVORITE_ID:
                cursor = favoriteHelper.getFavoriteItemById(uri.getLastPathSegment());
                break;
            case FAVORITE_MOVIE:
                cursor = favoriteHelper.getAllFavoriteMovie();
                break;
            case FAVORITE_TVSHOW:
                cursor = favoriteHelper.getAllFavoriteTVShow();
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insertFavorite a new row.
        long added;
        switch (sUriMatcher.match(uri)) {
            case FAVORITE:
                added = favoriteHelper.insertFavorite(values);
                break;
            default:
                added = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
//        int updated;
//        switch (sUriMatcher.match(uri)) {
//            case FAVORITE_ID:
//                updated = favoriteHelper.update(uri.getLastPathSegment(), values);
//                break;
//            default:
//                updated = 0;
//                break;
//        }
//        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
//        return updated;
        return 0;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case FAVORITE_ID:
                deleted = favoriteHelper.deleteFavorite(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
        return deleted;
    }
}
