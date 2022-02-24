package com.ariefzuhri.gizee.feature.nutritionfacts.data

import com.ariefzuhri.gizee.feature.nutritionfacts.domain.repository.INutritionFactsRepository
import com.ariefzuhri.gizee.core.common.dto.ApiResponse
import com.ariefzuhri.gizee.core.common.dto.NetworkBoundResource
import com.ariefzuhri.gizee.core.common.dto.Resource
import com.ariefzuhri.gizee.core.database.domain.model.Nutrient
import com.ariefzuhri.gizee.core.database.data.source.local.LocalDataSource
import com.ariefzuhri.gizee.core.database.data.source.remote.RemoteDataSource
import com.ariefzuhri.gizee.core.database.data.source.remote.response.NutrientResponse
import com.ariefzuhri.gizee.core.database.util.mapEntitiesToDomain
import com.ariefzuhri.gizee.core.database.util.mapResponsesToEntities
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NutritionFactsRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : INutritionFactsRepository {

    override fun getNutrients(): Flow<Resource<List<Nutrient>>> {
        return object :
            NetworkBoundResource<List<Nutrient>, List<NutrientResponse>>() {
            override fun loadFromDB(): Flow<List<Nutrient>> {
                return localDataSource.getNutrients().map {
                    mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Nutrient>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<NutrientResponse>>> {
                return remoteDataSource.getNutrients()
            }

            override suspend fun saveCallResult(data: List<NutrientResponse>) {
                val nutrientEntities = mapResponsesToEntities(data)
                localDataSource.insertNutrients(nutrientEntities)
            }
        }.asFlow()
    }
}