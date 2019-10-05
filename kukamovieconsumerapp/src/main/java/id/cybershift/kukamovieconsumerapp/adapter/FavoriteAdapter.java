package id.cybershift.kukamovieconsumerapp.adapter;

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

import id.cybershift.kukamovieconsumerapp.R;
import id.cybershift.kukamovieconsumerapp.entity.Favorite;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private ArrayList<Favorite> data = new ArrayList<>();
//    private FavoriteAdapter.OnItemClickCallback onItemClickCallback;

    public ArrayList<Favorite> getListFavorites() {
        return data;
    }

    public void setListFavorites(ArrayList<Favorite> listFavorites) {
        if (listFavorites.size() > 0) {
            this.data.clear();
        }
        this.data.addAll(listFavorites);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.favorite_items, viewGroup, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder favoriteViewHolder, int i) {
        favoriteViewHolder.bind(data.get(i), favoriteViewHolder);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

//    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
//        this.onItemClickCallback = onItemClickCallback;
//    }

//    public interface OnItemClickCallback {
//        void onItemClicked(Favorite data);
//    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvName, tvDescription;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.favorite_poster);
            tvName = itemView.findViewById(R.id.favorite_name);
            tvDescription = itemView.findViewById(R.id.favorite_description);
        }

        void bind(final Favorite favoriteItem, FavoriteViewHolder viewHolder) {
            Glide.with(viewHolder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w185" + favoriteItem.getPoster())
                    .apply(new RequestOptions().override(200, 250))
                    .into(viewHolder.imgPoster);
            tvName.setText(favoriteItem.getTitle());
            tvDescription.setText(favoriteItem.getOverview());

//            //Set click listener
//            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onItemClickCallback.onItemClicked(favoriteItem);
//                }
//            });
        }
    }
}
