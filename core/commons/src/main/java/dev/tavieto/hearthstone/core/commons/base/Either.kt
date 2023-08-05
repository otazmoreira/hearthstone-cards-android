package dev.tavieto.hearthstone.core.commons.base

import kotlinx.coroutines.flow.Flow

sealed class Either<out T> {

    val isSuccess get() = this is Success
    val isFailure get() = this is Failure

    data class Success<T>(val data: T) : Either<T>()
    data class Failure(val error: Throwable) : Either<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Failure -> "Either.Failure(error = ${this.error})"
            is Success -> "Either.Success(data = ${this.data})"
        }
    }
}

fun <T> Either<T>.onSuccess(action: (T) -> Unit): Either<T> {
    Result.success(true).isSuccess
    if (this is Either.Success) action(this.data)
    return this
}

suspend fun <T> Either<T>.onSuccessSuspend(action: suspend (T) -> Unit): Either<T> {
    Result.success(true).isSuccess
    if (this is Either.Success) action(this.data)
    return this
}

fun <T> Either<T>.onFailure(action: (exception: Throwable) -> Unit): Either<T> {
    if (this is Either.Failure) action(this.error)
    return this
}

suspend fun <T> Either<T>.onFailureSuspend(action: suspend (exception: Throwable) -> Unit): Either<T> {
    if (this is Either.Failure) action(this.error)
    return this
}

fun <T, R> Either<T>.mapCatching(transform: (data: T) -> R): Either<R> {
    return when (this) {
        is Either.Success -> runCatchData { transform(data) }
        is Either.Failure -> Either.Failure(error)
    }
}

fun <T> runCatchData(block: () -> T): Either<T> {
    return try {
        Either.Success(block())
    } catch (error: Throwable) {
        Either.Failure(error)
    }
}

suspend fun <T> runCatchSuspendData(block: suspend () -> T): Either<T> {
    return try {
        Either.Success(block())
    } catch (error: Throwable) {
        Either.Failure(error)
    }
}

suspend fun <T> Flow<Either<T>>.collectData(
    onSuccess: (T) -> Unit = {},
    onFailure: (Throwable) -> Unit = {}
) {
    collect { result ->
        when (result) {
            is Either.Failure -> onFailure(result.error)
            is Either.Success -> onSuccess(result.data)
        }
    }
}
