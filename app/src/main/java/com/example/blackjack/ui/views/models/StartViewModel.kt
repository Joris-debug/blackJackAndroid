package com.example.blackjack.ui.views.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.blackjack.data.repositories.CardDeckRepository
import com.example.blackjack.ui.views.states.StartViewState
import com.example.blackjack.workers.ShuffleWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val WORK_KEY = "shuffle_worker"

@HiltViewModel
class StartViewModel @Inject constructor(
    private val cardDeckRepository: CardDeckRepository,
    private val workManager: WorkManager
): ViewModel(){
    private val _uiStateFlow = MutableStateFlow(StartViewState())
    val uiStateFlow: StateFlow<StartViewState> = _uiStateFlow

    init {
        createNewDeck()
        startWork()
    }

    fun createNewDeck() {
        viewModelScope.launch {
            cardDeckRepository.createNewDeck()
            val daoFlow = cardDeckRepository.gameFlow.first()
            _uiStateFlow.value = _uiStateFlow.value.copy(
                deckId = daoFlow.deckId,
                success = daoFlow.success
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

    fun startWork() {
        val shuffleWorkRequest = PeriodicWorkRequestBuilder<ShuffleWorker>(15, TimeUnit.MINUTES)
            .build()

        workManager.enqueueUniquePeriodicWork(
            WORK_KEY,
            ExistingPeriodicWorkPolicy.UPDATE,
            shuffleWorkRequest
        )
    }



    fun stopWork() {
        workManager.cancelUniqueWork(WORK_KEY)
    }

}