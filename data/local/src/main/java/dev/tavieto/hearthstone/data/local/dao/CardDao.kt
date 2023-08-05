package dev.tavieto.hearthstone.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.tavieto.hearthstone.data.local.entity.CardEntity

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCards(cards: List<CardEntity>)

    @Query(value = "SELECT * FROM cards")
    fun getCards(): List<CardEntity>

    @Query(value = "SELECT * FROM cards WHERE id >= :start AND id <= :end")
    fun getCards(start: Int, end: Int): List<CardEntity>

    @Query(value = "DELETE FROM cards")
    fun deleteAll()
}
