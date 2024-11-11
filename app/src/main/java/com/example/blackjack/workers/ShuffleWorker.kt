package com.example.blackjack.workers


import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.blackjack.data.repositories.CardDeckRepository
import com.example.blackjack.ui.views.models.StartViewModel
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject


@HiltWorker
class ShuffleWorker @AssistedInject constructor(
    @Assisted context: Context,                          // Kontext über den Standardkonstruktor
    @Assisted workerParams: WorkerParameters,            // WorkerParameters ebenfalls injizieren
    val cardDeckRepository: CardDeckRepository  // Abhängigkeit injizieren
) : CoroutineWorker(context, workerParams) {

    companion object {
        val TAG = "WORKER"
    }

    override suspend fun doWork(): Result {
        coroutineScope {
            Log.d(TAG, "klappt")
            val deck = cardDeckRepository.createNewDeck()
            Log.d(TAG, "Deck ID is ${deck.deckId}")
        }
        return Result.success()
    }
}