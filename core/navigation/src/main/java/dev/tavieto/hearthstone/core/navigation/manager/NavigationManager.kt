package dev.tavieto.hearthstone.core.navigation.manager

import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class NavigationManager(
    private val applicationScope: CoroutineScope
) {

    private val _commands: Channel<NavigationCommand> = Channel(Channel.BUFFERED)
    val commands = _commands.receiveAsFlow()

    private fun navigate(route: String, navOptions: NavOptions? = null) {
        applicationScope.launch {
            _commands.send(
                NavigationCommand.Navigate(
                    destination = route,
                    type = NavigationType.NavigateTo,
                    navOptions = navOptions
                )
            )
        }
    }

    fun navigate(
        route: String,
        builder: NavOptionsBuilder.() -> Unit = {
            launchSingleTop = true
        }
    ) {
        navigate(route, navOptions(builder))
    }

    fun navigateUp() {
        applicationScope.launch {
            _commands.send(NavigationCommand.NavigateUp)
        }
    }

    fun popStackTo(route: String, inclusive: Boolean) {
        applicationScope.launch {
            _commands.send(
                NavigationCommand.Navigate(
                    destination = route,
                    type = NavigationType.PopUpTo(inclusive)
                )
            )
        }
    }

    fun popBackStack() {
        applicationScope.launch {
            _commands.send(NavigationCommand.PopBackStack.Default)
        }
    }

    fun popBackStack(
        value: Map<String, Any>
    ) {
        applicationScope.launch {
            _commands.send(NavigationCommand.PopBackStack.Arguments(value))
        }
    }

    fun popUpTo(
        route: String,
        value: Map<String, Any>
    ) {
        applicationScope.launch {
            _commands.send(NavigationCommand.PopBackStack.Arguments(value, route))
        }
    }
}

sealed class NavigationType {

    object NavigateTo : NavigationType()

    class PopUpTo(val inclusive: Boolean) : NavigationType()
}

sealed class NavigationCommand {

    data class Navigate(
        val destination: String,
        val navOptions: NavOptions? = null,
        val type: NavigationType,
    ) : NavigationCommand()

    object NavigateUp : NavigationCommand()

    sealed class PopBackStack : NavigationCommand() {

        object Default : PopBackStack()

        data class Arguments(
            val value: Map<String, Any>,
            val route: String? = null
        ) : PopBackStack()
    }
}
