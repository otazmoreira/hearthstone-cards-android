package dev.tavieto.hearthstone.data.remote.core

import com.google.gson.Gson
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Retrofit {
    inline operator fun <reified T> invoke(
        baseUrl: String,
        interceptor: Interceptor?
    ): T {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(RetrofitClient(interceptor))
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create()
    }
}
