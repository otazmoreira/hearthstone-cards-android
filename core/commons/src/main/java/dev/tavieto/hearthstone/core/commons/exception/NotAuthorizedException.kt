package dev.tavieto.hearthstone.core.commons.exception

class NotAuthorizedException(
    override val message: String? = null,
    override val cause: Throwable? = null
) : Throwable(message)
