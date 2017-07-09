package com.example.catrates.di.modules

import com.example.catrates.persistence.AppDatabase
import com.example.catrates.persistence.CatDao
import com.example.catrates.persistence.FavoriteCatsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FavoriteCatsPersistenceModule {

    @Provides
    @Singleton
    fun provideCatDao(appDb: AppDatabase) : CatDao {
        return appDb.catDao()
    }

    @Provides
    @Singleton
    fun provideFavoriteCatsRepository(dao: CatDao) : FavoriteCatsRepository {
        return FavoriteCatsRepository(dao)
    }
}