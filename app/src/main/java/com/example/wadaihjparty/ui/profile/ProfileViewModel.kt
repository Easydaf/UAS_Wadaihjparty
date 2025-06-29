package com.example.wadaihjparty.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wadaihjparty.data.repository.AuthRepository
import com.example.wadaihjparty.domain.model.UserProfile
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _userProfile = MutableLiveData<UserProfile?>()
    val userProfile: LiveData<UserProfile?> = _userProfile
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    init {
        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        val userId = repository.getCurrentUserId()

        if (userId != null) {
            viewModelScope.launch {
                val profile = repository.getUserProfile(userId)
                _userProfile.postValue(profile)
            }
        } else {
            _status.postValue("Tidak ada pengguna yang login.")
        }
    }
}