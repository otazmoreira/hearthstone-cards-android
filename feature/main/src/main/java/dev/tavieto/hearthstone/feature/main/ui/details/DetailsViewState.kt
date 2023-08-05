package dev.tavieto.hearthstone.feature.main.ui.details

import dev.tavieto.hearthstone.feature.main.model.CardModel

data class DetailsViewState(
    val card: CardModel? = null,
    val isLoading: Boolean = true
)
