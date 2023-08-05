package dev.tavieto.hearthstone.repository.repository

import dev.tavieto.hearthstone.core.commons.base.Either
import dev.tavieto.hearthstone.core.commons.base.collectData
import dev.tavieto.hearthstone.core.commons.base.onFailure
import dev.tavieto.hearthstone.core.commons.base.onFailureSuspend
import dev.tavieto.hearthstone.core.commons.base.onSuccess
import dev.tavieto.hearthstone.core.commons.base.onSuccessSuspend
import dev.tavieto.hearthstone.domain.cards.model.CardDomain
import dev.tavieto.hearthstone.domain.cards.repository.CardsRepository
import dev.tavieto.hearthstone.repository.datasource.local.CardsLocalDataSource
import dev.tavieto.hearthstone.repository.datasource.remote.CardsRemoteDataSource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

internal class CardsRepositoryImpl(
    private val remote: CardsRemoteDataSource,
    private val local: CardsLocalDataSource
) : CardsRepository {
    override suspend fun getAll(): Flow<Either<Unit>> = flow {
        if (local.getIsEmpty()) {
            remote.getAll()
                .onSuccessSuspend {
                    local.saveCards(it.shuffled())
                    emit(Either.Success(Unit))
                }
                .onFailureSuspend {
                    emit(Either.Failure(it))
                }
        } else {
            emit(Either.Success(Unit))
        }
    }

    override suspend fun getCards(
        page: Int,
        itemCount: Int
    ): Flow<Either<List<CardDomain>>> = callbackFlow {
        val localResult = local.getCards(page, itemCount)
        localResult
            .onSuccessSuspend { cards ->
                if (cards.isEmpty()) {
                    getRemoteCards(
                        page = page,
                        itemCount = itemCount,
                        onSuccess = { trySend(it) },
                        onFailure = { trySend(it) }
                    )
                } else {
                    trySend(Either.Success(cards))
                }
            }
            .onFailureSuspend { _ ->
                getRemoteCards(
                    page = page,
                    itemCount = itemCount,
                    onSuccess = { trySend(it) },
                    onFailure = { trySend(it) }
                )
            }
        awaitClose()
    }

    private suspend fun getRemoteCards(
        page: Int,
        itemCount: Int,
        onSuccess: (Either<List<CardDomain>>) -> Unit,
        onFailure: (Either.Failure) -> Unit
    ) {
        remote.getAll()
            .onSuccess { cards ->
                local.saveCards(cards.shuffled())
                    .onSuccess {
                        onSuccess(local.getCards(page, itemCount))
                    }
                    .onFailure { error ->
                        onFailure(Either.Failure(error))
                    }
            }
            .onFailure { error ->
                onFailure(Either.Failure(error))
            }
    }
}