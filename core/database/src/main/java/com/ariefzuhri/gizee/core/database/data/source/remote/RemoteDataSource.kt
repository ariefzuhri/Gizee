package com.ariefzuhri.gizee.core.database.data.source.remote

import com.ariefzuhri.gizee.core.common.dto.ApiResponse
import com.ariefzuhri.gizee.core.common.util.TAG
import com.ariefzuhri.gizee.core.database.data.source.remote.network.ApiService
import com.ariefzuhri.gizee.core.database.data.source.remote.response.FoodResponse
import com.ariefzuhri.gizee.core.database.data.source.remote.response.NutrientResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getFoodsByNaturalLanguage(query: String): Flow<ApiResponse<FoodResponse>> {
        return flow {
            try {
                val response = apiService.postNutrientsNatural(mapOf("query" to query))
                val dataArray = response.foods
                if (dataArray?.isNotEmpty() as Boolean) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Timber.tag(TAG).e(e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getNutrients(): Flow<ApiResponse<List<NutrientResponse>>> {
        return flow {
            try {
                val response = apiService.getNutrientsUtils()
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Timber.tag(TAG).e(e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}