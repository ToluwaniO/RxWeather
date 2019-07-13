package com.example.rxweather

object Util {
    fun weatherIconId(icon: String): Int {
        return when(icon) {
            "01d" -> R.drawable.day
            "01n" -> R.drawable.night
            "02d" -> R.drawable.cloudy_day_1
            "02n" -> R.drawable.cloudy_night_1
            "04d", "03d", "04n", "03n" ->  R.drawable.cloudy
            "09d", "09n" ->  R.drawable.rainy_5
            "10d", "10n" -> R.drawable.rainy_7
            "11d", "11n" -> R.drawable.thunder
            "13d", "13n" -> R.drawable.snowy_6
            "50d", "50n" -> R.drawable.cloudy
            else -> R.drawable.weather
        }
    }
}