package dev.tavieto.hearthstone.data.remote.interceptor

import dev.tavieto.hearthstone.data.remote.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

internal class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader(name = "accept", value = "application/json")
            .addHeader(name = "X-RapidAPI-Key", value = BuildConfig.RAPID_API_KEY)
            .addHeader(name = "X-RapidAPI-Host", value = BuildConfig.RAPID_API_HOST)
            .build()
        return chain.proceed(newRequest)
    }
}
