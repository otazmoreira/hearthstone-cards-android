package dev.tavieto.hearthstone.feature.main.ui.splash

import dev.tavieto.hearthstone.domain.cards.usecase.GetCardsUseCase
import dev.tavieto.hearthstone.feature.main.ui.splash.SplashNavigation
import dev.tavieto.hearthstone.feature.main.ui.splash.SplashViewAction
import dev.tavieto.hearthstone.feature.main.ui.splash.SplashViewAction.Get
import dev.tavieto.hearthstone.feature.main.ui.splash.SplashViewAction.Navigate
import dev.tavieto.hearthstone.feature.main.ui.splash.SplashViewModel
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


internal class SplashViewModelTest {

    private lateinit var viewModel: SplashViewModel
    private lateinit var action: (SplashViewAction) -> Unit
    private val navigation: SplashNavigation = mockk()
    private val useCase: GetCardsUseCase = mockk()
    private val splashModule = module { single { useCase } }

    @Before
    fun setUp() {
        startKoin { modules(splashModule) }
        viewModel = SplashViewModel(navigation)
        action = viewModel::dispatchAction
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test dispatchAction with Navigate_Home`() {
        // GIVEN
        navigationStub()

        // WHEN
        action(Navigate.Home)

        // THEN
        verify(exactly = 1) { navigation.navigateToHome() }
    }

    @Test
    fun `test dispatchAction with Get_LoadAllCards success`() {
        // GIVEN
        useCaseSuccessStub()
        navigationStub()

        // WHEN
        action(Get.LoadAllCards)

        // THEN
        verify(exactly = 1) { useCase.invoke(onSuccess = any(), onFailure = any()) }
        verify(exactly = 1) { action(Navigate.Home) }
    }

    @Test
    fun `test dispatchAction with Get_LoadAllCards failure`() {
        // GIVEN
        useCaseFailureStub()
        navigationStub()

        // WHEN
        action(Get.LoadAllCards)

        // THEN
        verify(exactly = 1) { useCase.invoke(onSuccess = any(), onFailure = any()) }
        verify(exactly = 1) { action(Navigate.Home) }
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
        every { navigation.navigateToHome() } returns Unit
    }
    private fun useCaseSuccessStub() {
        every {
            useCase(
                onSuccess = captureLambda(),
                onFailure = any()
            )
        } answers {
            lambda<(Unit) -> Unit>().invoke(Unit)
        }
    }

    private fun useCaseFailureStub() {
        every {
            useCase(
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
