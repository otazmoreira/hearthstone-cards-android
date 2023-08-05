package dev.tavieto.hearthstone.data.remote.model


import com.google.gson.annotations.SerializedName
import dev.tavieto.hearthstone.core.commons.extension.Empty
import dev.tavieto.hearthstone.domain.cards.model.CardDomain

internal data class CardResponse(
    @SerializedName("artist")
    val artist: String? = null,
    @SerializedName("attack")
    val attack: Int? = null,
    @SerializedName("cardId")
    val cardId: String? = null,
    @SerializedName("cardSet")
    val cardSet: String? = null,
    @SerializedName("playerClass")
    val playerClass: String? = null,
    @SerializedName("collectible")
    val collectible: Boolean? = null,
    @SerializedName("cost")
    val cost: Int? = null,
    @SerializedName("dbfId")
    val dbfId: Int? = null,
    @SerializedName("elite")
    val elite: Boolean? = null,
    @SerializedName("faction")
    val faction: String? = null,
    @SerializedName("flavor")
    val flavor: String? = null,
    @SerializedName("health")
    val health: Int? = null,
    @SerializedName("img")
    val img: String? = null,
    @SerializedName("imgGold")
    val imgGold: String? = null,
    @SerializedName("locale")
    val locale: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("race")
    val race: String? = null,
    @SerializedName("rarity")
    val rarity: String? = null,
    @SerializedName("text")
    val text: String? = null,
    @SerializedName("type")
    val type: String? = null
) {
    @Suppress("CyclomaticComplexMethod")
    fun mapToDomain(): CardDomain {
        return CardDomain(
            artist = artist ?: String.Empty,
            attack = attack ?: 0,
            cardId = cardId ?: String.Empty,
            cardSet = cardSet ?: String.Empty,
            collectible = this.collectible ?: false,
            cost = cost ?: 0,
            dbfId = dbfId ?: 0,
            elite = elite ?: false,
            faction = this.faction ?: String.Empty,
            flavor = flavor ?: String.Empty,
            health = health ?: 0,
            img = img ?: String.Empty,
            imgGold = imgGold ?: String.Empty,
            locale = locale ?: String.Empty,
            name = name ?: String.Empty,
            playerClass = playerClass ?: String.Empty,
            race = race ?: String.Empty,
            rarity = rarity ?: String.Empty,
            text = text ?: String.Empty,
            type = type ?: String.Empty
        )
    }
}

internal fun List<CardResponse>.mapToDomain() = map { it.mapToDomain() }
