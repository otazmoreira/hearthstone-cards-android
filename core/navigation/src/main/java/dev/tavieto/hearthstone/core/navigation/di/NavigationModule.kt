package dev.tavieto.hearthstone.core.navigation.di

import dev.tavieto.hearthstone.core.navigation.manager.NavigationManager
import dev.tavieto.hearthstone.core.navigation.navigation.MainNavigationImpl
import dev.tavieto.hearthstone.feature.main.di.mainFeatureModule
import dev.tavieto.hearthstone.feature.main.ui.details.DetailsNavigation
import dev.tavieto.hearthstone.feature.main.ui.home.HomeNavigation
import dev.tavieto.hearthstone.feature.main.ui.splash.SplashNavigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val navigationModule = module {
    single { NavigationManager(CoroutineScope(SupervisorJob() + Dispatchers.Main)) }

    single<SplashNavigation> { MainNavigationImpl(get()) }
    single<HomeNavigation> { MainNavigationImpl(get()) }
    single<DetailsNavigation> { MainNavigationImpl(get()) }

    loadKoinModules(mainFeatureModule)
}
