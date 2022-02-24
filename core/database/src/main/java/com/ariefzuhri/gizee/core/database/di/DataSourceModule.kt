package com.ariefzuhri.gizee.core.database.di

import androidx.room.Room
import com.ariefzuhri.gizee.core.common.util.AppExecutors
import com.ariefzuhri.gizee.core.database.BuildConfig.*
import com.ariefzuhri.gizee.core.database.data.source.local.LocalDataSource
import com.ariefzuhri.gizee.core.database.data.source.local.persistence.FoodDatabase
import com.ariefzuhri.gizee.core.database.data.source.remote.RemoteDataSource
import com.ariefzuhri.gizee.core.database.data.source.remote.network.ApiService
import com.chuckerteam.chucker.api.ChuckerInterceptor
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val NETWORK_CALL_TIMEOUT = 120L

val databaseModule = module {
    factory { get<FoodDatabase>().foodDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes(DATABASE_PASSPHRASE.toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            FoodDatabase::class.java, "Food.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = NUTRITIONIX_BASE_URL
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/$NUTRITIONIX_PUBLIC_KEY_1")
            .add(hostname, "sha256/$NUTRITIONIX_PUBLIC_KEY_2")
            .add(hostname, "sha256/$NUTRITIONIX_PUBLIC_KEY_3")
            .add(hostname, "sha256/$NUTRITIONIX_PUBLIC_KEY_4")
            .build()

        val httpClient = OkHttpClient.Builder()
        with(httpClient) {
            if (DEBUG) addInterceptor(
                ChuckerInterceptor.Builder(androidContext()).build()
            )
            connectTimeout(NETWORK_CALL_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(NETWORK_CALL_TIMEOUT, TimeUnit.SECONDS)
            certificatePinner(certificatePinner)
            build()
        }
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(NUTRITIONIX_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(foodDao = get()) }
    single { RemoteDataSource(apiService = get()) }
    factory { AppExecutors() }
}