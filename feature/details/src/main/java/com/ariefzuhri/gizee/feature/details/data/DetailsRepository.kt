package com.ariefzuhri.gizee.feature.details.data

import com.ariefzuhri.gizee.core.common.util.AppExecutors
import com.ariefzuhri.gizee.core.database.domain.model.Food
import com.ariefzuhri.gizee.feature.details.domain.repository.IDetailsRepository
import com.ariefzuhri.gizee.core.database.data.source.local.LocalDataSource
import com.ariefzuhri.gizee.core.database.data.repository.mapper.mapDomainToEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DetailsRepository(
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
) : IDetailsRepository {

    override fun isFavorite(foodId: String): Flow<Boolean> {
        return localDataSource.getFavoriteFood(foodId).map {
            @Suppress("SENSELESS_COMPARISON")
            it != null
        }
    }

    override fun setFavorite(food: Food, newState: Boolean) {
        val foodEntity = mapDomainToEntity(food)
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteFood(foodEntity, newState)
        }
    }
}