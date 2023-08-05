package dev.tavieto.hearthstone.feature.main.ui.splash

sealed class SplashViewAction {
    sealed class Get : SplashViewAction() {
        data object LoadAllCards : Get()
    }
    sealed class Navigate : SplashViewAction(){
        data object Home : Navigate()
    }
}
