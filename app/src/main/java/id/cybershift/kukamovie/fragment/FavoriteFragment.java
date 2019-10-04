package id.cybershift.kukamovie.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import java.util.Locale;

import id.cybershift.kukamovie.MainActivity;
import id.cybershift.kukamovie.R;

public class FavoriteFragment extends Fragment {
    TabLayout tabLayout;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        //Select default tab
        tabLayout.getTabAt(0).select();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        //Ganti title action bar
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.favorite_page_title));

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

        //Set TabLayout
        tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(title1));
        tabLayout.addTab(tabLayout.newTab().setText(title2));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            MovieFavoriteFragment movieFavoriteFragment = new MovieFavoriteFragment();
            TVShowFavoriteFragment tvShowFavoriteFragment = new TVShowFavoriteFragment();

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
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
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.favorite_child_fragment, movieFavoriteFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}