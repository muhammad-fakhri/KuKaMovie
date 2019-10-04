package id.cybershift.kukamovie.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import java.util.ArrayList;

import id.cybershift.kukamovie.DetailActivity;
import id.cybershift.kukamovie.MainActivity;
import id.cybershift.kukamovie.R;
import id.cybershift.kukamovie.adapter.TVShowAdapter;
import id.cybershift.kukamovie.entity.TVShow;
import id.cybershift.kukamovie.model.MainViewModel;

public class TVShowFragment extends Fragment {
    private ProgressBar progressBar;
    private TVShowAdapter tvShowAdapter;

    public TVShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Ganti title action bar
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.tvshow_page_title));

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvshow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar_tv);

        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getTVShows().observe(this, getTVShow);

        tvShowAdapter = new TVShowAdapter();
        tvShowAdapter.notifyDataSetChanged();

        RecyclerView recyclerView = view.findViewById(R.id.rv_tvshow);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(tvShowAdapter);

        mainViewModel.setTVShows();
        showLoading(true);

//        Pasang Click listener ke recycler view
        tvShowAdapter.setOnItemClickCallback(new TVShowAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(TVShow data) {
                //Intent activity detail movie
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_TVSHOW, data);
                startActivity(intent);
            }
        });
    }

    private Observer<ArrayList<TVShow>> getTVShow = new Observer<ArrayList<TVShow>>() {
        @Override
        public void onChanged(@Nullable ArrayList<TVShow> tvShows) {
            if (tvShows != null) {
                tvShowAdapter.setData(tvShows);
                showLoading(false);
            }
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}