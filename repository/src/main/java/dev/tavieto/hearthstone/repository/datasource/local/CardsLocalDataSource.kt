package dev.tavieto.hearthstone.repository.datasource.local

import dev.tavieto.hearthstone.core.commons.base.Either
import dev.tavieto.hearthstone.domain.cards.model.CardDomain
import kotlinx.coroutines.flow.Flow

interface CardsLocalDataSource {
    fun getAll(): Flow<Either<List<CardDomain>>>
    fun getIsEmpty(): Boolean
    fun saveCards(cards: List<CardDomain>): Either<Unit>
    fun getCards(page: Int, itemCount: Int): Either<List<CardDomain>>
}