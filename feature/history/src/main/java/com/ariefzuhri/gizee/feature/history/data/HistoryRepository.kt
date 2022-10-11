package com.ariefzuhri.gizee.feature.history.data

import com.ariefzuhri.gizee.feature.history.domain.repository.IHistoryRepository
import com.ariefzuhri.gizee.core.database.data.source.local.LocalDataSource
import com.ariefzuhri.gizee.core.database.domain.model.History
import com.ariefzuhri.gizee.core.common.util.AppExecutors
import com.ariefzuhri.gizee.core.database.data.repository.mapper.mapDomainToEntity
import com.ariefzuhri.gizee.core.database.data.repository.mapper.mapEntitiesToDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HistoryRepository(
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
) : IHistoryRepository {

    override fun getHistory(): Flow<List<History>> {
        return localDataSource.getHistoryWithFoods().map {
            mapEntitiesToDomain(it)
        }
    }

    override fun clearHistory() {
        appExecutors.diskIO().execute {
            localDataSource.deleteHistory()
        }
    }

    override fun deleteHistory(history: History) {
        val historyEntity = mapDomainToEntity(history)
        appExecutors.diskIO().execute {
            localDataSource.deleteHistory(historyEntity)
        }
    }
}