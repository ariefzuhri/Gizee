package com.ariefzuhri.gizee.core.data.source.remote.network

import com.ariefzuhri.gizee.core.BuildConfig.NUTRITIONIX_APP_ID
import com.ariefzuhri.gizee.core.BuildConfig.NUTRITIONIX_APP_KEY
import com.ariefzuhri.gizee.core.data.source.remote.response.FoodResponse
import com.ariefzuhri.gizee.core.data.source.remote.response.NutrientResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers(
        "x-app-id: $NUTRITIONIX_APP_ID",
        "x-app-key: $NUTRITIONIX_APP_KEY",
        "x-remote-user-id: 0"
    )
    @POST("natural/nutrients")
    suspend fun postNutrientsNatural(@Body body: HashMap<String, Any>): FoodResponse

    @GET("utils/nutrients")
    suspend fun getNutrientsUtils(): List<NutrientResponse>
}