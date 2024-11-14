package com.example.blackjack.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.squareup.moshi.Moshi
import com.example.blackjack.api.DeckOfCardsApi
import com.example.blackjack.data.database.GameDatabase
import com.example.blackjack.data.repositories.CardDeckRepository
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext

import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object repomodule {

    data class RetroFitHolder(val cardDeckRetrofit: Retrofit, val quotesRetrofit: Retrofit)

    @Singleton
    @Provides
    fun providesCardDeckRepository(api: DeckOfCardsApi, gameDb: GameDatabase): CardDeckRepository {
        return CardDeckRepository(api, gameDb.gameDao)
    }

    @Provides
    fun providesMoshi(): Moshi {

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return moshi
    }

    @Provides
    fun providesAPI(moshi: Moshi): DeckOfCardsApi {

        val retrofit = Retrofit.Builder()
            .baseUrl(DeckOfCardsApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create(DeckOfCardsApi::class.java)
    }

    @Provides
    fun providesDatabase(@ApplicationContext context: Context): GameDatabase {
        return Room.databaseBuilder(
            context, GameDatabase::class.java, "gamedatabase"
        ).build()
    }

    @Provides
    fun providesWorkmanager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }
}