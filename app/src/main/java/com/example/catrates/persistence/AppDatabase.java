package com.example.catrates.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.catrates.models.Cat;

@Database(entities = {Cat.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CatDao catDao();

}
