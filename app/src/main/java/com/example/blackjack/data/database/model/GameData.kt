package com.example.blackjack.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class GameData(
    @PrimaryKey(autoGenerate = false)
    val uid: Int = 1,
    @ColumnInfo(name = "deck_id") val deckId: String,
    @ColumnInfo(name = "success") val success: Boolean,
    @ColumnInfo(name = "remaining") val remaining: Int,
    @ColumnInfo(name = "shuffled") val shuffled: Boolean,
)