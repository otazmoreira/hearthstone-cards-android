package dev.tavieto.hearthstone.feature.main.ui.splash

import androidx.lifecycle.ViewModel
import dev.tavieto.hearthstone.core.delegate.useCase
import dev.tavieto.hearthstone.domain.cards.usecase.GetCardsUseCase
import dev.tavieto.hearthstone.feature.main.ui.splash.SplashViewAction.Get
import dev.tavieto.hearthstone.feature.main.ui.splash.SplashViewAction.Navigate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent

class SplashViewModel(
    private val navigation: SplashNavigation
): ViewModel(), KoinComponent {

    private val getCardsUseCase: GetCardsUseCase by useCase()

    fun dispatchAction(action: SplashViewAction) {
        when (action) {
            is Get.LoadAllCards -> getLoadAllCards()
            is Navigate.Home -> navigation.navigateToHome()
        }
    }
    private fun getLoadAllCards() {
        getCardsUseCase(
            onSuccess = {
                dispatchAction(Navigate.Home)
            },
            onFailure = {
                dispatchAction(Navigate.Home)
            }
        )
    }

    public override fun onCleared() {
        super.onCleared()
        getCardsUseCase.cancel()
    }
}
