package id.cybershift.kukamovie.adapter;

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

import id.cybershift.kukamovie.R;
import id.cybershift.kukamovie.entity.TVShow;

public class TVShowAdapter extends RecyclerView.Adapter<TVShowAdapter.TVShowViewHolder> {
    private ArrayList<TVShow> data = new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;

    public void setData(ArrayList<TVShow> items) {
        data.clear();
        data.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TVShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tvshow_items, viewGroup, false);
        return new TVShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowViewHolder tvShowViewHolder, int i) {
        tvShowViewHolder.bind(data.get(i), tvShowViewHolder);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TVShowViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvName, tvDescription;

        public TVShowViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.tvshow_poster);
            tvName = itemView.findViewById(R.id.tvshow_name);
            tvDescription = itemView.findViewById(R.id.tvshow_description);
        }

        void bind(final TVShow tvshowItem, TVShowViewHolder viewHolder) {
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
                    onItemClickCallback.onItemClicked(tvshowItem);
                }
            });
        }
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClicked(TVShow data);
    }
}
