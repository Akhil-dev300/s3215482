package uk.ac.tees.mad.eventful.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.launch
import uk.ac.tees.mad.eventful.data.database.AppDatabase
import uk.ac.tees.mad.eventful.data.models.Event

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val firestore = FirebaseFirestore.getInstance()
    private val eventDao = AppDatabase.getDatabase(application).eventDao()

    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events

    private val _filteredEvents = MutableLiveData<List<Event>>()
    val filteredEvents: LiveData<List<Event>> = _filteredEvents

    init {
        syncEvents()
    }

    fun syncEvents() {
        firestore.collection("events")
            .get()
            .addOnSuccessListener { snapshot ->
                val eventList = snapshot.documents.mapNotNull { document ->
                    document.toObject(Event::class.java)?.copy(id = document.id)
                }

                viewModelScope.launch {
                    eventDao.clearEvents()
                    eventDao.insertEvents(eventList)
                    _events.postValue(eventList)
                    _filteredEvents.postValue(eventList)
                }
            }
            .addOnFailureListener {
                Log.e("HomeViewModel", "Failed to sync events", it)
                loadEventsFromLocal()
            }
    }

    private fun loadEventsFromLocal() {
        viewModelScope.launch {
            eventDao.getAllEvents().observeForever { localEvents ->
                _events.postValue(localEvents)
                _filteredEvents.postValue(localEvents)
            }
        }
    }

    fun searchEvents(query: String) {
        val allEvents = _events.value ?: return
        _filteredEvents.value = allEvents.filter {
            it.name.contains(query, ignoreCase = true) || it.date.contains(query)
        }
    }
}
