package id.cybershift.kukamovie.fragment;


import android.content.Intent;
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
import id.cybershift.kukamovie.db.FavoriteHelper;
import id.cybershift.kukamovie.entity.Favorite;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavoriteFragment extends Fragment {
    private FavoriteAdapter favoriteAdapter;
    private FavoriteHelper favoriteHelper;
    TextView emptyMovieFavorite;
    private static final String EXTRA_STATE_MOVIE = "EXTRA_STATE_MOVIE";


    public MovieFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Set Database Helper
        favoriteHelper = FavoriteHelper.getInstance(this.getContext());
        favoriteHelper.open();

        //Set Recycler View Adapter
        favoriteAdapter = new FavoriteAdapter();
        favoriteAdapter.notifyDataSetChanged();

        //Set Recyeler View
        RecyclerView recyclerView = view.findViewById(R.id.rv_movie_favorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(favoriteAdapter);

        //Set Empty Movie Favorite
        emptyMovieFavorite = view.findViewById(R.id.movie_favorite_empty);

        //Ambil data dari database
        ArrayList<Favorite> favorites = favoriteHelper.getAllFavoriteMovie();
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
            ArrayList<Favorite> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE_MOVIE);
            if (list != null) {
                favoriteAdapter.setListFavorites(list);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        ArrayList<Favorite> favorites = favoriteHelper.getAllFavoriteMovie();
        if (favorites.size() > 0) {
            showEmptyMovie(false);
        } else {
            showEmptyMovie(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        favoriteHelper.close();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE_MOVIE, favoriteAdapter.getListNotes());
    }

    private void showEmptyMovie(Boolean state) {
        if (state) {
            emptyMovieFavorite.setVisibility(View.VISIBLE);
        } else {
            emptyMovieFavorite.setVisibility(View.GONE);
        }
    }

}
