package com.example.rxweather.model

import kotlinx.serialization.Serializable

@Serializable
data class City (val name: String, val country: String)