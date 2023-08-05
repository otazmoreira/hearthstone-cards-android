package dev.tavieto.hearthstone.data.local.datasource

import dev.tavieto.hearthstone.core.commons.base.Either
import dev.tavieto.hearthstone.core.commons.base.runCatchData
import dev.tavieto.hearthstone.data.local.dao.CardDao
import dev.tavieto.hearthstone.data.local.entity.mapToDomain
import dev.tavieto.hearthstone.data.local.entity.mapToEntity
import dev.tavieto.hearthstone.domain.cards.model.CardDomain
import dev.tavieto.hearthstone.repository.datasource.local.CardsLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class CardsLocalDataSourceImpl(
    private val dao: CardDao
) : CardsLocalDataSource {
    override fun getAll(): Flow<Either<List<CardDomain>>> = flow {
        emit(runCatchData {
            dao.getCards().mapToDomain()
        })
    }

    override fun getIsEmpty(): Boolean {
        return dao.getCards(start = 1, end = 2).isEmpty()
    }

    override fun saveCards(cards: List<CardDomain>): Either<Unit> {
        return runCatchData { dao.insertCards(cards.mapToEntity()) }
    }

    override fun getCards(page: Int, itemCount: Int): Either<List<CardDomain>> {
        val start = itemCount * page
        val end = (itemCount) * (page + 1)

        val result = runCatchData {
            dao.getCards(start = start, end = end).mapToDomain()
        }
        return result
    }
}
