package com.ariefzuhri.gizee.core.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ariefzuhri.gizee.core.data.source.remote.network.ApiResponse
import com.ariefzuhri.gizee.core.data.source.remote.network.ApiService
import com.ariefzuhri.gizee.core.data.source.remote.response.FoodResponse
import com.ariefzuhri.gizee.core.data.source.remote.response.NutrientResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val apiService: ApiService) {

    companion object {
        private val TAG = RemoteDataSource::class.java.simpleName

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service)
            }
    }

    fun searchFoodsByNaturalLanguage(query: String): LiveData<ApiResponse<FoodResponse>> {
        val result = MutableLiveData<ApiResponse<FoodResponse>>()

        val client = apiService.postNutrientsNatural(hashMapOf("query" to query))
        client.enqueue(object : Callback<FoodResponse> {

            override fun onResponse(call: Call<FoodResponse>, response: Response<FoodResponse>) {
                val data = response.body()
                result.value = if (data != null) ApiResponse.Success(data) else ApiResponse.Empty
            }

            override fun onFailure(call: Call<FoodResponse>, t: Throwable) {
                result.value = ApiResponse.Error(t.message.toString())
                Log.e(TAG, t.message.toString())
            }

        })

        return result
    }

    fun getNutrients(): LiveData<ApiResponse<List<NutrientResponse>>> {
        val result = MutableLiveData<ApiResponse<List<NutrientResponse>>>()

        val client = apiService.getNutrientsUtils()
        client.enqueue(object : Callback<List<NutrientResponse>> {

            override fun onResponse(
                call: Call<List<NutrientResponse>>,
                response: Response<List<NutrientResponse>>
            ) {
                val data = response.body()
                result.value = if (data != null) ApiResponse.Success(data) else ApiResponse.Empty
            }

            override fun onFailure(call: Call<List<NutrientResponse>>, t: Throwable) {
                result.value = ApiResponse.Error(t.message.toString())
                Log.e(TAG, t.message.toString())
            }

        })

        return result
    }
}