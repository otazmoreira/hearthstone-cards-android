package dev.tavieto.hearthstone.core.commons.exception

class BadRequestException(
    override val message: String? = null
) : Throwable(message)
