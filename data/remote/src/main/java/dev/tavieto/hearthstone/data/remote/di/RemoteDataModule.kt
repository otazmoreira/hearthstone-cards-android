package dev.tavieto.hearthstone.data.remote.di

import dev.tavieto.hearthstone.data.remote.BuildConfig
import dev.tavieto.hearthstone.data.remote.core.Retrofit
import dev.tavieto.hearthstone.data.remote.datasource.CardsRemoteDataSourceImpl
import dev.tavieto.hearthstone.data.remote.interceptor.HeaderInterceptor
import dev.tavieto.hearthstone.data.remote.service.HearthstoneService
import dev.tavieto.hearthstone.repository.datasource.remote.CardsRemoteDataSource
import okhttp3.Interceptor
import org.koin.dsl.module

val remoteDataModule = module {
    factory<Interceptor> { HeaderInterceptor() }

    factory<HearthstoneService> {
        Retrofit(
            baseUrl = BuildConfig.RAPID_API_URL,
            interceptor = get()
        )
    }

    single<CardsRemoteDataSource> { CardsRemoteDataSourceImpl(get()) }
}
