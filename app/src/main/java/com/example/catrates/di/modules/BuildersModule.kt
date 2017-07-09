package com.example.catrates.di.modules

import com.example.catrates.catlist.CatsFragment
import com.example.catrates.favorites.FavoriteCatsFragment
import com.example.catrates.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    abstract fun contributeMainActivityInjector() : MainActivity

    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    abstract fun contributeCatFragmentInjector() : CatsFragment

    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    abstract fun contributeFavoritesFragmentInjector() : FavoriteCatsFragment


}