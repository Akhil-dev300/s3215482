package uk.ac.tees.mad.eventful.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class CreateEventViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _isEventCreated = MutableLiveData(false)
    val isEventCreated: LiveData<Boolean> = _isEventCreated

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
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("events")
            .add(event)
            .addOnSuccessListener {
                _isEventCreated.value = true
            }
            .addOnFailureListener {
                _isEventCreated.value = false
            }
    }

    fun resetEventCreationState() {
        _isEventCreated.value = false
    }
}
