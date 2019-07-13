package com.example.rxweather.model

import kotlinx.serialization.Serializable

@Serializable
data class Forecast (val dt: Long, val pressure: Double, val humidity: Int, val weather: List<WeatherInfo>,
                     val temp: Temperature)