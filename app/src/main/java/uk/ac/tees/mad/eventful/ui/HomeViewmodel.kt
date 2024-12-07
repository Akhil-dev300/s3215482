package uk.ac.tees.mad.eventful.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import uk.ac.tees.mad.eventful.data.models.Event

class HomeViewmodel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()

    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events

    private val _filteredEvents = MutableLiveData<List<Event>>()
    val filteredEvents: LiveData<List<Event>> = _filteredEvents

    init {
        fetchEvents()
    }

    fun fetchEvents() {
        firestore.collection("events")
            .get()
            .addOnSuccessListener {
                val eventList = it.documents.mapNotNull { document ->
                    document.toObject(Event::class.java)?.copy(id = document.id)
                }
                _events.value = eventList
                _filteredEvents.value = eventList
            }
            .addOnFailureListener {
                Log.e("HomeViewmodel", "Failed to fetch events", it)
            }

    }

    fun searchEvents(query: String) {
        val allEvents = _events.value ?: return
        _filteredEvents.value = allEvents.filter {
            it.name.contains(query, ignoreCase = true) || it.date.contains(query)
        }
    }
}