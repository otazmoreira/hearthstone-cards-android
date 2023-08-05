package dev.tavieto.hearthstone.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.tavieto.hearthstone.data.local.dao.CardDao
import dev.tavieto.hearthstone.data.local.entity.CardEntity

@Database(entities = [CardEntity::class], version = 2, exportSchema = false)
abstract class CardsDatabase : RoomDatabase() {
    abstract fun cardsDao(): CardDao
}
