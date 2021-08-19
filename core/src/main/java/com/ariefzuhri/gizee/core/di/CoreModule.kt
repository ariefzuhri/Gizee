package com.ariefzuhri.gizee.core.di

import androidx.room.Room
import com.ariefzuhri.gizee.core.BuildConfig.*
import com.ariefzuhri.gizee.core.data.repository.FoodRepository
import com.ariefzuhri.gizee.core.data.source.local.LocalDataSource
import com.ariefzuhri.gizee.core.data.source.local.persistence.FoodDatabase
import com.ariefzuhri.gizee.core.data.source.remote.RemoteDataSource
import com.ariefzuhri.gizee.core.data.source.remote.network.ApiService
import com.ariefzuhri.gizee.core.domain.repository.IFoodRepository
import com.ariefzuhri.gizee.core.utils.AppExecutors
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
            connectTimeout(120, TimeUnit.SECONDS)
            readTimeout(120, TimeUnit.SECONDS)
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
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IFoodRepository> { FoodRepository(get(), get(), get()) }
}