package dev.tavieto.hearthstone.domain.cards.usecase

import dev.tavieto.hearthstone.core.commons.base.Either
import dev.tavieto.hearthstone.core.commons.base.UseCase
import dev.tavieto.hearthstone.domain.cards.model.CardDomain
import dev.tavieto.hearthstone.domain.cards.repository.CardsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class GetCardsUseCase(
    scope: CoroutineScope,
    private val repository: CardsRepository
) : UseCase<Unit, Unit>(scope) {
    override suspend fun run(params: Unit?): Flow<Either<Unit>> {
        return repository.getAll()
    }
}
