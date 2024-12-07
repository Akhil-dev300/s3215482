package uk.ac.tees.mad.eventful.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore
import uk.ac.tees.mad.eventful.R

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
        location: String,
        context: Context
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
                val combinedDateTimeString = "$date ${time}}"
                showNotification(context, name, combinedDateTimeString, it.id)
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

    fun showNotification(context: Context, eventName: String, eventTime: String, eventId: String) {
        val notificationId = eventId.hashCode()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "event_notifications",
                "Event Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifies users about their upcoming events"
            }
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        // Create the notification
        val builder = NotificationCompat.Builder(context, "event_notifications")
            .setSmallIcon(R.drawable.event)
            .setContentTitle("Upcoming Event")
            .setContentText("Event: $eventName at $eventTime")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        // Show the notification
        val notificationManager =
            NotificationManagerCompat.from(context)


        notificationManager.notify(notificationId, builder.build())

    }
}