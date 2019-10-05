package id.cybershift.kukamovie.fragment;


import android.content.Intent;
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

import id.cybershift.kukamovie.DetailActivity;
import id.cybershift.kukamovie.R;
import id.cybershift.kukamovie.adapter.FavoriteAdapter;
import id.cybershift.kukamovie.entity.Favorite;
import id.cybershift.kukamovie.helper.MappingHelper;

import static id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowFavoriteFragment extends Fragment {
    private FavoriteAdapter favoriteAdapter;
    TextView emptyTVShowFavorite;
    private static final String EXTRA_STATE_TV = "EXTRA_STATE_TV";
    Uri uriForTVShowFavorite;

    public TVShowFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        uriForTVShowFavorite = Uri.parse(CONTENT_URI + "/tvshow");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvshow_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Set Recycler View Adapter
        favoriteAdapter = new FavoriteAdapter();
        favoriteAdapter.notifyDataSetChanged();

        //Set Recyeler View
        RecyclerView recyclerView = view.findViewById(R.id.rv_tvshow_favorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(favoriteAdapter);

        //Set Empty Movie Favorite
        emptyTVShowFavorite = view.findViewById(R.id.tvshow_favorite_empty);

        //Ambil data dari database
        Cursor favoriteTVShowDataCursor = getActivity()
                .getContentResolver()
                .query(uriForTVShowFavorite, null, null, null, null);
        //Konversi cursor ke array list
        ArrayList<Favorite> favorites = MappingHelper.mapFavoriteCursorToArrayList(favoriteTVShowDataCursor);
        favoriteAdapter.setListFavorites(favorites);

        //Pasang Click listener ke recycler view
        favoriteAdapter.setOnItemClickCallback(new FavoriteAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Favorite data) {
                //Intent activity detail favorite item
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_FAVORITE, data);
                startActivity(intent);
            }
        });

        if (savedInstanceState != null) {
            ArrayList<Favorite> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE_TV);
            if (list != null) {
                favoriteAdapter.setListFavorites(list);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Cursor favoriteTVShowDataCursor = getActivity()
                .getContentResolver()
                .query(uriForTVShowFavorite, null, null, null, null);
        //Konversi cursor ke array list
        ArrayList<Favorite> favorites = MappingHelper.mapFavoriteCursorToArrayList(favoriteTVShowDataCursor);
        if (favorites.size() > 0) {
            showEmptyTVShow(false);
        } else {
            showEmptyTVShow(true);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE_TV, favoriteAdapter.getListFavorites());
    }

    private void showEmptyTVShow(Boolean state) {
        if (state) {
            emptyTVShowFavorite.setVisibility(View.VISIBLE);
        } else {
            emptyTVShowFavorite.setVisibility(View.GONE);
        }
    }
}
