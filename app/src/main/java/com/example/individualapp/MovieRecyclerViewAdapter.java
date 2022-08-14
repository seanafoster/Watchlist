package com.example.individualapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder>{

    public List<Movie> movies;

    public MovieRecyclerViewAdapter(List<Movie> movies) { this.movies = movies; }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Movie movie = movies.get(position);
        if (movie != null) {
            holder.txtTitle.setText(movie.getTitle());
            holder.txtDirector.setText(movie.getDirector());
            holder.txtYear.setText(movie.getYear());
            holder.txtRated.setText(movie.getRated());
            holder.txtRunTime.setText(movie.getRuntime());
            holder.txtRating.setText(movie.getImdbRating());
            holder.txtPoster.setText(movie.getPoster());

            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MovieDAO dao = AppDatabase.getInstance(view.getContext()).movieDAO();
                            dao.delete(movie);
                        }
                    }).start();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void addItems(List<Movie> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public Movie movie;
        public TextView txtTitle, txtDirector, txtYear, txtRated, txtRunTime, txtRating, txtPoster;
        public Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            txtTitle = view.findViewById(R.id.rTitle);
            txtDirector = view.findViewById(R.id.rDirector);
            txtYear = view.findViewById(R.id.rYear);
            txtRated = view.findViewById(R.id.rRated);
            txtRunTime = view.findViewById(R.id.rRunTime);
            txtRating = view.findViewById(R.id.rImdbRating);
            txtPoster = view.findViewById(R.id.rPosterLink);
            btnDelete = view.findViewById(R.id.btnDelete);
        }
    }
}

