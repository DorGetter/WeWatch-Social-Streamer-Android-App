package com.example.wewatchapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wewatchapp.Model.GetVideoDetails;
import com.example.wewatchapp.Model.MoviesItemClickListenerNew;
import com.example.wewatchapp.R;

import java.util.List;

public class MoviesShowAdapter extends RecyclerView.Adapter<MoviesShowAdapter.MyViewHolder> {

    //import com.google.firebase.database.core.Context;
    private Context mContext;
    private List<GetVideoDetails> uploads;

    MoviesItemClickListenerNew moviesItemClickListenerNew;

    public MoviesShowAdapter(Context mContext, List<GetVideoDetails> uploads,
                             MoviesItemClickListenerNew moviesItemClickListenerNew) {
        this.mContext = mContext;
        this.uploads = uploads;
        this.moviesItemClickListenerNew = moviesItemClickListenerNew;
    }





    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.movie_item_new,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesShowAdapter.MyViewHolder holder, int position) {

        GetVideoDetails getVideoDetails = uploads.get(position);
        holder.tvTitle.setText(getVideoDetails.getVideo_name());
        Glide.with(mContext).load(getVideoDetails.getVideo_thum()).into(holder.ImgMovie);

    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView ImgMovie;
        ConstraintLayout container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.item_movies_title);
            ImgMovie = itemView.findViewById(R.id.item_movies_img);
            container = itemView.findViewById(R.id.container);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moviesItemClickListenerNew.onMovieClick(uploads.get(getAdapterPosition()),ImgMovie);
                }
            });
        }
    }
}
