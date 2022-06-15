package com.dicoding.picodiploma.submissionaplikasistoryapp.view.camera


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.submissionaplikasistoryapp.Utils.Event
import com.dicoding.picodiploma.submissionaplikasistoryapp.model.Repository
import com.dicoding.picodiploma.submissionaplikasistoryapp.model.UserModel
import com.dicoding.picodiploma.submissionaplikasistoryapp.respones.ApiResponses
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class CameraViewModel(private val repo: Repository) : ViewModel() {
    val responeUpload: LiveData<ApiResponses> = repo.responseUpload
    val isLoading: LiveData<Boolean> = repo.isLoading
    val toastText: LiveData<Event<String>> = repo.toastText

    fun storyUpload(token: String, file: MultipartBody.Part, description: RequestBody) {
        viewModelScope.launch {
            repo.addStories(token, file, description)
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repo.getUser()
    }
}