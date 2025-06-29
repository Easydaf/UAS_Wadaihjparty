package com.example.wadaihjparty.data.repository

import android.util.Log
import com.example.wadaihjparty.domain.model.UserProfile
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val firebaseAuth: FirebaseAuth = Firebase.auth
    private val firestore = Firebase.firestore
    private val TAG = "AuthRepository"

    fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }


    suspend fun registerUserAndCreateProfile(name: String, email: String, phone: String, password: String): AuthResult {
        val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        Log.d(TAG, "Auth user created with UID: ${authResult.user?.uid}")
        authResult.user?.let {
            val userProfile = UserProfile(uid = it.uid, name = name, email = email, phone = phone)
            firestore.collection("users").document(it.uid).set(userProfile).await()
        }
        return authResult
    }

    suspend fun loginUser(email: String, password: String): AuthResult {
        return firebaseAuth.signInWithEmailAndPassword(email, password).await()
    }

    suspend fun sendPasswordResetEmail(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).await()
    }

    suspend fun getUserProfile(uid: String): UserProfile? {
        val userId = firebaseAuth.currentUser?.uid
        if (userId == null) {
            Log.d(TAG, "getUserProfile: Gagal, tidak ada pengguna yang login.")
            return null
        }
        Log.d(TAG, "Mencoba mengambil profil untuk UID: $userId")
        return try {
            val documentSnapshot = firestore.collection("users").document(userId).get().await()
            val profile = documentSnapshot.toObject<UserProfile>()
            if (profile != null) {
                Log.d(TAG, "Profil ditemukan: ${profile.name}")
            } else {
                Log.d(TAG, "Dokumen ditemukan, tapi gagal di-map ke UserProfile.")
            }
            profile
            firestore.collection("users").document(uid).get().await().toObject<UserProfile>()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updateUserProfile(uid: String, newName: String, newPhone: String) {
        val userUpdates = mapOf(
            "name" to newName,
            "phone" to newPhone
        )
        firestore.collection("users").document(uid).update(userUpdates).await()
    }

    fun logout() {
        firebaseAuth.signOut()
    }
}