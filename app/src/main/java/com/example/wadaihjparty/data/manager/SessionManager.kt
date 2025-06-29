package com.example.wadaihjparty.data.manager

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth

object SessionManager {
    private const val PREFS_NAME = "WadaiHjPartySession"
    private const val KEY_ONBOARDING_COMPLETED = "onboardingCompleted"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun isLoggedIn(): Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }


    fun setOnboardingCompleted(context: Context, isCompleted: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_ONBOARDING_COMPLETED, isCompleted).apply()
    }


    fun isOnboardingCompleted(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_ONBOARDING_COMPLETED, false)
    }

    fun clearAllSession(context: Context) {
        getPrefs(context).edit().clear().apply()
    }
}