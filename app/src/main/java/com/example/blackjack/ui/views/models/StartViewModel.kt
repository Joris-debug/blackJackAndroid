package com.example.blackjack.ui.views.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blackjack.data.repositories.CardDeckRepository
import com.example.blackjack.ui.views.states.StartViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val cardDeckRepository: CardDeckRepository
): ViewModel(){
    private val _uiStateFlow = MutableStateFlow(StartViewState())
    val uiStateFlow: StateFlow<StartViewState> = _uiStateFlow

    init {
        viewModelScope.launch {
           val deckData = cardDeckRepository.createNewDeck()
            _uiStateFlow.value = _uiStateFlow.value.copy(
                success = deckData.success,
                deckId = deckData.deckId,
                remaining = deckData.remaining,
                shuffled = deckData.shuffled
            )
        }
    }

    fun drawCard() {
        viewModelScope.launch {
            val deckId = _uiStateFlow.value.deckId
            if (deckId.isNotEmpty()) {
                val drawData = cardDeckRepository.drawCard(deckId)
                _uiStateFlow.value = _uiStateFlow.value.copy(
                    remaining = drawData.remaining,
                    cardsDrawn = _uiStateFlow.value.cardsDrawn + drawData.cards.map { it.code }
                )
            }
        }
    }
}