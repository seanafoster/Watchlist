package com.example.individualapp.ui.findTrack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.individualapp.AppDatabase;
import com.example.individualapp.GetMovie;
import com.example.individualapp.MovieDAO;
import com.example.individualapp.R;
import com.example.individualapp.Movie;
import com.example.individualapp.databinding.FragmentFindmovieBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FindMovieFragment extends Fragment {

    private FragmentFindmovieBinding binding;
    private Movie movie;
    private View root;
    private TextView txtTitle, txtDirector, txtYear, txtRated, txtRunTime, txtRating, txtPoster, txtMessage;
    private TextInputEditText searchTitle, searchYear;
    private Button btnSearch, btnSave;
    private GetMovie task;
    private Movie lastMovie;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFindmovieBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
        }

        txtTitle = root.findViewById(R.id.staticTitle);
        txtDirector = root.findViewById(R.id.staticDirector);
        txtYear = root.findViewById(R.id.staticYear);
        txtRated = root.findViewById(R.id.staticRated);
        txtRunTime = root.findViewById(R.id.staticRunTime);
        txtRating = root.findViewById(R.id.staticImdbRating);
        txtPoster = root.findViewById(R.id.staticPosterLink);
        txtMessage = root.findViewById(R.id.txt_Message);

        searchTitle = root.findViewById(R.id.textMovieTitle);
        searchYear = root.findViewById(R.id.textYear);

        btnSearch = root.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtMessage.setText("");
                task = new GetMovie();
                task.setOnMovieListImportListener(new GetMovie.OnMovieListImport() {
                    @Override
                    public void movieList(Movie[] movies) {
                        final ArrayList<Movie> movieList = new ArrayList<>();
                        if (movies.length > 0) {
                            lastMovie = movies[0];
                            txtTitle.setText(lastMovie.getTitle());
                            txtDirector.setText(lastMovie.getDirector());
                            txtYear.setText(lastMovie.getYear());
                            txtRated.setText(lastMovie.getRated());
                            txtRunTime.setText(lastMovie.getRuntime());
                            txtRating.setText(lastMovie.getImdbRating());
                            txtPoster.setText(lastMovie.getPoster());
                        }
                    }
                }, String.valueOf(searchTitle.getText()), String.valueOf(searchYear.getText()));
                task.execute("");
            }
        });

        btnSave = root.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastMovie != null && !Objects.equals(lastMovie.getTitle(), "")) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MovieDAO dao = AppDatabase.getInstance(getContext()).movieDAO();
                            dao.insert(lastMovie);
                        }
                    }).start();

                    searchTitle.setText("");
                    searchYear.setText("");
                    txtMessage.setText(R.string.movie_saved);
                }
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}