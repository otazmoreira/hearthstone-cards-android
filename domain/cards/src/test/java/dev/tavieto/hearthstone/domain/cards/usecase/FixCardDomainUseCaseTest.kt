package dev.tavieto.hearthstone.domain.cards.usecase

import dev.tavieto.hearthstone.core.commons.base.Either
import dev.tavieto.hearthstone.core.commons.exception.MissingParamsException
import dev.tavieto.hearthstone.domain.cards.model.CardDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

internal class FixCardDomainUseCaseTest {
    private lateinit var useCase: FixCardDomainUseCase

    @Before
    fun setUp() {
        useCase = FixCardDomainUseCase(
            scope = CoroutineScope(Dispatchers.Unconfined)
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test(
        // THEN
        expected = MissingParamsException::class
    )
    fun `test null param useCase must be failure`() = runTest {
        // WHEN
        useCase.run(null)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test useCase success`() = runTest {
        // WHEN
        val result = useCase.run(CardDomain.mockToFix)

        // THEN
        assert(result.last() is Either.Success)
        assert((result.last() as Either.Success).data == CardDomain.mock)
    }
}

val CardDomain.Companion.mockToFix: CardDomain
    get() = CardDomain(
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
        img = "http://wow.zamimg.com/images/hearthstone/cards/enus/original/EX1_572.png",
        imgGold = "http://wow.zamimg.com/images/hearthstone/cards/enus/animated/EX1_572_premium.gif",
        race = "Minion",
        rarity = "Legendary"
    )
