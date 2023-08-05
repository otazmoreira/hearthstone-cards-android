package dev.tavieto.hearthstone.feature.main.ui.details

import dev.tavieto.hearthstone.feature.main.model.CardModel

sealed class DetailsViewAction {
    sealed class Navigate : DetailsViewAction() {
        object PopBackStack : Navigate()
    }

    sealed class Set : DetailsViewAction() {
        data class Card(val card: CardModel?) : Set()
    }
}
