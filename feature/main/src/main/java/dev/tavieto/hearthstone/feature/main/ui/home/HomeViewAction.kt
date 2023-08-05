package dev.tavieto.hearthstone.feature.main.ui.home

import dev.tavieto.hearthstone.feature.main.model.CardModel

sealed class HomeViewAction {
    object Get {
        data object Cards : HomeViewAction()
        object Page {
            data object Next : HomeViewAction()
        }
    }

    object Navigate {
        data class Details(val card: CardModel) : HomeViewAction()
    }

    object Set {
        data class Loading(val isLoading: Boolean): HomeViewAction()
    }
}
