package id.cybershift.kukamovie.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import id.cybershift.kukamovie.R;
import id.cybershift.kukamovie.entity.Favorite;
import id.cybershift.kukamovie.helper.MappingHelper;

import static id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns.CONTENT_URI;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final List<Bitmap> widgetItems = new ArrayList<>();
    private final Context context;
    private ArrayList<Favorite> listFavorite = new ArrayList<>();

    StackRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();

        //Ambil data dari database
//        Cursor favoriteItem = favoriteHelper.getAllFavoriteItem();
        Cursor favoriteItem = context.getContentResolver().query(CONTENT_URI, null, null, null, null);
        //Konversi cursor ke array list
        ArrayList<Favorite> favorites = MappingHelper.mapFavoriteCursorToArrayList(favoriteItem);

        listFavorite.addAll(favorites);
        URL imageURL = null;
        for (int i = 0; i < listFavorite.size(); i++) {
            try {
                imageURL = new URL("https://image.tmdb.org/t/p/w185" + listFavorite.get(i).getPoster());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                widgetItems.add(BitmapFactory.decodeStream(imageURL.openConnection().getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return widgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        remoteViews.setImageViewBitmap(R.id.imageView, widgetItems.get(position));
        Bundle extras = new Bundle();
        extras.putInt(FavoriteWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        remoteViews.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
