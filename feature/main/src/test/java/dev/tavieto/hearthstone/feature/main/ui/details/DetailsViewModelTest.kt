package dev.tavieto.hearthstone.feature.main.ui.details

import dev.tavieto.hearthstone.domain.cards.model.CardDomain
import dev.tavieto.hearthstone.domain.cards.usecase.FixCardDomainUseCase
import dev.tavieto.hearthstone.feature.main.model.CardModel
import dev.tavieto.hearthstone.feature.main.ui.details.DetailsViewAction.Navigate
import dev.tavieto.hearthstone.feature.main.ui.details.DetailsViewAction.Set
import io.mockk.every
import io.mockk.invoke
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module


internal class DetailsViewModelTest {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var action: (DetailsViewAction) -> Unit
    private val navigation: DetailsNavigation = mockk()
    private val useCase: FixCardDomainUseCase = mockk()
    private val detailsModule = module { single { useCase } }

    @Before
    fun setUp() {
        startKoin { modules(detailsModule) }
        viewModel = DetailsViewModel(navigation)
        action = viewModel::dispatchAction
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test dispatchAction with Navigate_PopBackStack`() {
        // GIVEN
        navigationStub()

        // WHEN
        action(Navigate.PopBackStack)

        // THEN
        verify(exactly = 1) { navigation.popBackStack() }
    }

    @Test
    fun `test dispatchAction with Set_Card success`() {
        // GIVEN
        useCaseSuccessStub()

        // WHEN
        action(Set.Card(CardModel.mock))

        // THEN
        verify(exactly = 1) { useCase(params = any(), onSuccess = any(), onFailure = any()) }

        val card = viewModel.state.value.card

        assert(card != null)
        assert(card?.img == CardDomain.mock.img)
        assert(card?.imgGold == CardDomain.mock.imgGold)
        assert(card?.artist == CardModel.mock.artist)
        assert(card?.attack == CardModel.mock.attack)
        assert(card?.cardId == CardModel.mock.cardId)
        assert(card?.cardSet == CardModel.mock.cardSet)
        assert(card?.playerClass == CardModel.mock.playerClass)
        assert(card?.collectible == CardModel.mock.collectible)
        assert(card?.cost == CardModel.mock.cost)
        assert(card?.dbfId == CardModel.mock.dbfId)
        assert(card?.elite == CardModel.mock.elite)
        assert(card?.faction == CardModel.mock.faction)
        assert(card?.flavor == CardModel.mock.flavor)
        assert(card?.health == CardModel.mock.health)
        assert(card?.locale == CardModel.mock.locale)
        assert(card?.name == CardModel.mock.name)
        assert(card?.race == CardModel.mock.race)
        assert(card?.rarity == CardModel.mock.rarity)
        assert(card?.text == CardModel.mock.text)
        assert(card?.type == CardModel.mock.type)
    }

    @Test
    fun `test send null to dispatchAction with Set_Card success`() {
        // WHEN
        action(Set.Card(null))

        // THEN
        assert(viewModel.state.value.card == null)
    }

    @Test
    fun `test dispatchAction with Set_Card failure`() {
        // GIVEN
        useCaseFailureStub()

        // WHEN
        action(Set.Card(CardModel.mock))

        // THEN
        verify(exactly = 1) { useCase(params = any(), onSuccess = any(), onFailure = any()) }

        assert(viewModel.state.value.card == null)
    }

    @Test
    fun onCleared() {
        // GIVEN
        useCaseCanceledStub()

        // WHEN
        viewModel.onCleared()

        // THEN
        verify(exactly = 1) { useCase.cancel() }
    }

    private fun navigationStub() {
        every { navigation.popBackStack() } returns Unit
    }

    private fun useCaseSuccessStub() {
        every {
            useCase(
                params = any(),
                onSuccess = captureLambda(),
                onFailure = any()
            )
        } answers {
            lambda<(CardDomain) -> Unit>().invoke(CardDomain.mock)
        }
    }

    private fun useCaseFailureStub() {
        every {
            useCase(
                params = any(),
                onSuccess = any(),
                onFailure = captureLambda()
            )
        } answers {
            lambda<(Throwable) -> Unit>().invoke(Throwable())
        }
    }

    private fun useCaseCanceledStub() {
        every { useCase.cancel() } returns Unit
    }
}