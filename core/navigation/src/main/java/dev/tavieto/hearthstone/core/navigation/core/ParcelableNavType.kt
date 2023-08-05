package dev.tavieto.hearthstone.core.navigation.core

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import dev.tavieto.hearthstone.core.navigation.extension.decode

internal class ParcelableNavType<T : Parcelable>(
    private val type: Class<T>,
    isNullable: Boolean = false,
) : NavType<T>(isNullableAllowed = isNullable) {

    override val name: String
        get() = type.name

    override fun get(bundle: Bundle, key: String): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, type)
        } else {
            bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): T {
        return Gson().fromJson(value.decode(), type)
    }

    override fun put(bundle: Bundle, key: String, value: T) = bundle.putParcelable(key, value)
}
