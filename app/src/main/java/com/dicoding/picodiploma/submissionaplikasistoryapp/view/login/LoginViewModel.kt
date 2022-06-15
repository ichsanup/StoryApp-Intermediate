package com.dicoding.picodiploma.submissionaplikasistoryapp.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.submissionaplikasistoryapp.Utils.Event
import com.dicoding.picodiploma.submissionaplikasistoryapp.model.Repository
import com.dicoding.picodiploma.submissionaplikasistoryapp.model.UserModel
import com.dicoding.picodiploma.submissionaplikasistoryapp.respones.LoginResponses
import kotlinx.coroutines.launch

class LoginViewModel(private val repo: Repository) : ViewModel() {
    val responseLogin: LiveData<LoginResponses> = repo.responseLogin
    val isLoading: LiveData<Boolean> = repo.isLoading
    val toastText: LiveData<Event<String>> = repo.toastText

    fun postLogin(email: String, password: String) {
        viewModelScope.launch {
            repo.postLogin(email, password)
        }
    }

    fun saveUser(session: UserModel) {
        viewModelScope.launch {
            repo.saveUser(session)
        }
    }

    fun login() {
        viewModelScope.launch {
            repo.login()
        }
    }
}