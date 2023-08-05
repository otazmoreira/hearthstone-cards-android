package dev.tavieto.hearthstone.core.delegate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tavieto.hearthstone.core.commons.base.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.plus
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

inline fun <V, reified U : UseCase<*, *>> V.useCase(): Lazy<U> where V : ViewModel, V : KoinComponent {
    return inject { parametersOf(viewModelScope + Dispatchers.IO) }
}
