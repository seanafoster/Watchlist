package com.example.individualapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.individualapp.AppDatabase;
import com.example.individualapp.Movie;
import com.example.individualapp.MovieDAO;
import com.example.individualapp.R;
import com.example.individualapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private View root;
    TextView txtLastTitle, txtLastDirector, txtLastYear, txtLastRated, txtLastRunTime, txtLastRating, txtLastPoster,
            txtFirstTitle, txtFirstDirector, txtFirstYear, txtFirstRated, txtFirstRunTime, txtFirstRating, txtFirstPoster;
    Movie firstMovie, lastMovie;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
        }

        try {
            setMovies(true);
        } catch (InterruptedException e) {
            Log.e("HomeFragment", e.getMessage());
        }

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

        try {
            setMovies(false);
        } catch (InterruptedException e) {
            Log.e("HomeFragment", e.getMessage());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setMovies(boolean delay) throws InterruptedException {
        txtLastTitle = root.findViewById(R.id.lastTitle);
        txtLastDirector = root.findViewById(R.id.lastDirector);
        txtLastYear = root.findViewById(R.id.lastYear);
        txtLastRated = root.findViewById(R.id.lastRated);
        txtLastRunTime = root.findViewById(R.id.lastRunTime);
        txtLastRating = root.findViewById(R.id.lastImdbRating);
        txtLastPoster = root.findViewById(R.id.lastPosterLink);

        txtFirstTitle = root.findViewById(R.id.firstTitle);
        txtFirstDirector = root.findViewById(R.id.firstDirector);
        txtFirstYear = root.findViewById(R.id.firstYear);
        txtFirstRated = root.findViewById(R.id.firstRated);
        txtFirstRunTime = root.findViewById(R.id.firstRunTime);
        txtFirstRating = root.findViewById(R.id.firstImdbRating);
        txtFirstPoster = root.findViewById(R.id.firstPosterLink);

        final int[] minId = new int[1];
        final int[] maxId = new int[1];

        new Thread(new Runnable() {
            @Override
            public void run() {
                MovieDAO dao = AppDatabase.getInstance(getContext()).movieDAO();
                minId[0] = dao.getMinId();
                maxId[0] = dao.getMaxId();
                if (minId[0] > 0) {
                    firstMovie = dao.getById(minId[0]).get(0);
                }
                if (maxId[0] > 0) {
                    lastMovie = dao.getById(maxId[0]).get(0);
                }

        }}).start();

        if (delay) {
            Thread.sleep(100);
        }

        if (firstMovie != null) {
            txtFirstTitle.setText(firstMovie.getTitle());
            txtFirstDirector.setText(firstMovie.getDirector());
            txtFirstYear.setText(firstMovie.getYear());
            txtFirstRated.setText(firstMovie.getRated());
            txtFirstRunTime.setText(firstMovie.getRuntime());
            txtFirstRating.setText(firstMovie.getImdbRating());
            txtFirstPoster.setText(firstMovie.getPoster());
        }

        if (lastMovie != null) {
            txtLastTitle.setText(lastMovie.getTitle());
            txtLastDirector.setText(lastMovie.getDirector());
            txtLastYear.setText(lastMovie.getYear());
            txtLastRated.setText(lastMovie.getRated());
            txtLastRunTime.setText(lastMovie.getRuntime());
            txtLastRating.setText(lastMovie.getImdbRating());
            txtLastPoster.setText(lastMovie.getPoster());
        }
    }
}