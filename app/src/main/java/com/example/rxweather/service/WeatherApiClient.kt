package com.example.rxweather.service

import android.location.Location
import android.util.Log
import com.example.rxweather.model.City
import com.example.rxweather.model.Forecast
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.OkHttpClient



class WeatherApiClient {

    private val weatherApi by lazy { initApi() }
    private val TAG by lazy { WeatherApiClient::class.java.simpleName }
    private val apiKey = "b002921a2e394137f7857ebbd066ec6c"

    private fun initApi(): WeatherAPI {
        val logger = HttpLoggingInterceptor {
            Log.d(TAG, it)
        }
        logger.level = HttpLoggingInterceptor.Level.BASIC
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logger)

        val contentType = MediaType.get("application/json")
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(Json.nonstrict.asConverterFactory(contentType))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient.build())
            .build()
        return retrofit.create(WeatherAPI::class.java)
    }

    fun getForecast(location: Location): Single<ForecastResponse> {
        return weatherApi.getForecast(location.latitude.toInt(), location.longitude.toInt(), apiKey, "metric")
    }

    interface WeatherAPI {
        @GET("forecast/daily")
        fun getForecast(@Query("lat") lat: Int, @Query("lon") lon: Int,
                        @Query("APPID") apiKey: String, @Query("unit") unit: String): Single<ForecastResponse>
    }

    @Serializable
    data class ForecastResponse (val city: City, val list: List<Forecast>)
}