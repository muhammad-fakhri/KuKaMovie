package id.cybershift.kukamovie;

import android.content.ContentValues;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import id.cybershift.kukamovie.db.FavoriteHelper;
import id.cybershift.kukamovie.entity.Favorite;
import id.cybershift.kukamovie.entity.Movie;
import id.cybershift.kukamovie.entity.TVShow;

import static id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns.CONTENT_URI;
import static id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns.ID_FROM_API;
import static id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns.OVERVIEW;
import static id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns.POSTER;
import static id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns.RATE;
import static id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns.TITLE;
import static id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns.TYPE;
import static id.cybershift.kukamovie.db.DatabaseContract.FavoriteColumns.YEAR;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_FAVORITE = "extra_favorite";
    public static final String EXTRA_TVSHOW = "extra_tvshow";
    ImageView detailPoster;
    TextView detailName, detailYear, detailRate, detailDescription;
    ProgressBar progressBar;
    String title, overview, year, poster, type;
    int idFromAPI, id;
    double rate;
    private FavoriteHelper favoriteHelper;
    private Uri uriWithId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());
        favoriteHelper.open();

        //Casting View
        detailPoster = findViewById(R.id.detail_poster);
        detailName = findViewById(R.id.detail_name);
        detailRate = findViewById(R.id.detail_rate);
        detailYear = findViewById(R.id.detail_year);
        detailDescription = findViewById(R.id.detail_description);
        progressBar = findViewById(R.id.detail_progress);

        //Display Loading
        showLoading(true);

        //Ambil data dari parceable
        if (getIntent().getParcelableExtra(EXTRA_MOVIE) != null) {
            Movie data_movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

            uriWithId = Uri.parse(CONTENT_URI + "/" + data_movie.getId());

            //Ambil Data Detail Filmnya
            idFromAPI = data_movie.getId();
            title = data_movie.getTitle();
            overview = data_movie.getOverview();
            year = data_movie.getYear();
            poster = data_movie.getPoster();
            rate = data_movie.getRate();
            type = "movie";

            //Pasang data ke view
            detailName.setText(data_movie.getTitle());
            detailYear.setText(data_movie.getYear());
            detailRate.setText(String.valueOf(data_movie.getRate()));
            detailDescription.setText(data_movie.getOverview());
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w185" + data_movie.getPoster())
                    .apply(new RequestOptions().override(200, 250))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            showLoading(false);
                            return false;
                        }
                    })
                    .into(detailPoster);

            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle("Movie Detail");
            }
        } else if (getIntent().getParcelableExtra(EXTRA_TVSHOW) != null) {
            TVShow data_tvshow = getIntent().getParcelableExtra(EXTRA_TVSHOW);

            uriWithId = Uri.parse(CONTENT_URI + "/" + data_tvshow.getId());

            //Ambil Data Detail Acara TV nya
            idFromAPI = data_tvshow.getId();
            title = data_tvshow.getTitle();
            overview = data_tvshow.getOverview();
            year = data_tvshow.getYear();
            poster = data_tvshow.getPoster();
            rate = data_tvshow.getRate();
            type = "tv";

            //Pasang data ke view
            detailName.setText(data_tvshow.getTitle());
            detailYear.setText(data_tvshow.getYear());
            detailRate.setText(String.valueOf(data_tvshow.getRate()));
            detailDescription.setText(data_tvshow.getOverview());
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w185" + data_tvshow.getPoster())
                    .apply(new RequestOptions().override(200, 250))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            showLoading(false);
                            return false;
                        }
                    })
                    .into(detailPoster);

            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle("TV Show Detail");
            }
        } else if (getIntent().getParcelableExtra(EXTRA_FAVORITE) != null) {
            Favorite data_favorite = getIntent().getParcelableExtra(EXTRA_FAVORITE);

            uriWithId = Uri.parse(CONTENT_URI + "/" + data_favorite.getIdFromAPI());

            //Ambil Data Detail Favorite nya
            id = data_favorite.getId();
            idFromAPI = data_favorite.getIdFromAPI();
            title = data_favorite.getTitle();
            overview = data_favorite.getOverview();
            year = data_favorite.getYear();
            poster = data_favorite.getPoster();
            rate = data_favorite.getRate();
            type = "favorite";

            //Pasang data ke view
            detailName.setText(data_favorite.getTitle());
            detailYear.setText(data_favorite.getYear());
            detailRate.setText(String.valueOf(data_favorite.getRate()));
            detailDescription.setText(data_favorite.getOverview());
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w185" + data_favorite.getPoster())
                    .apply(new RequestOptions().override(200, 250))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            showLoading(false);
                            return false;
                        }
                    })
                    .into(detailPoster);

            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle("Favorite Detail");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        favoriteHelper.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean exist = favoriteHelper.checkExistOrNot(idFromAPI);
        if (exist) {
            getMenuInflater().inflate(R.menu.already_favorite_menu, menu);
        } else {
            getMenuInflater().inflate(R.menu.favorite_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.action_favorite) {
            boolean exist = favoriteHelper.checkExistOrNot(idFromAPI);
            if (exist) {
                long result = getContentResolver().delete(uriWithId, null, null);
//                long result = favoriteHelper.deleteFavorite(idFromAPI);
                if (result > 0) {
                    Toast.makeText(DetailActivity.this, getString(R.string.unfavorite_toast_success), Toast.LENGTH_SHORT).show();
                    item.setIcon(getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
                } else {
                    Toast.makeText(DetailActivity.this, getString(R.string.unfavorite_toast_fail), Toast.LENGTH_SHORT).show();
                }
            } else {
                ContentValues values = new ContentValues();
                values.put(ID_FROM_API, idFromAPI);
                values.put(TITLE, title);
                values.put(RATE, rate);
                values.put(POSTER, poster);
                values.put(YEAR, year);
                values.put(OVERVIEW, overview);
                values.put(TYPE, type);
                Uri nice = getContentResolver().insert(CONTENT_URI, values);
                String res = nice.getLastPathSegment();
                int result = Integer.parseInt(res);
                if (result > 0) {
                    Toast.makeText(DetailActivity.this, getString(R.string.favorite_toast_success), Toast.LENGTH_SHORT).show();
                    item.setIcon(getResources().getDrawable(R.drawable.ic_favorite_white_24dp));
                } else {
                    Toast.makeText(DetailActivity.this, getString(R.string.favorite_toast_fail), Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
