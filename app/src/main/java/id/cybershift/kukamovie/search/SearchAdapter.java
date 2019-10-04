package id.cybershift.kukamovie.search;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import id.cybershift.kukamovie.DetailActivity;
import id.cybershift.kukamovie.R;
import id.cybershift.kukamovie.entity.Movie;
import id.cybershift.kukamovie.entity.TVShow;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private ArrayList<Movie> listMovie = new ArrayList<>();
    private ArrayList<TVShow> listTVShow = new ArrayList<>();

    public void setMovie(ArrayList<Movie> items) {
        listMovie.clear();
        listMovie.addAll(items);
        notifyDataSetChanged();
    }

    public void setTVShow(ArrayList<TVShow> items) {
        listTVShow.clear();
        listTVShow.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_items, viewGroup, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, int i) {
        if (listMovie.size() > 0) {
            searchViewHolder.bindMovie(listMovie.get(i), searchViewHolder);
        } else if (listTVShow.size() > 0) {
            searchViewHolder.bindTVShow(listTVShow.get(i), searchViewHolder);
        }
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (listMovie.size() > 0) {
            size = listMovie.size();
        } else if (listTVShow.size() > 0) {
            size = listTVShow.size();
        }
        return size;
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvName, tvDescription;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.search_poster);
            tvName = itemView.findViewById(R.id.search_name);
            tvDescription = itemView.findViewById(R.id.search_description);
        }

        void bindMovie(final Movie movieItem, final SearchViewHolder viewHolder) {
            Glide.with(viewHolder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w185" + movieItem.getPoster())
                    .apply(new RequestOptions().override(200, 250))
                    .into(viewHolder.imgPoster);
            tvName.setText(movieItem.getTitle());
            tvDescription.setText(movieItem.getOverview());

            //Set click listener
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(viewHolder.itemView.getContext(), DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_MOVIE, movieItem);
                    viewHolder.itemView.getContext().startActivity(intent);
                }
            });
        }

        void bindTVShow(final TVShow tvshowItem, final SearchViewHolder viewHolder) {
            Glide.with(viewHolder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w185" + tvshowItem.getPoster())
                    .apply(new RequestOptions().override(200, 250))
                    .into(viewHolder.imgPoster);
            tvName.setText(tvshowItem.getTitle());
            tvDescription.setText(tvshowItem.getOverview());

            //Set click listener
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(viewHolder.itemView.getContext(), DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_TVSHOW, tvshowItem);
                    viewHolder.itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
