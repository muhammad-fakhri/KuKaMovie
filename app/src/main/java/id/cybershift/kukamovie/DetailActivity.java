package id.cybershift.kukamovie;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_FAVORITE = "extra_favorite";
    public static final String EXTRA_TVSHOW = "extra_tvshow";
    private FavoriteHelper favoriteHelper;
    ImageView detailPoster;
    TextView detailName, detailYear, detailRate, detailDescription;
    ProgressBar progressBar;
    String title, overview, year, poster, type;
    int idFromAPI, id;
    double rate;

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
            Movie data = getIntent().getParcelableExtra(EXTRA_MOVIE);

            //Ambil Data Detail Filmnya
            idFromAPI = data.getId();
            title = data.getTitle();
            overview = data.getOverview();
            year = data.getYear();
            poster = data.getPoster();
            rate = data.getRate();
            type = "movie";

            //Pasang data ke view
            detailName.setText(data.getTitle());
            detailYear.setText(data.getYear());
            detailRate.setText(String.valueOf(data.getRate()));
            detailDescription.setText(data.getOverview());
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w185" + data.getPoster())
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
        favoriteHelper.close();
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
                long result = favoriteHelper.deleteFavorite(idFromAPI);
                if (result > 0) {
                    Toast.makeText(DetailActivity.this, getString(R.string.unfavorite_toast_success), Toast.LENGTH_SHORT).show();
                    item.setIcon(getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
                } else {
                    Toast.makeText(DetailActivity.this, getString(R.string.unfavorite_toast_fail), Toast.LENGTH_SHORT).show();
                }
            } else {
                Favorite favorite = new Favorite();
                favorite.setIdFromAPI(idFromAPI);
                favorite.setTitle(title);
                favorite.setRate(rate);
                favorite.setPoster(poster);
                favorite.setYear(year);
                favorite.setOverview(overview);
                favorite.setType(type);

                long result = favoriteHelper.insertFavorite(favorite);
                if (result > 0) {
                    Toast.makeText(DetailActivity.this, getString(R.string.favorite_toast_success), Toast.LENGTH_SHORT).show();
                    item.setIcon(getResources().getDrawable(R.drawable.ic_favorite_white_24dp));
                } else {
                    Toast.makeText(DetailActivity.this, getString(R.string.favorite_toast_fail), Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        }
        else {
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
