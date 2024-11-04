package com.example.blackjack.network

import retrofit2.http.GET

interface DeckOfCardsApi {
    @GET("api/deck/new/shuffle/")
    suspend fun createDeck(): DeckResponse
}

data class DeckResponse(
    val success: Boolean,
    val deckId: String,
    val remaining: Int
)
