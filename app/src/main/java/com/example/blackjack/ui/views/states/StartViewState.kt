package com.example.blackjack.ui.views.states

data class StartViewStates(
    val success: Boolean = false,
    val deckId: String = "",
    val remaining: Int = 0,
    val shuffled: Boolean = false
)
