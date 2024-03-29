package com.ariefzuhri.gizee.base

import android.app.Application
import android.util.Log
import com.ariefzuhri.gizee.BuildConfig
import com.ariefzuhri.gizee.core.database.di.databaseModule
import com.ariefzuhri.gizee.core.database.di.networkModule
import com.ariefzuhri.gizee.core.database.di.repositoryModule
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.orhanobut.logger.Logger
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.PrettyFormatStrategy

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initLogger()
        initTimber()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@BaseApplication)
            // Init data source modules
            modules(
                databaseModule,
                networkModule,
                repositoryModule,
            )
        }
    }

    private fun initLogger() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .tag("DEV_LOG")
            .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }

    private class DebugTree : Timber.DebugTree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            Logger.log(priority, tag, message, t)
        }
    }

    private class ReleaseTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == Log.ERROR || priority == Log.WARN) {
                if (t == null) {
                    FirebaseCrashlytics.getInstance().log(message)
                } else {
                    FirebaseCrashlytics.getInstance().recordException(t)
                }
            }
        }
    }
}