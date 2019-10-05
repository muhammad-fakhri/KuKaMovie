package id.cybershift.kukamovie.search;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import id.cybershift.kukamovie.R;
import id.cybershift.kukamovie.entity.Movie;
import id.cybershift.kukamovie.entity.TVShow;
import id.cybershift.kukamovie.model.SearchResultViewModel;

public class SearchResultActivity extends AppCompatActivity {
    public static final String EXTRA_QUERY = "extra_query";
    public static final String EXTRA_TYPE = "extra_type";
    ProgressBar progressBar;
    TextView txtNotFound;
    RecyclerView recyclerView;
    SearchAdapter searchAdapter;
    SearchResultViewModel searchResultViewModel;
    private Observer<ArrayList<Movie>> getMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Movie> movies) {
            showLoading(false);
            if (movies.size() > 0) {
                searchAdapter.setMovie(movies);
                showNotFound(false);
            } else {
                showNotFound(true);
            }
        }
    };
    private Observer<ArrayList<TVShow>> getTVShow = new Observer<ArrayList<TVShow>>() {
        @Override
        public void onChanged(@Nullable ArrayList<TVShow> tvshows) {
            showLoading(false);
            if (tvshows.size() > 0) {
                searchAdapter.setTVShow(tvshows);
                showNotFound(false);
            } else {
                showNotFound(true);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        recyclerView = findViewById(R.id.rv_search_result);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        String query = getIntent().getStringExtra(EXTRA_QUERY);
        boolean typeMovie = getIntent().getBooleanExtra(EXTRA_TYPE, true);

        progressBar = findViewById(R.id.progress_bar_search);
        txtNotFound = findViewById(R.id.txt_not_found);

        searchResultViewModel = ViewModelProviders.of(this).get(SearchResultViewModel.class);
        if (typeMovie) {
            searchResultViewModel.getMovies().observe(this, getMovie);
            searchResultViewModel.setResult(getApplicationContext(), true, query);
            showLoading(true);
            showNotFound(false);
        } else {
            searchResultViewModel.getTVShows().observe(this, getTVShow);
            searchResultViewModel.setResult(getApplicationContext(), false, query);
            showLoading(true);
            showNotFound(false);
        }

        searchAdapter = new SearchAdapter();
        searchAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(searchAdapter);

        if (getSupportActionBar() != null) {
            if (typeMovie) {
                getSupportActionBar().setTitle(R.string.search_result_movie);
            } else {
                getSupportActionBar().setTitle(R.string.search_result_tvshow);
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showNotFound(Boolean state) {
        if (state) {
            recyclerView.setVisibility(View.INVISIBLE);
            txtNotFound.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            txtNotFound.setVisibility(View.INVISIBLE);
        }
    }
}
