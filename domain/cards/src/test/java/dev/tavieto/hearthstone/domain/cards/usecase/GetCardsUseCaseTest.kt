package dev.tavieto.hearthstone.domain.cards.usecase

import dev.tavieto.hearthstone.core.commons.base.Either
import dev.tavieto.hearthstone.domain.cards.repository.CardsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

internal class GetCardsUseCaseTest {
    private lateinit var useCase: GetCardsUseCase
    private val repository: CardsRepository = mockk()

    @Before
    fun setUp() {
        useCase = GetCardsUseCase(
            scope = CoroutineScope(Dispatchers.Unconfined),
            repository = repository
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test invoke useCase success`() = runTest {
        // GIVEN
        stubRepositorySuccess()

        // WHEN
        val result = useCase.run(Unit)

        // THEN
        coVerify(exactly = 1) { repository.getAll() }
        assert(result.last() is Either.Success)
        assert((result.last() as Either.Success).data == Unit)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test invoke useCase failure`() = runTest {
        // GIVEN
        val error = Throwable("custom error")
        stubRepositoryFailure(error)

        // WHEN
        val result = useCase.run(Unit)

        // THEN
        coVerify(exactly = 1) { repository.getAll() }
        assert(result.last() is Either.Failure)
        assert((result.last() as Either.Failure).error == error)
    }

    private fun stubRepositorySuccess() {
        coEvery { repository.getAll() } returns flowOf(Either.Success(Unit))
    }

    private fun stubRepositoryFailure(error: Throwable) {
        coEvery { repository.getAll() } returns flowOf(Either.Failure(error))
    }
}
