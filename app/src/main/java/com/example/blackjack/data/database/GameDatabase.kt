package com.example.blackjack.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.blackjack.data.database.model.GameData

@Database(entities = [GameData::class], version = 1, exportSchema = false)
abstract class GameDatabase : RoomDatabase() {

    abstract val gameDao: GameDao

}