@file:Suppress("unused")

package com.ariefzuhri.gizee

import android.app.Application
import com.ariefzuhri.gizee.core.di.databaseModule
import com.ariefzuhri.gizee.core.di.networkModule
import com.ariefzuhri.gizee.core.di.repositoryModule
import com.ariefzuhri.gizee.core.utils.ReleaseTree
import com.ariefzuhri.gizee.di.useCaseModule
import com.ariefzuhri.gizee.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initTimber()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }
}