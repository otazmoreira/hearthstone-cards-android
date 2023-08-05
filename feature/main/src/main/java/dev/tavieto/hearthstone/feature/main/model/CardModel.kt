package dev.tavieto.hearthstone.feature.main.model

import android.os.Parcelable
import dev.tavieto.hearthstone.domain.cards.model.CardDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class CardModel(
    val artist: String,
    val attack: Int,
    val cardId: String,
    val cardSet: String,
    val playerClass: String,
    val collectible: Boolean,
    val cost: Int,
    val dbfId: Int,
    val elite: Boolean,
    val faction: String,
    val flavor: String,
    val health: Int,
    val img: String,
    val imgGold: String,
    val locale: String,
    val name: String,
    val race: String,
    val rarity: String,
    val text: String,
    val type: String
) : Parcelable {
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

    companion object {
        val mock = CardModel(
            artist = "Luca Zontini",
            attack = 0,
            cardId = "RLK_Prologue_DS1_185",
            cardSet = "Basic",
            cost = 1,
            dbfId = 100777,
            elite = false,
            flavor = "Magi conjured arcane arrows to sell to hunters, until hunters learned just enough magic to do it themselves.  The resulting loss of jobs sent Stormwind into a minor recession.",
            health = 0,
            locale = "enUS",
            name = "Arcane Shot",
            playerClass = "Hunter",
            text = "Deal \$2 damage.",
            type = "Spell",
            collectible = false,
            faction = "Neutral",
            img = "http://wow.zamimg.com/images/hearthstone/cards/enus/original/EX1_572.png",
            imgGold = "http://wow.zamimg.com/images/hearthstone/cards/enus/animated/EX1_572_premium.gif",
            race = "Minion",
            rarity = "Legendary"
        )
    }
}

internal fun CardDomain.mapToModel(): CardModel {
    return CardModel(
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

internal fun List<CardDomain>.mapToModel() = map { it.mapToModel() }
