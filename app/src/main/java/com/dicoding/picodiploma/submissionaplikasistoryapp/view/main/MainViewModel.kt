package com.dicoding.picodiploma.submissionaplikasistoryapp.view.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.submissionaplikasistoryapp.model.Repository
import kotlinx.coroutines.launch

class MainViewModel (private val repo:Repository):ViewModel(){

    fun logout() {
        viewModelScope.launch {
            repo.logout()
        }
    }
}