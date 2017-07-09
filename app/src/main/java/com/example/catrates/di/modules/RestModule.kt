package com.example.catrates.di.modules

import com.example.catrates.BuildConfig
import com.example.catrates.catapi.CatApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory

@Module
class RestModule {

    @Provides
    fun provideCatApi() : CatApi {

        return Retrofit.Builder()
                .baseUrl("http://thecatapi.com")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .validateEagerly(BuildConfig.DEBUG)
                .build()
                .create(CatApi::class.java)

    }

}