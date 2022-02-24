package com.ariefzuhri.gizee.feature.favorites.data

import com.ariefzuhri.gizee.feature.favorites.domain.repository.IFavoritesRepository
import com.ariefzuhri.gizee.core.database.data.source.local.LocalDataSource
import com.ariefzuhri.gizee.core.database.domain.model.Food
import com.ariefzuhri.gizee.core.database.util.mapEntitiesToDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesRepository(private val localDataSource: LocalDataSource) : IFavoritesRepository {

    override fun getFavorites(): Flow<List<Food>> {
        return localDataSource.getFavoriteFoods().map {
            mapEntitiesToDomain(it)
        }
    }
}