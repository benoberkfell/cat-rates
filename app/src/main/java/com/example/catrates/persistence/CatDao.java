package com.example.catrates.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.catrates.models.Cat;

import java.util.List;

@Dao
public interface CatDao {

    @Query("SELECT * FROM cat")
    List<Cat> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Cat... cats);

    @Delete
    void delete(Cat cat);


}
