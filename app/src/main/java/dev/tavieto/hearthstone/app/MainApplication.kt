package dev.tavieto.hearthstone.app

import android.app.Application
import dev.tavieto.hearthstone.core.navigation.di.navigationModule
import dev.tavieto.hearthstone.data.local.di.localDataModule
import dev.tavieto.hearthstone.data.remote.di.remoteDataModule
import dev.tavieto.hearthstone.repository.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


// TODO - adicionar tela de splash para verificar se o app está fora do ar
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                listOf(
                    navigationModule,
                    localDataModule,
                    remoteDataModule,
                    repositoryModule
                )
            )
        }.androidContext(this)
    }
}