package com.example.rxweather.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherInfo (val id: String, val main: String, val description: String, val icon: String)