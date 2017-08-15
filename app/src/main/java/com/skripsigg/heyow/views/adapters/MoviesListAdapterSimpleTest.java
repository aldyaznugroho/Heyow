package com.skripsigg.heyow.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skripsigg.heyow.R;
import com.skripsigg.heyow.models.test.Movies;
import com.skripsigg.heyow.utils.interfaces.MoviesItemListClickListener;
import com.skripsigg.heyow.utils.others.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by Aldyaz on 12/10/2016.
 */

public class MoviesListAdapterSimpleTest extends RecyclerView.Adapter<MoviesListAdapterSimpleTest.MoviesHolder> {
    private Context context;
    private List<Movies> moviesDataList;
    private String matchDetailType;

    public MoviesListAdapterSimpleTest(Context context, List<Movies> moviesDataList, String matchDetailType) {
        this.context = context;
        this.moviesDataList = moviesDataList;
        this.matchDetailType = matchDetailType;
    }

    @Override
    public MoviesListAdapterSimpleTest.MoviesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View moviesView = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_view_holder_2, null);

        moviesView.setLayoutParams(new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT));

        return new MoviesListAdapterSimpleTest.MoviesHolder(moviesView);
    }

    @Override
    public void onBindViewHolder(MoviesListAdapterSimpleTest.MoviesHolder holder, int position) {
        String imagePath = moviesDataList.get(position).getPosterPath();
        String title = moviesDataList.get(position).getTitle();
        String releaseDate = moviesDataList.get(position).getReleaseDate();

        Glide.with(context)
                .load(Constants.MOVIES_POSTER_PATH_URL + imagePath)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(holder.posterImage);

        holder.movieTitle.setText(title);
        holder.movieYear.setText(releaseDate);

        holder.setCardItemClickListener(new MoviesItemListClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Action here if user clicking match item list
                Toast.makeText(context, moviesDataList.get(position).getTitle(), Toast.LENGTH_SHORT).show();

                /*Bundle matchDetailBundle = new Bundle();
                matchDetailBundle.putString(Constants.MATCH_DETAIL_TYPE, matchDetailType);

                Intent matchDetailIntent = new Intent(context, MatchDetailActivity.class);
                matchDetailIntent.putExtras(matchDetailBundle);

                context.startActivity(matchDetailIntent);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesDataList.size();
    }

    class MoviesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout itemListLayout;
        ImageView posterImage;
        TextView movieTitle, movieYear;

        MoviesItemListClickListener moviesItemListClickListener;

        private MoviesHolder(View itemView) {
            super(itemView);
            itemListLayout = (LinearLayout) itemView.findViewById(R.id.match_list_2_parent_layout);
            posterImage = (ImageView) itemView.findViewById(R.id.match_list_2_poster_image);
            movieTitle = (TextView) itemView.findViewById(R.id.match_list_2_title_text);
            movieYear = (TextView) itemView.findViewById(R.id.match_list_2_datetime_text);

            itemListLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            moviesItemListClickListener.onItemClick(view, getAdapterPosition());
        }

        public void setCardItemClickListener(MoviesItemListClickListener moviesItemListClickListener) {
            this.moviesItemListClickListener = moviesItemListClickListener;
        }
    }
}
