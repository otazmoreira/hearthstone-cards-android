package dev.tavieto.hearthstone.data.remote.core

import dev.tavieto.hearthstone.core.commons.exception.BadRequestException
import dev.tavieto.hearthstone.core.commons.exception.MissingBodyResponseException
import dev.tavieto.hearthstone.core.commons.exception.NotAuthorizedException
import dev.tavieto.hearthstone.core.commons.exception.NotFoundException
import dev.tavieto.hearthstone.core.commons.exception.TimeOutException
import dev.tavieto.hearthstone.core.commons.exception.UnknownCodeException
import dev.tavieto.hearthstone.core.commons.extension.Empty
import java.io.BufferedReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

object NetworkWrapper {
    suspend operator fun <T> invoke(
        request: suspend () -> Call<T>
    ): T = request().execute().let { response ->
        return when {
            response.isSuccessful.not() -> throw handleException(response)
            else -> response.body() ?: throw MissingBodyResponseException()
        }
    }

    suspend fun getJson(
       request: suspend  () -> ResponseBody
    ): String {
        val result = request()
        var json = String.Empty
        val bufferedReader = BufferedReader(result.charStream())

        var line: String?
        withContext(Dispatchers.IO) {
            while (bufferedReader.readLine().also { line = it } != null) {
                json += line
            }
        }

        return json
    }

    private fun handleException(response: Response<*>): Throwable {
        return when (response.code()) {
            StatusCode.BAD_REQUEST -> BadRequestException()
            StatusCode.NOT_AUTHORIZED -> NotAuthorizedException()
            StatusCode.NOT_FOUND -> NotFoundException()
            StatusCode.TIMEOUT -> TimeOutException()
            else -> UnknownCodeException()
        }
    }

    private object StatusCode {
        const val BAD_REQUEST = 400
        const val NOT_AUTHORIZED = 401
        const val NOT_FOUND = 404
        const val TIMEOUT = 408
    }
}
