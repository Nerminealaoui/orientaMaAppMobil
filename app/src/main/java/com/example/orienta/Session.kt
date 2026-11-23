package com.example.orienta

import android.content.Context

object Session {
    const val EXTRA_GO_QUESTIONNAIRE = "go_questionnaire"
    private const val PREFS = "orienta_prefs"

    private fun p(c: Context) = c.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun isLoggedIn(c: Context) = p(c).getBoolean("logged_in", false)
    fun setLoggedIn(c: Context, v: Boolean) = p(c).edit().putBoolean("logged_in", v).apply()

    fun saveString(c: Context, key: String, value: String) =
        p(c).edit().putString(key, value).apply()

    fun saveSet(c: Context, key: String, value: Set<String>) =
        p(c).edit().putStringSet(key, value).apply()
}