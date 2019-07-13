package com.example.rxweather.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import com.example.rxweather.service.LocationProvider
import com.example.rxweather.service.WeatherApiClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class ForecastViewModel(app: Application): AndroidViewModel(app) {
    private val TAG by lazy { ForecastViewModel::class.java.simpleName }
    private val weatherApi = WeatherApiClient()
    private val locationProvider = LocationProvider(app.applicationContext)
    private val disposables = CompositeDisposable()
    private val liveForecast = PublishSubject.create<WeatherApiClient.ForecastResponse>()

    fun getForecast(): Observable<WeatherApiClient.ForecastResponse> {
        val sub = locationProvider.getLastKnownLocation()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .flatMap {
                weatherApi.getForecast(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response, error ->
                if (error != null) {
                    Log.d(TAG, error.message ?: "An error occurred")
                    return@subscribe
                }
                Log.d(TAG, "$response")
                liveForecast.onNext(response)
            }
        disposables.add(sub)
        return liveForecast
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}