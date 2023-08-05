package dev.tavieto.hearthstone.core.navigation.destination

import android.os.Parcelable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.gson.Gson
import dev.tavieto.hearthstone.core.commons.exception.InvalidIDException
import dev.tavieto.hearthstone.core.navigation.core.ParcelableNavType
import dev.tavieto.hearthstone.core.navigation.extension.encode

open class LeafDestination(
    val root: BranchDestination,
    private val route: String,
    private val args: List<NavArg> = emptyList()
) {

    val arguments: List<NamedNavArgument> = args.getNamedNavArguments()
    open fun createRoute(): String = "${root.route}/$route${args.mapArgsId()}"

    /**
     * @param args rules: Pair type is a short way to create a [Arg] where the Pair first arg
     * is the [Arg] id and the second arg is the [Arg] value. Ex.: putArgs(ARG_KEY to someValue)
     * */
    internal fun putArgs(vararg args: Pair<String, Any?>): String {
        var route = createRoute()
        val newArg: MutableList<Pair<String, Any>> = mutableListOf()
        args.forEach { it.filterPair(newArg::add) }
        newArg.forEach { route = route.putArg(Arg(id = it.first, value = it.second)) }
        return route
    }

    internal fun putArgs(vararg args: Arg): String {
        var route = createRoute()
        args.forEach { route = route.putArg(it) }
        return route
    }

    private fun Pair<String, Any?>.filterPair(getPair: (Pair<String, Any>) -> Unit) {
        second?.run { getPair(Pair(first, this)) }
        if (second == null) {
            this@LeafDestination.args.firstOrNull { arg ->
                arg.id == first
            }?.run {
                defaultValue?.run { getPair(Pair(first, this)) }
            }
        }
    }

    private fun String.putArg(arg: Arg): String {
        val containsId = contains(arg.id)

        if (containsId.not()) throw InvalidIDException("The ID <${arg.id}> do not exits.")

        return insertArgOnRoute(arg)
    }

    private fun String.insertArgOnRoute(arg: Arg): String {
        return replace("{${arg.id}}", "${arg.value.formatArg()}")
    }

    internal data class Arg(
        val id: String,
        var value: Any
    )
}

private fun Any.formatArg(): Any {
    return when (this) {
        is String -> this.encode()
        is Parcelable -> this.toGson()
        else -> this
    }
}

data class NavArg(
    val id: String,
    val type: NavType<*>,
    val defaultValue: Any? = null,
    val isNullable: Boolean = false
)

internal fun List<NavArg>.mapArgsId(): String {
    val mandatoryArgs = filter {
        (if (it.type is ParcelableNavType) it.type.isNullableAllowed else it.isNullable).not()
    }.map { it.id }
    val optionalArgs = filter {
        if (it.type is ParcelableNavType) it.type.isNullableAllowed else it.isNullable
    }.map { it.id }

    val mandatoryArgsRoute = mandatoryArgs.formatMandatoryArgsId()
    val optionalArgsRoute = optionalArgs.formatOptionalArgsId()

    return "${mandatoryArgsRoute}$optionalArgsRoute"
}

private fun List<NavArg>.getNamedNavArguments(): List<NamedNavArgument> {
    return map {
        navArgument(name = it.id) {
            type = it.type
            defaultValue?.let { value -> defaultValue = value }
            nullable =
                if (it.type is ParcelableNavType) it.type.isNullableAllowed else it.isNullable
        }
    }
}

private fun List<String>.formatMandatoryArgsId(): String = joinToString(separator = "") { "/{$it}" }
private fun List<String>.formatOptionalArgsId(): String {
    val args = joinToString(separator = "") { "$it={$it}" }
    return if (args.isEmpty()) args else "?$args"
}

fun Any.toGson(): String = Gson().toJson(this).encode()
