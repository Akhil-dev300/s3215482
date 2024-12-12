package uk.ac.tees.mad.eventful.data.models

data class Event(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val date: String = "",
    val time: String = "",
    val location: String = "",
    val uploadedByUid: String = "",
    val uploadedByName: String = ""
)
