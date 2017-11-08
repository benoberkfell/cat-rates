package com.example.catrates

import android.app.Activity
import android.app.Application
import android.content.SharedPreferences
import com.example.catrates.di.DaggerAppComponent
import com.example.catrates.di.modules.AppModule
import com.nytimes.android.external.fs3.PathResolver
import com.nytimes.android.external.store3.base.impl.BarCode
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import okio.Okio
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class CatRatesApplication : Application(), HasActivityInjector {

    @Inject lateinit var dispatchingActivityInjector : DispatchingAndroidInjector<Activity>

    @Inject lateinit var sharedPrefs: SharedPreferences

    @Inject lateinit var pathResolver: PathResolver<BarCode>

    val APPLIED_RES_PREF = "APPLIED_RES"

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
                .inject(this)


        Timber.plant(Timber.DebugTree())

        maybeApplyCannedResources()
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingActivityInjector;
    }

    /*
     * Insert data from our assets into the Store, so we have something on first use.
     */
    fun maybeApplyCannedResources() {
        val hasAppliedBefore = sharedPrefs.getBoolean(APPLIED_RES_PREF, false)
        if (!hasAppliedBefore) {

            val barcode = BarCode("Cats", "AllOfThem")

            val inputStream = assets.open("preloaded_cats.xml")

            val outputFile = File(cacheDir, pathResolver.resolve(barcode))

            val source = Okio.source(inputStream)

            val sink = Okio.buffer(Okio.sink(outputFile))
            sink.writeAll(source)
            sink.flush()
            sink.close()

            sharedPrefs.edit().putBoolean(APPLIED_RES_PREF, true).apply()
        }
    }

}

