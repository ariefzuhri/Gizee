package com.ariefzuhri.gizee.feature.searchresults.data

import com.ariefzuhri.gizee.feature.searchresults.domain.repository.ISearchResultsRepository
import com.ariefzuhri.gizee.core.common.wrapper.ApiResponse
import com.ariefzuhri.gizee.core.common.wrapper.NetworkBoundResource
import com.ariefzuhri.gizee.core.common.wrapper.Resource
import com.ariefzuhri.gizee.core.database.domain.model.History
import com.ariefzuhri.gizee.core.database.data.source.local.LocalDataSource
import com.ariefzuhri.gizee.core.database.data.source.local.entity.FoodEntity
import com.ariefzuhri.gizee.core.database.data.source.local.entity.HistoryEntity
import com.ariefzuhri.gizee.core.database.data.source.remote.RemoteDataSource
import com.ariefzuhri.gizee.core.database.data.source.remote.response.FoodResponse
import com.ariefzuhri.gizee.core.database.data.repository.mapper.mapEntityToDomain
import com.ariefzuhri.gizee.core.database.data.repository.mapper.mapResponseToEntities
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class SearchResultsRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) :
    ISearchResultsRepository {

    override fun getSearchResults(query: String): Flow<Resource<History>> {
        return object : NetworkBoundResource<History, FoodResponse>() {
            var historyEntity = HistoryEntity(query)
            var result = listOf<FoodEntity>()

            override fun loadFromDB(): Flow<History> {
                val data = flowOf(result)
                return data.map { mapEntityToDomain(historyEntity, it) }
            }

            override fun shouldFetch(data: History?): Boolean {
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<FoodResponse>> {
                localDataSource.insertHistory(historyEntity)
                return remoteDataSource.getFoodsByNaturalLanguage(query)
            }

            override suspend fun saveCallResult(data: FoodResponse) {
                val foodEntities = mapResponseToEntities(data)
                result = foodEntities
            }
        }.asFlow()
    }
}