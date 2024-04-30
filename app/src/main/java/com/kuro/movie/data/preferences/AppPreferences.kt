package com.kuro.movie.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import javax.inject.Inject

class AppPreferences @Inject constructor(val context: Context) {
    companion object {
        private const val APP_PREFERENCES_NAME = "Kuro-Movie"
        private const val MODE = Context.MODE_PRIVATE

        private const val UI_MODE_KEY = "ui_mode_key"
    }

    private val appPreferences: SharedPreferences =
        context.getSharedPreferences(APP_PREFERENCES_NAME, MODE)


    /**
     * SharedPreferences extension function, so we won't need to call edit() and apply()
     * ourselves on every SharedPreferences operation.
     */
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    fun putInt(key: String, value: Int) = appPreferences.edit {
        it.putInt(key, value)
    }

    fun getInt(key: String, defaultValue: Int = 0) = appPreferences.getInt(key, defaultValue)

    var uiMode: Int
        get() = getInt(UI_MODE_KEY, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        set(value) = putInt(UI_MODE_KEY, value)

    fun clearPreferences() {
        appPreferences.edit {
            it.clear().apply()
        }
    }
}