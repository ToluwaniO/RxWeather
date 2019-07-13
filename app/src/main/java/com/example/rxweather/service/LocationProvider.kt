package com.example.rxweather.service

import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices
import io.reactivex.Single
import io.reactivex.SingleEmitter

class LocationProvider (context: Context) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    fun getLastKnownLocation() = Single.create<Location> { lastKnownLocations(it) }

    private fun lastKnownLocations(emitter: SingleEmitter<Location>) {
        try {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                emitter.onSuccess(it)
            }.addOnFailureListener {
                emitter.onError(it)
            }.addOnCanceledListener {
                emitter.onError(Exception("Location request cancelled"))
            }
        } catch (e: SecurityException) {
            emitter.onError(e)
        }
    }

}