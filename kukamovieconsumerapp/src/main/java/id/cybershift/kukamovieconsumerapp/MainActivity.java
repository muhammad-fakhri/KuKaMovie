package id.cybershift.kukamovieconsumerapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set Bahasa
        String lang = Locale.getDefault().getLanguage();
        String title1, title2;
        if (lang.equals("in")) {
            title1 = "Film Favorit";
            title2 = "Acara TV Favorit";
        } else {
            title1 = "Favorite Movie";
            title2 = "Favorite TV Show";
        }

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(title1));
        tabLayout.addTab(tabLayout.newTab().setText(title2));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            MovieFavoriteFragment movieFavoriteFragment = new MovieFavoriteFragment();
            TVShowFavoriteFragment tvShowFavoriteFragment = new TVShowFavoriteFragment();

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                if (tab.getText() == "Favorite Movie" || tab.getText() == "Film Favorit") {
                    fragmentTransaction.replace(R.id.favorite_child_fragment, movieFavoriteFragment);
                } else if (tab.getText() == "Favorite TV Show" || tab.getText() == "Acara TV Favorit") {
                    fragmentTransaction.replace(R.id.favorite_child_fragment, tvShowFavoriteFragment);
                }
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.favorite_child_fragment, movieFavoriteFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        tabLayout.getTabAt(0).select();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
