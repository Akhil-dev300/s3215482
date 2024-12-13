package uk.ac.tees.mad.eventful.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uk.ac.tees.mad.eventful.data.models.Event

@Dao
interface EventDao {
    @Query("SELECT * FROM events")
    fun getAllEvents(): LiveData<List<Event>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<Event>)

    @Query("DELETE FROM events")
    suspend fun clearEvents()
}
