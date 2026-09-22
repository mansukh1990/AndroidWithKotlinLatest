package com.example.animationandroid.sharepreference

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun setLogin(isLogin: Boolean) {
        editor.putBoolean(KEY_IS_LOGIN, isLogin)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGIN, false)
    }

    companion object {
        private const val PREF_NAME = "login"
        private const val KEY_IS_LOGIN = "flag"

    }
}
