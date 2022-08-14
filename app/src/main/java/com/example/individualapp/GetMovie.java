package com.example.individualapp;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetMovie extends AsyncTask<String, Integer, String> {
    private String rawJSON;
    private String title = null;
    private String year = null;

    private OnMovieListImport listener;

    public interface OnMovieListImport {
        void movieList(Movie[] movies);
    }

    public void setOnMovieListImportListener(OnMovieListImport listenerFromMain, String title, String year) {
        listener = listenerFromMain;
        this.title = title;
        this.year = year;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(buildUrl(title, year));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            int status = connection.getResponseCode();
            switch (status){
                case 200:
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    rawJSON = bufferedReader.readLine();

                    Log.d("GetMovie", "doInBackground: rawJSON " + rawJSON);
                    break;
                default:
                    Log.d("GetMovie", "doInBackground: Status Code " + status);
                    break;
            }
        } catch (Exception e) {
            Log.d("GetMovie", "doInBackground: error  " + e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        Movie[] movies;
        try {
            movies = parseJson();
            listener.movieList(movies);
        } catch (Exception e) {
            Log.d("GetMovie", "onPostExecute: error  " + e);
        }
        super.onPostExecute(s);
    }

    private String buildUrl(String title, String year){
        String url = "http://www.omdbapi.com/?";
        url = url + "apikey=" + Authorization.omdbApiKey;
        String titleFormat = title.replace(" ", "+");
        url = url + "&t=" + titleFormat;
        if (year != null) {
            url = url + "&y=" + year;
        }
        return url;
    }

    private Movie[] parseJson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Movie[] movies = null;
        try {
            movies = gson.fromJson(rawJSON, Movie[].class);
        } catch (Exception e) {
            Log.d("GetMovies", "parseJson: error  " + e);
            try {
                Movie movie = gson.fromJson(rawJSON, Movie.class);
                movies = new Movie[] { movie };
            } catch (Exception e2) {
                Log.d("GetMovies", "parseJson: error  " + e2);
            }
        }
        return movies;
    }
}
