package com.dicoding.picodiploma.submissionaplikasistoryapp.view.welcome

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.submissionaplikasistoryapp.Utils.Inject
import com.dicoding.picodiploma.submissionaplikasistoryapp.map.MapsViewModel
import com.dicoding.picodiploma.submissionaplikasistoryapp.model.Repository
import com.dicoding.picodiploma.submissionaplikasistoryapp.view.camera.CameraViewModel
import com.dicoding.picodiploma.submissionaplikasistoryapp.view.home.HomeViewModel
import com.dicoding.picodiploma.submissionaplikasistoryapp.view.login.LoginViewModel
import com.dicoding.picodiploma.submissionaplikasistoryapp.view.main.MainViewModel
import com.dicoding.picodiploma.submissionaplikasistoryapp.view.signup.SignupViewModel

class ViewModelFactory(private val repo: Repository) : ViewModelProvider.NewInstanceFactory() {


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repo) as T
            }
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(repo) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repo) as T
            }
            modelClass.isAssignableFrom(CameraViewModel::class.java) -> {
                CameraViewModel(repo) as T
            }
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(repo) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repo) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Inject.repositoryProvide(context))
            }.also { instance = it }
        }
    }
}