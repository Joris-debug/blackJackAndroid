package com.example.blackjack.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.blackjack.ui.views.models.MainViewModel

@Composable
fun Start(modifier: Modifier = Modifier, viewModel: MainViewModel = hiltViewModel()) {
    val viewState = viewModel.uiStateFlow.collectAsState().value

    LazyColumn(
        modifier = modifier.padding(16.dp) // Padding für die gesamte LazyColumn
    ) {
        item {
            Text(
                text = "Deck created: ${viewState.success}",
                modifier = modifier
            )

            // Show the remaining cards in the deck
            Text(
                text = "Remaining cards: ${viewState.remaining}",
                modifier = modifier
            )
        }

        item {
            Button(
                onClick = {
                    viewModel.drawCard()
                }
            ) {
                Text(text = "Draw Card")
            }
        }
        item {
            if (viewState.cardsDrawn.isNotEmpty()) {
                Text(
                    text = "Cards Drawn:",
                    modifier = modifier
                )
                // Iterate over the cardsDrawn list and display each card code
                viewState.cardsDrawn.forEach { cardCode ->
                    Text(
                        text = cardCode,
                        modifier = modifier
                    )
                    val identifier = LocalContext.current.resources.
                        getIdentifier("img_${cardCode.lowercase()}.png", "drawable", LocalContext.current.packageName)

                    if(identifier != 0) {
                        Image(
                            painter = painterResource(id = identifier),
                            contentDescription = cardCode,
                            modifier = Modifier
                        )
                    } else {
                        Text("Image not found: img_${cardCode.lowercase()}.png")
                    }
                }
            }
        }

    }
}