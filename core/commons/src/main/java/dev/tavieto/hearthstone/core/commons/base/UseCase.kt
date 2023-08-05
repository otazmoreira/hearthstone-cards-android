package dev.tavieto.hearthstone.core.commons.base

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class UseCase<in Params, out T>(
    private val scope: CoroutineScope
) {

    abstract suspend fun run(params: Params?): Flow<Either<T>>

    operator fun invoke(
        params: Params? = null,
        onFailure: (Throwable) -> Unit = {},
        onSuccess: (T) -> Unit = {}
    ) {
        scope.launch(handleError(onFailure)) {
            run(params).collectData(
                onSuccess = onSuccess,
                onFailure = onFailure
            )
        }
    }

    private fun handleError(onError: (Throwable) -> Unit): CoroutineContext {
        return CoroutineExceptionHandler { _, throwable -> onError(throwable) }
    }

    fun cancel() = scope.coroutineContext.cancelChildren()
}
