package com.example.rxweather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rxweather.viewmodel.ForecastViewModel
import kotlinx.android.synthetic.main.weekly_forecast_fragment.*

class WeeklyForecastFragment: Fragment() {
    private val viewModel by lazy { ViewModelProviders.of(this).get(ForecastViewModel::class.java) }
    private val adapter = WeatherAdapter()
    private val TAG = WeeklyForecastFragment::class.java.simpleName
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.weekly_forecast_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weather_recycler.apply {
            layoutManager = LinearLayoutManager(this@WeeklyForecastFragment.context)
            adapter = this@WeeklyForecastFragment.adapter
        }
        viewModel.getForecast().subscribe {
            Log.d(TAG, it.toString())
            adapter.submitList(it.list)
//            adapter.notifyDataSetChanged()
        }
    }
}