package com.example.blackjack.workers


import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.blackjack.data.repositories.CardDeckRepository
import kotlinx.coroutines.coroutineScope


@HiltWorker
class ShuffleWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    val cardDeckRepository: CardDeckRepository
) : CoroutineWorker(context, workerParams) {

    companion object {
        val TAG = "WORKER"
    }

    override suspend fun doWork(): Result {
        coroutineScope {
            Log.d(TAG, "Worker routine successfully started")
            cardDeckRepository.createNewDeck()
        }
        return Result.success()
    }
}