package com.example.individualapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MovieDAO {
    @Query("select * from Movie")
    LiveData<List<Movie>> getAll();

    @Query("select * from Movie where id = :id")
    List<Movie> getById(int id);

    @Query("select max(id) from Movie")
    int getMaxId();

    @Query("select min(id) from Movie")
    int getMinId();

    @Query("delete from Movie")
    void deleteAll();

    @Update
    void update(Movie movie);

    @Delete
    void delete(Movie movie);

    @Insert
    void insert(Movie... movie);
}
