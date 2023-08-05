package dev.tavieto.hearthstone.core.navigation.routes

import dev.tavieto.hearthstone.core.navigation.core.ParcelableNavType
import dev.tavieto.hearthstone.core.navigation.core.Routes
import dev.tavieto.hearthstone.core.navigation.destination.BranchDestination
import dev.tavieto.hearthstone.core.navigation.destination.LeafDestination
import dev.tavieto.hearthstone.core.navigation.destination.NavArg
import dev.tavieto.hearthstone.feature.main.model.CardModel

object MainRoutes : Routes {
    override val branch: BranchDestination = BranchDestination.Main
    const val card_arg = "card_arg"

    object Splash : LeafDestination(root = branch, route = "splash")
    object Home : LeafDestination(root = branch, route = "home")
    object Details : LeafDestination(
        root = branch,
        route = "details",
        args = listOf(
            NavArg(id = card_arg, type = ParcelableNavType(CardModel::class.java))
        )
    ) {
        fun createRoute(card: CardModel): String {
            return putArgs(card_arg to card)
        }
    }
}
