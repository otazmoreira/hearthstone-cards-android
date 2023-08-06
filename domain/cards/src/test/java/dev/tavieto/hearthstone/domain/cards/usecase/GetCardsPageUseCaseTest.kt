package dev.tavieto.hearthstone.domain.cards.usecase

import dev.tavieto.hearthstone.core.commons.base.Either
import dev.tavieto.hearthstone.core.commons.exception.MissingParamsException
import dev.tavieto.hearthstone.domain.cards.model.CardDomain
import dev.tavieto.hearthstone.domain.cards.model.CardPage
import dev.tavieto.hearthstone.domain.cards.repository.CardsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

internal class GetCardsPageUseCaseTest {
    private lateinit var useCase: GetCardsPageUseCase
    private val repository: CardsRepository = mockk()

    @Before
    fun setUp() {
        useCase = GetCardsPageUseCase(
            scope = CoroutineScope(Dispatchers.Unconfined),
            repository = repository
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
        // GIVEN
        stubRepositorySuccess()
        val pageInfo = CardPage(page = 1, count = 3)
        // WHEN
        val result = useCase.run(pageInfo)

        // THEN
        val last = result.last()
        assert(last is Either.Success)

        val lastSuccess = last as Either.Success
        assert(lastSuccess.data.size == pageInfo.count)
        assert(lastSuccess.data[0].cardId == "1")
        assert(lastSuccess.data[1].cardId == "2")
        assert(lastSuccess.data[2].cardId == "3")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test useCase failure`() = runTest {
        // GIVEN
        val error = Throwable("custom error")
        val pageInfo = CardPage(page = 1, count = 3)
        stubRepositoryFailure(error)

        // WHEN
        val result = useCase.run(pageInfo)

        // THEN
        val last = result.last()
        assert(last is Either.Failure)
        assert((last as Either.Failure).error == error)
    }

    private fun stubRepositorySuccess() {
        coEvery {
            repository.getCards(page = any(), itemCount = any())
        } returns flowOf(Either.Success(CardDomain.listMock(1, 3)))
    }

    private fun stubRepositoryFailure(error: Throwable) {
        coEvery {
            repository.getCards(page = any(), itemCount = any())
        } returns flowOf(Either.Failure(error))
    }
}

fun CardDomain.Companion.listMock(page: Int, count: Int): List<CardDomain> {
    val finalItem = page * count
    val pageList = mutableListOf<CardDomain>()
    var counter = count - 1
    while (counter >= 0) {
        pageList.add(mock.copy(cardId = "${finalItem - counter}"))
        counter--
    }
    return pageList
}
