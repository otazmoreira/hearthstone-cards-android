package dev.tavieto.hearthstone.feature.main.di

import dev.tavieto.hearthstone.feature.main.ui.details.DetailsViewModel
import dev.tavieto.hearthstone.feature.main.ui.home.HomeViewModel
import dev.tavieto.hearthstone.feature.main.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainFeatureModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
}
