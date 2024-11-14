package com.example.blackjack.data.repositories


import com.example.blackjack.api.DeckOfCardsApi
import com.example.blackjack.api.DeckCreationResponse
import com.example.blackjack.api.DrawCardResponse
import com.example.blackjack.data.database.GameDao
import com.example.blackjack.data.database.model.GameData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CardDeckRepository(val api: DeckOfCardsApi, private val gameDao: GameDao) {

    private val _cardDeckStatus = MutableStateFlow(false)
    val cardDeckStatus = _cardDeckStatus.asStateFlow()
    val gameFlow = gameDao.getGame()

    suspend fun createNewDeck() {
        val resp = api.createDeck()
        gameDao.insert(resp.toGameData())
    }

    private fun DeckCreationResponse.toGameData(): GameData {
        return GameData(
            deckId = deckId.toString(),
            success = success,
            remaining = remaining,
            shuffled = shuffled
        )
    }

    suspend fun drawCard(deckId: String): DrawCardResponse {
        return api.drawCard(deckId)
    }
}
