package dev.tavieto.hearthstone.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.tavieto.hearthstone.core.navigation.AppNavigation
import dev.tavieto.hearthstone.core.navigation.manager.NavigationCommand
import dev.tavieto.hearthstone.core.navigation.manager.NavigationManager
import dev.tavieto.hearthstone.core.navigation.manager.NavigationType
import dev.tavieto.hearthstone.core.uikit.theme.HearthstoneTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainActivity : ComponentActivity(), KoinComponent {

    private val navManager: NavigationManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            HearthstoneTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AppNavigation(navController = navController)
                }
            }

            LaunchedEffect(Unit) {
                observeAndNavigate(navManager, navController)
            }
        }
    }
    private fun observeAndNavigate(
        navManager: NavigationManager,
        navController: NavController
    ) {
        lifecycleScope.launch {
            navManager.commands.collectLatest { command ->
                when (command) {
                    is NavigationCommand.Navigate -> navigate(command, navController)
                    is NavigationCommand.NavigateUp -> navController.navigateUp()
                    is NavigationCommand.PopBackStack.Arguments -> popBackStack(
                        command,
                        navController
                    )

                    is NavigationCommand.PopBackStack.Default -> navController.popBackStack()
                }
            }
        }
    }

    private fun navigate(
        command: NavigationCommand.Navigate,
        navController: NavController
    ) {
        when (val type = command.type) {
            is NavigationType.NavigateTo -> navController.navigate(
                route = command.destination,
                navOptions = command.navOptions
            )

            is NavigationType.PopUpTo -> navController.popBackStack(
                route = command.destination,
                inclusive = type.inclusive
            )
        }
    }

    private fun popBackStack(
        command: NavigationCommand.PopBackStack.Arguments,
        navController: NavController
    ) {
        if (command.route.isNullOrBlank()) {
            navController.previousBackStackEntry?.savedStateHandle?.let { savedState ->
                for ((key, data) in command.value) {
                    savedState[key] = data
                }
            }
            navController.popBackStack()
        } else {
            val route = command.route ?: ""

            navController.getBackStackEntry(route).savedStateHandle.let { savedState ->
                for ((key, data) in command.value) {
                    savedState[key] = data
                }
            }
            navController.popBackStack(route = route, inclusive = false)
        }
    }
}
