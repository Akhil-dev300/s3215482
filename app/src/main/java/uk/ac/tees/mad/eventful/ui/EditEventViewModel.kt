package uk.ac.tees.mad.eventful.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import uk.ac.tees.mad.eventful.data.models.Event

class EditEventViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()

    fun getEventDetails(eventId: String) {
        firestore.collection("events")
            .document(eventId)
            .get()
            .addOnSuccessListener { document ->
                val eventData = document.toObject(Event::class.java)?.copy(id = document.id)
                Log.d("EDITEVENT", "EVENT: $eventData")
            }
            .addOnFailureListener {
                Log.e("EditEventViewModel", "Failed to fetch event details")
            }
    }

}
