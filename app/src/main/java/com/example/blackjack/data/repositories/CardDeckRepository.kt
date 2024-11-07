package com.example.blackjack.data.repositories


import com.example.blackjack.api.DeckOfCardsApi
import com.example.blackjack.api.DeckCreationResponse
import com.example.blackjack.api.DrawCardResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CardDeckRepository(val api: DeckOfCardsApi) {

    private val _cardDeckStatus = MutableStateFlow(false)
    val cardDeckStatus = _cardDeckStatus.asStateFlow()


    suspend fun createNewDeck(): DeckCreationResponse {
        val deckResp = api.createDeck()
        return deckResp
    }

    suspend fun drawCard(deckId: String): DrawCardResponse {
        return api.drawCard(deckId)
    }
}
