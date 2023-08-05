package dev.tavieto.hearthstone.feature.main.ui.home

import androidx.lifecycle.ViewModel
import dev.tavieto.hearthstone.core.delegate.useCase
import dev.tavieto.hearthstone.domain.cards.model.CardPage
import dev.tavieto.hearthstone.domain.cards.usecase.GetCardsPageUseCase
import dev.tavieto.hearthstone.domain.cards.usecase.GetCardsUseCase
import dev.tavieto.hearthstone.feature.main.model.mapToModel
import dev.tavieto.hearthstone.feature.main.ui.home.HomeViewAction.Get
import dev.tavieto.hearthstone.feature.main.ui.home.HomeViewAction.Navigate
import dev.tavieto.hearthstone.feature.main.ui.home.HomeViewAction.Set
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent

class HomeViewModel(
    private val navigation: HomeNavigation
) : ViewModel(), KoinComponent {

    private val _state = MutableStateFlow(HomeViewState())
    val state = _state.asStateFlow()

    private val getCardsUseCase: GetCardsUseCase by useCase()
    private val getCardsPageUseCase: GetCardsPageUseCase by useCase()

    fun dispatchAction(action: HomeViewAction) {
        when (action) {
            is Get.Cards -> getCards()
            is Get.Page.First -> getFirstPage()
            is Get.Page.Next -> getNextPage()
            is Navigate.Details -> navigation.navigateToDetails(action.card)
            is Set.Loading -> setLoading(action.isLoading)
        }
    }

    private fun setLoading(isLoading: Boolean) {
        _state.update { it.copy(isLoading = isLoading) }
    }

    private fun getFirstPage() {

    }

    private fun getNextPage() {
        if (_state.value.isLoading.not()) {
            dispatchAction(Set.Loading(true))
            getCardsPageUseCase(
                params = CardPage(
                    page = _state.value.page + 1,
                    count = 30
                ),
                onSuccess = { cards ->
                    _state.update {
                        val cardList = it.cards.toMutableList()
                        cardList.addAll(cards.mapToModel())
                        it.copy(
                            cards = cardList,
                            page = _state.value.page + 1
                            )
                    }
                    dispatchAction(Set.Loading(false))
                },
                onFailure = { error ->
                    _state.update { it.copy(error = error) }
                    dispatchAction(Set.Loading(false))
                }
            )
        }
    }

    private fun getCards() {
        getCardsUseCase(

        )
    }
}
