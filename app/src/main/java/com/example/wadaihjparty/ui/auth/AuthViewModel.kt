package com.example.wadaihjparty.ui.auth

import android.annotation.SuppressLint
import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.wadaihjparty.data.manager.NetworkUtils
import com.example.wadaihjparty.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.launch

sealed class AuthState {
    object Loading : AuthState()
    data class Success(val type: String) : AuthState()
    data class Error(val message: String) : AuthState()
}
class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AuthRepository()
    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    @SuppressLint("NewApi")
    fun register(name: String, email: String, phone: String, password: String) {
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Semua kolom wajib diisi.")
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _authState.value = AuthState.Error("Format email tidak valid.")
            return
        }
        if (password.length < 6) {
            _authState.value = AuthState.Error("Password minimal 6 karakter.")
            return
        }

        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                repository.registerUserAndCreateProfile(name, email, phone, password)
                _authState.value = AuthState.Success("Register")
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is FirebaseAuthUserCollisionException -> "Email ini sudah terdaftar."
                    else -> "Registrasi Gagal: ${e.message}"
                }
                _authState.value = AuthState.Error(errorMessage)
            }
        }
    }

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email dan Password wajib diisi.")
            return
        }
        if (!NetworkUtils.isInternetAvailable(getApplication())) {
            _authState.value = AuthState.Error("Tidak ada koneksi internet.")
            return
        }
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                repository.loginUser(email, password)
                _authState.value = AuthState.Success("Login")
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Login gagal: Email atau Password salah.")
            }
        }
    }

    fun sendPasswordReset(email: String) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _authState.value = AuthState.Error("Harap masukkan format email yang valid.")
            return
        }
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                repository.sendPasswordResetEmail(email)
                _authState.value = AuthState.Success("Email reset password telah dikirim.")
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Gagal mengirim email.")
            }
        }
    }
}