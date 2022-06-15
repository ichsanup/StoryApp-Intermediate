package com.dicoding.picodiploma.submissionaplikasistoryapp.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.submissionaplikasistoryapp.model.Repository
import com.dicoding.picodiploma.submissionaplikasistoryapp.model.UserModel
import com.dicoding.picodiploma.submissionaplikasistoryapp.respones.ListStoryItem
import kotlinx.coroutines.launch

class MapsViewModel(private val repo:Repository): ViewModel() {
    val letMap: LiveData<List<ListStoryItem>> = repo.listmap

    fun getMaps(token: String) {
        viewModelScope.launch {
            repo.getStoryMap(token)
        }
    }

    fun getUser(): LiveData<UserModel> {
        return repo.getUser()
    }
}