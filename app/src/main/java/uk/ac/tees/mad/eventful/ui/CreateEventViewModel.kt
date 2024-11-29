package uk.ac.tees.mad.eventful.ui

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore

class CreateEventViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()

    private val _isEventCreated = MutableLiveData(false)
    val isEventCreated: LiveData<Boolean> = _isEventCreated

    private val _currentLocation = MutableLiveData<String>()
    val currentLocation: LiveData<String> = _currentLocation

    fun updateLocation(location: String) {
        _currentLocation.postValue(location)
    }

    fun createEvent(
        name: String,
        description: String,
        date: String,
        time: String,
        location: String
    ) {
        if (name.isBlank() || date.isBlank() || time.isBlank() || location.isBlank()) {
            return
        }

        val event = hashMapOf(
            "name" to name,
            "description" to description,
            "date" to date,
            "time" to time,
            "location" to location,
            "createdAt" to System.currentTimeMillis()
        )

        firestore.collection("events")
            .add(event)
            .addOnSuccessListener {
                _isEventCreated.value = true
            }
            .addOnFailureListener {
                _isEventCreated.value = false
            }
    }

    @SuppressLint("MissingPermission")
    fun fetchCurrentLocation(context: Context) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val geocoder = Geocoder(context)
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    val address = addresses[0]
                    val locationName = address.getAddressLine(0)
                    _currentLocation.value = locationName
                } else {
                    _currentLocation.value = "Unknown"
                }
            } else {
                _currentLocation.value = "Location not available"
            }
        }.addOnFailureListener {
            _currentLocation.value = "Failed to fetch location"
        }
    }

    fun resetEventCreationState() {
        _isEventCreated.value = false
    }
}
