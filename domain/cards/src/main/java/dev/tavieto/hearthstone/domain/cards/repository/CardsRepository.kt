package dev.tavieto.hearthstone.domain.cards.repository

import dev.tavieto.hearthstone.core.commons.base.Either
import dev.tavieto.hearthstone.domain.cards.model.CardDomain
import kotlinx.coroutines.flow.Flow

interface CardsRepository {
    suspend fun getAll(): Flow<Either<Unit>>
    suspend fun getCards(page: Int, itemCount: Int): Flow<Either<List<CardDomain>>>
}
