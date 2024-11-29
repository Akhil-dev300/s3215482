package uk.ac.tees.mad.eventful.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import uk.ac.tees.mad.eventful.data.models.Event

class HomeViewmodel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val eventsCollection = firestore.collection("events")


    init {
        fetchEvents()
    }

    fun fetchEvents() {
        firestore.collection("events")
            .orderBy("createdAt", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val eventList = snapshot.documents.mapNotNull { document ->
                        document.toObject(Event::class.java)?.copy(id = document.id)
                    }
                    Log.d("HomeViewModel", "Event: ${eventList}")

                }
            }
    }
}