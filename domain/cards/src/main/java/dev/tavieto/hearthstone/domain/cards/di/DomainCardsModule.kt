package dev.tavieto.hearthstone.domain.cards.di

import dev.tavieto.hearthstone.domain.cards.usecase.FixCardDomainUseCase
import dev.tavieto.hearthstone.domain.cards.usecase.GetCardsPageUseCase
import dev.tavieto.hearthstone.domain.cards.usecase.GetCardsUseCase
import kotlinx.coroutines.CoroutineScope
import org.koin.dsl.module

val domainCardsModule = module {
    factory { (scope: CoroutineScope) ->
        GetCardsUseCase(scope, get())
    }
    factory {  (scope: CoroutineScope) ->
        GetCardsPageUseCase(scope, get())
    }
    factory {  (scope: CoroutineScope) ->
        FixCardDomainUseCase(scope)
    }
}
