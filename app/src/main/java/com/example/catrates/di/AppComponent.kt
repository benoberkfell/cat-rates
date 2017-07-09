package com.example.catrates.di

import com.example.catrates.CatRatesApplication
import com.example.catrates.di.modules.*
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidSupportInjectionModule::class,
        BuildersModule::class,
        AppModule::class,
        RestModule::class,
        CatStoreModule::class,
        FavoriteCatsPersistenceModule::class))
interface AppComponent {

    fun inject(application: CatRatesApplication)

}