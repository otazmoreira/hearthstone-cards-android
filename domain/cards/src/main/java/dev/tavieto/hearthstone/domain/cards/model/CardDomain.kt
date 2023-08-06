package dev.tavieto.hearthstone.domain.cards.model

data class CardDomain(
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
) {
    companion object {
        val mock = CardDomain(
            artist = "Luca Zontini",
            attack = 0,
            cardId = "RLK_Prologue_DS1_185",
            cardSet = "Basic",
            cost = 1,
            dbfId = 100777,
            elite = false,
            flavor = "Magi conjured arcane arrows to sell to hunters, until hunters learned " +
                    "just enough magic to do it themselves.  The resulting loss of jobs sent " +
                    "Stormwind into a minor recession.",
            health = 0,
            locale = "enUS",
            name = "Arcane Shot",
            playerClass = "Hunter",
            text = "Deal \$2 damage.",
            type = "Spell",
            collectible = false,
            faction = "Neutral",
            img = "https://wow.zamimg.com/images/hearthstone/cards/enus/original/EX1_572.png",
            imgGold = "https://wow.zamimg.com/images/hearthstone/cards/enus/animated/EX1_572_premium.gif",
            race = "Minion",
            rarity = "Legendary"
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CardDomain

        if (artist != other.artist) return false
        if (attack != other.attack) return false
        if (cardId != other.cardId) return false
        if (cardSet != other.cardSet) return false
        if (playerClass != other.playerClass) return false
        if (collectible != other.collectible) return false
        if (cost != other.cost) return false
        if (dbfId != other.dbfId) return false
        if (elite != other.elite) return false
        if (faction != other.faction) return false
        if (flavor != other.flavor) return false
        if (health != other.health) return false
        if (img != other.img) return false
        if (imgGold != other.imgGold) return false
        if (locale != other.locale) return false
        if (name != other.name) return false
        if (race != other.race) return false
        if (rarity != other.rarity) return false
        if (text != other.text) return false
        if (type != other.type) return false

        return true
    }
}
