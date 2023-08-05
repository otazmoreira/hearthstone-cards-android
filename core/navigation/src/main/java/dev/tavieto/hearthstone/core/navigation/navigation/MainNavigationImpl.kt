package dev.tavieto.hearthstone.core.navigation.navigation

import dev.tavieto.hearthstone.core.navigation.manager.NavigationManager
import dev.tavieto.hearthstone.core.navigation.routes.MainRoutes
import dev.tavieto.hearthstone.feature.main.model.CardModel
import dev.tavieto.hearthstone.feature.main.ui.details.DetailsNavigation
import dev.tavieto.hearthstone.feature.main.ui.home.HomeNavigation
import dev.tavieto.hearthstone.feature.main.ui.splash.SplashNavigation

internal class MainNavigationImpl(
    private val navManager: NavigationManager
) : SplashNavigation,
    HomeNavigation,
    DetailsNavigation {
    override fun navigateToDetails(card: CardModel) {
        navManager.navigate(route = MainRoutes.Details.createRoute(card))
    }

    override fun popBackStack() {
        navManager.popBackStack()
    }

    override fun navigateToHome() {
        navManager.navigate(route = MainRoutes.Home.createRoute())
    }
}
