package uk.ac.tees.mad.eventful.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class Event(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val description: String = "",
    val date: String = "",
    val time: String = "",
    val location: String = "",
    val uploadedByUid: String = "",
    val uploadedByName: String = ""
)
