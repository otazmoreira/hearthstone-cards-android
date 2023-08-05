package dev.tavieto.hearthstone.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import dev.tavieto.hearthstone.domain.cards.model.CardDomain

@Entity(tableName = "cards")
data class CardEntity(
    @ColumnInfo(name = "artist")
    val artist: String,
    @ColumnInfo(name = "attack")
    val attack: Int,
    @ColumnInfo(name = "cardId")
    val cardId: String,
    @ColumnInfo(name = "cardSet")
    val cardSet: String,
    @ColumnInfo(name = "playerClass")
    val playerClass: String,
    @ColumnInfo(name = "collectible")
    val collectible: Boolean,
    @ColumnInfo(name = "cost")
    val cost: Int,
    @ColumnInfo(name = "dbfId")
    val dbfId: Int,
    @ColumnInfo(name = "elite")
    val elite: Boolean,
    @ColumnInfo(name = "faction")
    val faction: String,
    @ColumnInfo(name = "flavor")
    val flavor: String,
    @ColumnInfo(name = "health")
    val health: Int,
    @ColumnInfo(name = "img")
    val img: String,
    @ColumnInfo(name = "imgGold")
    val imgGold: String,
    @ColumnInfo(name = "locale")
    val locale: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "race")
    val race: String,
    @ColumnInfo(name = "rarity")
    val rarity: String,
    @ColumnInfo(name = "text")
    val text: String,
    @ColumnInfo(name = "type")
    val type: String,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
) {
    fun mapToDomain(): CardDomain {
        return CardDomain(
            artist,
            attack,
            cardId,
            cardSet,
            playerClass,
            collectible,
            cost,
            dbfId,
            elite,
            faction,
            flavor,
            health,
            img,
            imgGold,
            locale,
            name,
            race,
            rarity,
            text,
            type
        )
    }
}

fun List<CardEntity>.mapToDomain(): List<CardDomain> {
    return this.map { it.mapToDomain() }
}

fun CardDomain.mapToEntity(): CardEntity {
    return CardEntity(
        artist,
        attack,
        cardId,
        cardSet,
        playerClass,
        collectible,
        cost,
        dbfId,
        elite,
        faction,
        flavor,
        health,
        img,
        imgGold,
        locale,
        name,
        race,
        rarity,
        text,
        type
    )
}

fun List<CardDomain>.mapToEntity(): List<CardEntity> {
    return this.map { it.mapToEntity() }
}
