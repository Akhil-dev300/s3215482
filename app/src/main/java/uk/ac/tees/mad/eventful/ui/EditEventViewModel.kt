package uk.ac.tees.mad.eventful.ui

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore
import uk.ac.tees.mad.eventful.data.models.Event

class EditEventViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> = _event

    private val _currentLocation = MutableLiveData<String>()
    val currentLocation: LiveData<String> = _currentLocation

    fun getEventDetails(eventId: String) {
        firestore.collection("events")
            .document(eventId)
            .get()
            .addOnSuccessListener { document ->
                val eventData = document.toObject(Event::class.java)?.copy(id = document.id)
                eventData?.let {
                    _event.value = it
                    _currentLocation.value = it.location
                }
            }
            .addOnFailureListener {
                Log.e("EditEventViewModel", "Failed to fetch event details")
            }
    }

    fun updateEvent(event: Event) {
        firestore.collection("events")
            .document(event.id)
            .update(
                "name", event.name,
                "description", event.description,
                "date", event.date,
                "time", event.time,
                "location", event.location
            )
            .addOnSuccessListener {
                Log.d("EditEventViewModel", "Event updated successfully")
            }
            .addOnFailureListener {
                Log.e("EditEventViewModel", "Failed to update event")
            }
    }


    fun updateLocation(location: String) {
        _currentLocation.postValue(location)
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

}
