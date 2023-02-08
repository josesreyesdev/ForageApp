package com.devjsr.forageapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.devjsr.forageapp.model.Forageable
import kotlinx.coroutines.flow.Flow

@Dao
interface ForageableDao {

    @Insert( onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertForageable(forageable: Forageable)

    @Update
    suspend fun updateForageable(forageable: Forageable)

    @Delete
    suspend fun deleteForageable(forageable: Forageable)

    @Query("SELECT * FROM forageable_database WHERE id = :id")
    fun getForageable(id: Long): Flow<Forageable>

    @Query("SELECT * FROM forageable_database ORDER BY name ASC")
    fun getForageables(): Flow<List<Forageable>>
}