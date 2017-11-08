package com.example.catrates.di.modules

import com.example.catrates.BuildConfig
import com.example.catrates.catapi.CatApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

@Module
class RestModule {

    @Provides
    fun provideCatApi() : CatApi {

        return Retrofit.Builder()
                .baseUrl("http://thecatapi.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .validateEagerly(BuildConfig.DEBUG)
                .build()
                .create(CatApi::class.java)

    }

}