package dev.tavieto.hearthstone.core.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.tavieto.hearthstone.core.navigation.routes.MainRoutes
import dev.tavieto.hearthstone.feature.main.model.CardModel
import dev.tavieto.hearthstone.feature.main.ui.details.DetailsScreen
import dev.tavieto.hearthstone.feature.main.ui.home.HomeScreen
import dev.tavieto.hearthstone.feature.main.ui.splash.SplashScreen
import org.koin.androidx.compose.getViewModel

internal fun NavGraphBuilder.addMainGraph() {
    navigation(
        route = MainRoutes.branch.route,
        startDestination = MainRoutes.Splash.createRoute()
    ) {
        addHomeScreen()
        addDetailsScreen()
        addSplashScreen()
    }
}

private fun NavGraphBuilder.addSplashScreen() {
    composable(
        route = MainRoutes.Splash.createRoute()
    ) {
        SplashScreen(getViewModel())
    }
}


private fun NavGraphBuilder.addHomeScreen() {
    composable(
        route = MainRoutes.Home.createRoute()
    ) {
        HomeScreen(getViewModel())
    }
}

private fun NavGraphBuilder.addDetailsScreen() {
    composable(
        route = MainRoutes.Details.createRoute(),
        arguments = MainRoutes.Details.arguments
    ) {
        val card = it.arguments?.getParcelable<CardModel>(MainRoutes.card_arg)
        DetailsScreen(
            card = card,
            viewModel = getViewModel()
        )
    }
}
