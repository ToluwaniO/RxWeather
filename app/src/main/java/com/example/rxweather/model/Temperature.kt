package com.example.rxweather.model

import kotlinx.serialization.Serializable

@Serializable
data class Temperature (val min: Double, val max: Double)