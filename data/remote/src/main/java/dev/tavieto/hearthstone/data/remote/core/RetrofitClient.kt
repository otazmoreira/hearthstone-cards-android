package dev.tavieto.hearthstone.data.remote.core

import dev.tavieto.hearthstone.data.remote.BuildConfig
import java.time.Duration
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object RetrofitClient {
    operator fun invoke(
        interceptor: Interceptor?
    ): OkHttpClient {
        return OkHttpClient().newBuilder().apply {
            interceptor?.let { addInterceptor(it) }
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            }
            connectTimeout(5, TimeUnit.MINUTES)
            callTimeout(5, TimeUnit.MINUTES)
            readTimeout(5, TimeUnit.MINUTES)
            writeTimeout(5, TimeUnit.MINUTES)
        }.build()
    }
}
