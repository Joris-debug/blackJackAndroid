package com.example.blackjack.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DeckOfCardsApi {

    companion object {
        const val BASE_URL = "https://deckofcardsapi.com/"
    }

    @GET("api/deck/new/shuffle/")
    suspend fun createDeck(): DeckCreationResponse

    @GET("api/deck/{deck_id}/draw/")
    suspend fun drawCard(
        @Path("deck_id") deckId: String
    ): DrawCardResponse
}

@JsonClass(generateAdapter = true)
data class DeckCreationResponse(
    val success: Boolean,
    @Json(name = "deck_id") val deckId: String,
    val remaining: Int,
    val shuffled: Boolean
)

@JsonClass(generateAdapter = true)
data class DrawCardResponse(
    val success: Boolean,
    @Json(name = "deck_id") val deckId: String,
    val cards: List<Card>,
    val remaining: Int
)

@JsonClass(generateAdapter = true)
data class Card(
    val code: String,
    val image: String,
    val images: CardImages,
    val value: String,
    val suit: String
)

@JsonClass(generateAdapter = true)
data class CardImages(
    val svg: String,
    val png: String
)
