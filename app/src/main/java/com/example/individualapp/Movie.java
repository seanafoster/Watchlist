package com.example.individualapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Movie {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String Title;

    private String Director;

    private String Year;

    private String Rated;

    private String Runtime;

    private String imdbRating;

    private String Poster;

    public Movie() { }

    public Movie(String title, String director, String year, String rated, String run_time, String imdb_rating, String poster_link) {
        this.Title = title;
        this.Director = director;
        this.Year = year;
        this.Rated = rated;
        this.Runtime = run_time;
        this.imdbRating = imdb_rating;
        this.Poster = poster_link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        this.Director = director;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        this.Year = year;
    }

    public String getRated() {
        return Rated;
    }

    public void setRated(String rated) {
        this.Rated = rated;
    }

    public String getRuntime() {
        return Runtime;
    }

    public void setRuntime(String runtime) {
        this.Runtime = runtime;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        this.Poster = poster;
    }
}
