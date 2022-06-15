package com.dicoding.picodiploma.submissionaplikasistoryapp.view.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.submissionaplikasistoryapp.Utils.Event
import com.dicoding.picodiploma.submissionaplikasistoryapp.model.Repository
import com.dicoding.picodiploma.submissionaplikasistoryapp.respones.ApiResponses
import kotlinx.coroutines.launch

class SignupViewModel(private val repo: Repository) : ViewModel() {
    val responeRegister: LiveData<ApiResponses> = repo.responseRegister
    val isLoading: LiveData<Boolean> = repo.isLoading
    val toastText: LiveData<Event<String>> = repo.toastText

    fun postRegister(name: String, email: String, password: String) {
        viewModelScope.launch {
            repo.postRegister(name, email, password)
        }
    }
}
