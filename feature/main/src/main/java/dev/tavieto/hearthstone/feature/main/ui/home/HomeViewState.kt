package dev.tavieto.hearthstone.feature.main.ui.home


import dev.tavieto.hearthstone.feature.main.model.CardModel

data class HomeViewState(
    val page: Int = 0,
    val totalItems: Int = 0,
    val cards: List<CardModel> = emptyList(),
    val error: Throwable? = null,
    val isLoading: Boolean = false
)
