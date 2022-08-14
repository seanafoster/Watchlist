package com.example.individualapp;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class AllMovieViewModel extends ViewModel {
    private LiveData<List<Movie>> movieList;

    public LiveData<List<Movie>> getCourseList(Context context) {
        if (movieList == null) {
            movieList = AppDatabase.getInstance(context).movieDAO().getAll();
        }
        return movieList;
    }
}
