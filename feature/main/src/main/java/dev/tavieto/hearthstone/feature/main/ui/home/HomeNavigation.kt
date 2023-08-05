package dev.tavieto.hearthstone.feature.main.ui.home

import dev.tavieto.hearthstone.feature.main.model.CardModel

interface HomeNavigation {
    fun navigateToDetails(card: CardModel)
}
