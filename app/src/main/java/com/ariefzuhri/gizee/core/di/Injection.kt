package com.ariefzuhri.gizee.core.di

import android.content.Context
import com.ariefzuhri.gizee.core.data.FoodRepository
import com.ariefzuhri.gizee.core.data.source.local.LocalDataSource
import com.ariefzuhri.gizee.core.data.source.local.room.FoodDatabase
import com.ariefzuhri.gizee.core.data.source.remote.RemoteDataSource
import com.ariefzuhri.gizee.core.data.source.remote.network.ApiConfig
import com.ariefzuhri.gizee.core.domain.repository.IFoodRepository
import com.ariefzuhri.gizee.core.domain.usecase.FoodInteractor
import com.ariefzuhri.gizee.core.domain.usecase.FoodUseCase
import com.ariefzuhri.gizee.core.utils.AppExecutors

object Injection {

    private fun provideRepository(context: Context): IFoodRepository {
        val database = FoodDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApiService())
        val localDataSource = LocalDataSource.getInstance(database.foodDao())
        val appExecutors = AppExecutors()

        return FoodRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun provideFoodUseCase(context: Context): FoodUseCase {
        val repository = provideRepository(context)
        return FoodInteractor(repository)
    }
}