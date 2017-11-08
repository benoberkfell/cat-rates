package com.example.catrates.di.modules

import com.example.catrates.catapi.CatApi
import com.example.catrates.annotations.StoreCacheDir
import com.example.catrates.catapi.CatResponse
import com.example.catrates.data.CatParser
import com.nytimes.android.external.fs3.FileSystemPersisterFactory
import com.nytimes.android.external.fs3.PathResolver
import com.nytimes.android.external.fs3.SourcePersisterFactory
import com.nytimes.android.external.fs3.filesystem.FileSystem
import com.nytimes.android.external.fs3.filesystem.FileSystemFactory
import com.nytimes.android.external.store3.base.impl.BarCode
import com.nytimes.android.external.store3.base.impl.Store
import com.nytimes.android.external.store3.base.impl.StoreBuilder
import dagger.Module
import dagger.Provides
import okio.BufferedSource
import java.io.File
import javax.inject.Singleton

@Module
class CatStoreModule {

    @Provides
    @Singleton
    fun providePathResolver() : PathResolver<BarCode> {
        return PathResolver<BarCode> { key -> key.toString() }
    }

    @Provides
    @Singleton
    fun provideCatStore(api: CatApi,
                        @StoreCacheDir cacheDir: File,
                        pathResolver: PathResolver<BarCode>) : Store<CatResponse, BarCode> {
        return StoreBuilder.parsedWithKey<BarCode, BufferedSource, CatResponse>()
                .fetcher {
                    api.fetchCatPictures().map {
                            it.source()
                    }
                }
                .persister(FileSystemPersisterFactory.create(cacheDir, pathResolver))
                .parser(CatParser())
                .open()
    }

}