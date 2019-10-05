package id.cybershift.kukamovieconsumerapp;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import id.cybershift.kukamovieconsumerapp.adapter.FavoriteAdapter;
import id.cybershift.kukamovieconsumerapp.entity.Favorite;
import id.cybershift.kukamovieconsumerapp.helper.MappingHelper;

import static id.cybershift.kukamovieconsumerapp.db.DatabaseContract.FavoriteColumns.CONTENT_URI;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavoriteFragment extends Fragment {
    private FavoriteAdapter favoriteAdapter;
    TextView emptyMovieFavorite;
    private static final String EXTRA_STATE_MOVIE = "EXTRA_STATE_MOVIE";
    Uri uriForMovieFavorite;

    public MovieFavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        uriForMovieFavorite = Uri.parse(CONTENT_URI + "/movie");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Set Recycler View Adapter
        favoriteAdapter = new FavoriteAdapter();
        favoriteAdapter.notifyDataSetChanged();

        //Set Recyeler View
        RecyclerView recyclerView = view.findViewById(R.id.rv_movie_favorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(favoriteAdapter);

        //Set Empty Movie Favorite
        emptyMovieFavorite = view.findViewById(R.id.movie_favorite_empty);

        //Proses Ambil Data dari database content provider
        Cursor favoriteMovieDataCursor = getActivity()
                .getContentResolver()
                .query(uriForMovieFavorite, null, null, null, null);
        //Konversi cursor ke array list
        ArrayList<Favorite> favorites = MappingHelper.mapFavoriteCursorToArrayList(favoriteMovieDataCursor);
        favoriteAdapter.setListFavorites(favorites);

        if (savedInstanceState != null) {
            ArrayList<Favorite> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE_MOVIE);
            if (list != null) {
                favoriteAdapter.setListFavorites(list);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        //Ambil lagi data dari content provider
        Cursor favoriteMovieDataCursor = getActivity()
                .getContentResolver()
                .query(uriForMovieFavorite, null, null, null, null);
        //Konversi cursor ke array list
        ArrayList<Favorite> favorites = MappingHelper.mapFavoriteCursorToArrayList(favoriteMovieDataCursor);
        if (favorites.size() > 0) {
            showEmptyMovie(false);
        } else {
            showEmptyMovie(true);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE_MOVIE, favoriteAdapter.getListFavorites());
    }

    private void showEmptyMovie(Boolean state) {
        if (state) {
            emptyMovieFavorite.setVisibility(View.VISIBLE);
        } else {
            emptyMovieFavorite.setVisibility(View.GONE);
        }
    }
}
