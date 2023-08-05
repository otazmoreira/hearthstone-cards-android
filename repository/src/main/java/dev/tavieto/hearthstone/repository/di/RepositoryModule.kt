package dev.tavieto.hearthstone.repository.di

import dev.tavieto.hearthstone.domain.cards.di.domainCardsModule
import dev.tavieto.hearthstone.domain.cards.repository.CardsRepository
import dev.tavieto.hearthstone.repository.repository.CardsRepositoryImpl
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val repositoryModule = module {
    single<CardsRepository> { CardsRepositoryImpl(get(), get()) }

    loadKoinModules(domainCardsModule)
}
