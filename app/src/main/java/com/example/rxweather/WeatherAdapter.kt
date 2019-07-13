package com.example.rxweather

import android.view.LayoutInflater
import kotlinx.android.synthetic.main.main_weather_view.*
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rxweather.model.Forecast
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.main_weather_view.temperatureHighView
import kotlinx.android.synthetic.main.main_weather_view.temperatureLowView
import kotlinx.android.synthetic.main.main_weather_view.weatherIconView
import kotlinx.android.synthetic.main.weather_forecast_item_view.*
import java.text.SimpleDateFormat
import java.util.*

class WeatherAdapter : ListAdapter<Forecast, RecyclerView.ViewHolder>(WeatherDiffCallback()) {

    private val TODAY_VIEW = 0
    private val OTHER_VIEW = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TODAY_VIEW -> TodayViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.main_weather_view,
                parent, false))
            else -> ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.weather_forecast_item_view,
                parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        return when (viewType) {
            TODAY_VIEW -> (holder as TodayViewHolder).bind(getItem(position))
            else -> (holder as ItemViewHolder).bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TODAY_VIEW
            else -> OTHER_VIEW
        }
    }
}

class TodayViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(forecast: Forecast) {
        val date = Date(forecast.dt)
        val format = SimpleDateFormat("EEEEE dd MMMMM yyyy", Locale.US)
        val icon = if (forecast.weather.isEmpty()) {
            ""
        } else {
            forecast.weather[0].icon
        }
        weatherIconView.setImageResource(Util.weatherIconId(icon))
        dateView.text = format.format(date)
        temperatureHighView.text = "${forecast.temp.max}°c"
        temperatureLowView.text = "${forecast.temp.min}°c"
    }
}

class ItemViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(forecast: Forecast) {
//        val date = Date(forecast.dt)
//        val format = SimpleDateFormat("EEEEE dd MMMMM yyyy", Locale.ENGLISH)
//        dateView.text = format.format(date)
        val icon = if (forecast.weather.isEmpty()) {
            ""
        } else {
            forecast.weather[0].icon
        }
        weatherIconView.setImageResource(Util.weatherIconId(icon))
        weatherView.text = if (forecast.weather.isEmpty()) {
            "Clear"
        } else {
            forecast.weather[0].description
        }
        temperatureHighView.text = "${forecast.temp.max}°c"
        temperatureLowView.text = "${forecast.temp.min}°c"
    }
}

class WeatherDiffCallback : DiffUtil.ItemCallback<Forecast>() {
    override fun areItemsTheSame(oldItem: Forecast, newItem: Forecast) = oldItem == newItem

    override fun areContentsTheSame(oldItem: Forecast, newItem: Forecast) = oldItem == newItem

}