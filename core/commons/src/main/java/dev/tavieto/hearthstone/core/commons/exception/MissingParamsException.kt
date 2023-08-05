package dev.tavieto.hearthstone.core.commons.exception

class MissingParamsException(
    override val message: String? = null
) : Throwable(message)
