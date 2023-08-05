package dev.tavieto.hearthstone.domain.cards.usecase

import dev.tavieto.hearthstone.core.commons.base.Either
import dev.tavieto.hearthstone.core.commons.base.UseCase
import dev.tavieto.hearthstone.core.commons.exception.MissingParamsException
import dev.tavieto.hearthstone.domain.cards.model.CardDomain
import dev.tavieto.hearthstone.domain.cards.model.CardPage
import dev.tavieto.hearthstone.domain.cards.repository.CardsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class GetCardsPageUseCase(
    scope: CoroutineScope,
    private val repository: CardsRepository
) : UseCase<CardPage, List<CardDomain>>(scope) {
    override suspend fun run(params: CardPage?): Flow<Either<List<CardDomain>>> {
        if (params == null) throw MissingParamsException()
        return repository.getCards(page = params.page, itemCount = params.count)
    }
}