package dev.tavieto.hearthstone.domain.cards.usecase

import dev.tavieto.hearthstone.core.commons.base.Either
import dev.tavieto.hearthstone.core.commons.base.UseCase
import dev.tavieto.hearthstone.core.commons.base.runCatchData
import dev.tavieto.hearthstone.core.commons.exception.MissingParamsException
import dev.tavieto.hearthstone.core.commons.extension.checkAndFixUrlScheme
import dev.tavieto.hearthstone.domain.cards.model.CardDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class FixCardDomainUseCase(
    scope: CoroutineScope
): UseCase<CardDomain, CardDomain>(scope) {
    override suspend fun run(
        params: CardDomain?
    ): Flow<Either<CardDomain>> {
        if (params == null) throw MissingParamsException()

        return flow {
            emit(
                runCatchData {
                    params.copy(
                        img = params.img.checkAndFixUrlScheme(),
                        imgGold = params.imgGold.checkAndFixUrlScheme()
                    )
                }
            )
        }
    }
}
