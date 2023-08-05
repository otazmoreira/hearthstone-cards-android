package dev.tavieto.hearthstone.data.remote.service

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface HearthstoneService {
    @GET(value = "/cards")
    suspend fun getAllCards(
        @Query("locale") locale: String = "ptBR"
    ): ResponseBody
}
