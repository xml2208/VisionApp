package com.xml.visionapp.data.remote

import com.xml.visionapp.data.model.BatteryResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "http://localhost:8080/"

private val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface VisionApiService {

    @GET("charge")
    suspend fun getBatteryPercentage(): BatteryResponse

}

object VisionApi {
    val visionApiService: VisionApiService by lazy {
        retrofit.create(VisionApiService::class.java)
    }
}