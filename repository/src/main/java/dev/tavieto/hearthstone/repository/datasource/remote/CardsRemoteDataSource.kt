package dev.tavieto.hearthstone.repository.datasource.remote

import dev.tavieto.hearthstone.core.commons.base.Either
import dev.tavieto.hearthstone.domain.cards.model.CardDomain
import kotlinx.coroutines.flow.Flow


interface CardsRemoteDataSource {
    suspend fun getAll(): Either<List<CardDomain>>
}
