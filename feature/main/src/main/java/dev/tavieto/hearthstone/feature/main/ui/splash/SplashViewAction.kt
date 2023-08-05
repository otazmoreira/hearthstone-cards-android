package dev.tavieto.hearthstone.feature.main.ui.splash

sealed class SplashViewAction {
    sealed class Get : SplashViewAction() {
        object LoadAllCards : Get()
    }
    sealed class Navigate : SplashViewAction(){
        object Home : Navigate()
    }
}