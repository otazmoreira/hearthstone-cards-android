package dev.tavieto.hearthstone.data.remote.datasource

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.tavieto.hearthstone.core.commons.base.Either
import dev.tavieto.hearthstone.core.commons.base.mapCatching
import dev.tavieto.hearthstone.core.commons.base.runCatchSuspendData
import dev.tavieto.hearthstone.data.remote.model.CardResponse
import dev.tavieto.hearthstone.data.remote.service.HearthstoneService
import dev.tavieto.hearthstone.data.remote.core.NetworkWrapper
import dev.tavieto.hearthstone.data.remote.model.mapToDomain
import dev.tavieto.hearthstone.domain.cards.model.CardDomain
import dev.tavieto.hearthstone.repository.datasource.remote.CardsRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class CardsRemoteDataSourceImpl(
    private val service: HearthstoneService
) : CardsRemoteDataSource {
    override suspend fun getAll(): Either<List<CardDomain>>  {
        return runCatchSuspendData {
                val json = NetworkWrapper.getJson { service.getAllCards() }
                val mapTypeToken = object : TypeToken<Map<String, List<CardResponse>>>() {}
                val mapType = mapTypeToken.type
                val cardSets: Map<String, List<CardResponse>> = Gson().fromJson(json, mapType)
                cardSets
            }.mapCatching {  map ->
                map.flatMap { (_, cardSet) ->
                    cardSet.mapToDomain()
                }
            }

    }
}
