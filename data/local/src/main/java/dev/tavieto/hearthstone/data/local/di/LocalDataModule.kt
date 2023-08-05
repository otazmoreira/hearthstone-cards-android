package dev.tavieto.hearthstone.data.local.di

import androidx.room.Room
import dev.tavieto.hearthstone.data.local.database.CardsDatabase
import dev.tavieto.hearthstone.data.local.datasource.CardsLocalDataSourceImpl
import dev.tavieto.hearthstone.repository.datasource.local.CardsLocalDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localDataModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            CardsDatabase::class.java,
            "cards_database"
        ).build()
    }

    single {
        val db = get<CardsDatabase>()
        db.cardsDao()
    }

    single<CardsLocalDataSource> { CardsLocalDataSourceImpl(get()) }
}
