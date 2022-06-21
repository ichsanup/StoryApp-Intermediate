package com.dicoding.picodiploma.submissionaplikasistoryapp.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.submissionaplikasistoryapp.Utils.Event
import com.dicoding.picodiploma.submissionaplikasistoryapp.model.Repository
import com.dicoding.picodiploma.submissionaplikasistoryapp.model.UserModel
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: Repository) : ViewModel() {
    val isLoading: LiveData<Boolean> = repo.isLoading
    val toastText: LiveData<Event<String>> = repo.toastText

    fun getUser(): LiveData<UserModel> {
        return repo.getUser()
    }

    fun logout() {
        viewModelScope.launch {
            repo.logout()
        }
    }
}