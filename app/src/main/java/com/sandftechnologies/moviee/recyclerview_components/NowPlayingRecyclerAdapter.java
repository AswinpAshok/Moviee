package com.sandftechnologies.moviee.recyclerview_components;

import android.net.Uri;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sandftechnologies.moviee.Constants;
import com.sandftechnologies.moviee.R;
import com.sandftechnologies.moviee.bottom_sheets.MovieSheet;
import com.sandftechnologies.moviee.listeners.MovieClickListener;
import com.sandftechnologies.moviee.models.low_tier_models.Movie;

import java.util.List;

/**
 * Created by pc4 on 12/29/2017.
 */

public class NowPlayingRecyclerAdapter extends RecyclerView.Adapter<NowPlayingRecyclerAdapter.MyViewHolder> implements Constants{

    private List<Movie> movieList;
    private MovieClickListener listener;

    public NowPlayingRecyclerAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

     public void addMore(List<Movie> movies){
        int prevSize=getItemCount();
        movieList.addAll(movies);
        notifyItemRangeInserted(prevSize,getItemCount());
    }

    public void setMovieClickListener(MovieClickListener listener){
         this.listener=listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.now_playing_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Uri imageUri = Uri.parse(IMAGE_BASE_URL+"/w780"+movieList.get(position).getWideImage());
        holder.movieName.setText(movieList.get(position).getName());
        holder.movieGraphic.setImageURI(imageUri);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               listener.OnMovieClicked(movieList.get(position).getName(),movieList.get(position).getOverview(),IMAGE_BASE_URL+"/w500"+movieList.get(position).getPoster());
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder{

        protected SimpleDraweeView movieGraphic;
        protected TextView movieName;
        protected CardView root;

        public MyViewHolder(View itemView) {
            super(itemView);
            movieGraphic=itemView.findViewById(R.id.movie_graphic);
            movieName=itemView.findViewById(R.id.movie_name);
            root=itemView.findViewById(R.id.root);
        }
    }
}
