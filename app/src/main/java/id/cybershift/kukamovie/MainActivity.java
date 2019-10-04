package id.cybershift.kukamovie;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import id.cybershift.kukamovie.db.FavoriteHelper;
import id.cybershift.kukamovie.fragment.FavoriteFragment;
import id.cybershift.kukamovie.fragment.MovieFragment;
import id.cybershift.kukamovie.fragment.TVShowFragment;
import id.cybershift.kukamovie.search.SearchResultActivity;
import id.cybershift.kukamovie.setting.SettingActivity;

import static id.cybershift.kukamovie.search.SearchResultActivity.EXTRA_QUERY;
import static id.cybershift.kukamovie.search.SearchResultActivity.EXTRA_TYPE;

public class MainActivity extends AppCompatActivity {
    boolean typeMovie = true;
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            MovieFragment movieFragment = new MovieFragment();
            TVShowFragment tvShowFragment = new TVShowFragment();
            FavoriteFragment favoriteFragment = new FavoriteFragment();
            Fragment fragment;

            switch (menuItem.getItemId()) {
                case R.id.navigation_movie:
                    typeMovie = true;
                    fragment = fragmentManager.findFragmentByTag(MovieFragment.class.getSimpleName());
                    if (!(fragment instanceof MovieFragment)) {
                        fragmentTransaction.replace(R.id.container_layout, movieFragment, MovieFragment.class.getSimpleName());
                        fragmentTransaction.commit();
                    }
                    return true;
                case R.id.navigation_tvshow:
                    typeMovie = false;
                    fragment = fragmentManager.findFragmentByTag(TVShowFragment.class.getSimpleName());
                    if (!(fragment instanceof TVShowFragment)) {
                        fragmentTransaction.replace(R.id.container_layout, tvShowFragment, TVShowFragment.class.getSimpleName());
                        fragmentTransaction.commit();
                    }
                    return true;
                case R.id.navigation_favorite:
                    typeMovie = true;
                    fragment = fragmentManager.findFragmentByTag(FavoriteFragment.class.getSimpleName());
                    if (!(fragment instanceof FavoriteFragment)) {
                        fragmentTransaction.replace(R.id.container_layout, favoriteFragment, FavoriteFragment.class.getSimpleName());
                        fragmentTransaction.commit();
                    }
                    return true;
                default:
                    return false;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FavoriteHelper helper = FavoriteHelper.getInstance(getApplicationContext());
        helper.open();

        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            navigationView.setSelectedItemId(R.id.navigation_movie);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        final MenuItem searchMenuItem = menu.findItem(R.id.menu_search);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
                intent.putExtra(EXTRA_QUERY, s);
                intent.putExtra(EXTRA_TYPE, typeMovie);
                startActivity(intent);
                searchMenuItem.getIcon().setVisible(false, false);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_setting) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setSubtitle(title);
        }
    }
}
