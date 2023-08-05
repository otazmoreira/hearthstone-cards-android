package dev.tavieto.hearthstone.feature.main.ui.details

import androidx.lifecycle.ViewModel
import dev.tavieto.hearthstone.core.delegate.useCase
import dev.tavieto.hearthstone.domain.cards.usecase.FixCardDomainUseCase
import dev.tavieto.hearthstone.feature.main.model.CardModel
import dev.tavieto.hearthstone.feature.main.model.mapToModel
import dev.tavieto.hearthstone.feature.main.ui.details.DetailsViewAction.Navigate
import dev.tavieto.hearthstone.feature.main.ui.details.DetailsViewAction.Set
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent

class DetailsViewModel(
    private val navigation: DetailsNavigation
) : ViewModel(), KoinComponent {

    private val _state = MutableStateFlow(DetailsViewState())
    val state = _state.asStateFlow()

    private val fixCardUseCase: FixCardDomainUseCase by useCase()

    fun dispatchAction(action: DetailsViewAction) {
        when (action) {
            is Navigate.PopBackStack -> navigation.popBackStack()
            is Set.Card -> setCard(action.card)
        }
    }

    private fun setCard(card: CardModel?) {
        if (card == null) {
            _state.update { it.copy(card = null) }
        } else {
            fixCardUseCase(
                params = card.mapToDomain(),
                onSuccess = { newCard ->
                    _state.update { it.copy(card = newCard.mapToModel()) }
                },
                onFailure = { _ ->
                    _state.update { it.copy(card = null) }
                }
            )
        }
    }

    public override fun onCleared() {
        super.onCleared()
        fixCardUseCase.cancel()
    }
}
