package com.example.wadaihjparty.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wadaihjparty.domain.model.Cake
import kotlinx.coroutines.flow.Flow

@Dao
interface CakeDao {
    @Query("SELECT * FROM cake_table")
    fun getAllCakes(): Flow<List<Cake>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCakes(cakes: List<Cake>)

    @Query("DELETE FROM cake_table")
    fun deleteAllCakes()
}