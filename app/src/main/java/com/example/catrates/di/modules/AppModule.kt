package com.example.catrates.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import android.content.SharedPreferences
import com.example.catrates.persistence.AppDatabase
import com.example.catrates.persistence.CatDao
import com.example.catrates.CatRatesApplication
import com.example.catrates.annotations.StoreCacheDir
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Singleton

@Module
@Singleton
class AppModule constructor(val application: CatRatesApplication) {

    @Provides
    @StoreCacheDir
    @Singleton
    fun provideCacheDir() : File {
        return application.cacheDir
    }

    @Provides
    @Singleton
    fun provideAppDatabase() : AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "database").build()
    }



    @Provides
    @Singleton
    fun provideSharedPrefs() : SharedPreferences {
        return application.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePicasso() : Picasso {
        return Picasso.Builder(application)
                .downloader(OkHttp3Downloader(application.cacheDir, 100000000))
                .build()
    }

}