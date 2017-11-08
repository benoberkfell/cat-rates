package com.example.catrates.di.modules

import com.example.catrates.catapi.CatApi
import com.example.catrates.annotations.StoreCacheDir
import com.example.catrates.catapi.CatResponse
import com.example.catrates.data.CatParser
import com.nytimes.android.external.fs3.SourcePersisterFactory
import com.nytimes.android.external.store3.base.impl.BarCode
import com.nytimes.android.external.store3.base.impl.Store
import com.nytimes.android.external.store3.base.impl.StoreBuilder
import dagger.Module
import dagger.Provides
import okio.BufferedSource
import java.io.File

@Module
class CatStoreModule {

    @Provides
    fun provideCatStore(api: CatApi,
                        @StoreCacheDir cacheDir: File) : Store<CatResponse, BarCode> {
        return StoreBuilder.parsedWithKey<BarCode, BufferedSource, CatResponse>()
                .fetcher {
                    api.fetchCatPictures().map {
                            it.source()
                    }
                }
                .persister(SourcePersisterFactory.create(cacheDir))
                .parser(CatParser())
                .open()
    }

}