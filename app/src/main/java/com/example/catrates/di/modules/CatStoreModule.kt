package com.example.catrates.di.modules

import com.example.catrates.catapi.CatApi
import com.example.catrates.annotations.StoreCacheDir
import com.example.catrates.catapi.CatResponse
import com.nytimes.android.external.fs.SourcePersisterFactory
import com.nytimes.android.external.store.base.impl.BarCode
import com.nytimes.android.external.store.base.impl.Store
import com.nytimes.android.external.store.base.impl.StoreBuilder
import dagger.Module
import dagger.Provides
import okio.BufferedSource
import org.simpleframework.xml.core.Persister
import java.io.File

@Module
class CatStoreModule {

    @Provides
    fun provideCatStore(api: CatApi,
                        @StoreCacheDir cacheDir: File) : Store<CatResponse, BarCode> {
        return StoreBuilder.parsedWithKey<BarCode, BufferedSource, CatResponse>()
                .fetcher {
                    api.fetchCatPictures().map {
                            it?.source()
                    }
                }
                .persister(SourcePersisterFactory.create(cacheDir))
                .parser { t: BufferedSource? ->
                    Persister().read(CatResponse::class.java, t?.inputStream())
                }
                .open()
    }

}