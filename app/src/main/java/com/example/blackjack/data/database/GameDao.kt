package com.example.blackjack.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.blackjack.data.database.model.GameData
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    @Query("SELECT * FROM gamedata")
    fun getGame(): Flow<GameData>

    @Upsert
    suspend fun insert(game: GameData)

}