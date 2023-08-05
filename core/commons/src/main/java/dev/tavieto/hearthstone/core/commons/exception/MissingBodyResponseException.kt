package dev.tavieto.hearthstone.core.commons.exception

class MissingBodyResponseException(
    override val message: String? = null
) : Throwable(message)
