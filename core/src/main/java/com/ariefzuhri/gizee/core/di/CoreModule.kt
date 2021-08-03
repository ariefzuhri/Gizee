package com.ariefzuhri.gizee.core.di

import androidx.room.Room
import com.ariefzuhri.gizee.core.BuildConfig
import com.ariefzuhri.gizee.core.data.FoodRepository
import com.ariefzuhri.gizee.core.data.source.local.LocalDataSource
import com.ariefzuhri.gizee.core.data.source.local.room.FoodDatabase
import com.ariefzuhri.gizee.core.data.source.remote.RemoteDataSource
import com.ariefzuhri.gizee.core.data.source.remote.network.ApiService
import com.ariefzuhri.gizee.core.domain.repository.IFoodRepository
import com.ariefzuhri.gizee.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val NUTRITIONIX_BASE_URL = "***REMOVED***"

val databaseModule = module {
    factory { get<FoodDatabase>().foodDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            FoodDatabase::class.java, "Food.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        val httpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            httpClient.connectTimeout(120, TimeUnit.SECONDS)
            httpClient.readTimeout(120, TimeUnit.SECONDS)
        }
        httpClient.build()
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
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IFoodRepository> { FoodRepository(get(), get(), get()) }
}