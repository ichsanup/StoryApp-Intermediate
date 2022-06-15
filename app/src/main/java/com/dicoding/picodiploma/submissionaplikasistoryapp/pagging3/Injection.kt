package com.dicoding.picodiploma.submissionaplikasistoryapp.pagging3

import android.content.Context
import com.dicoding.picodiploma.submissionaplikasistoryapp.api.APIConfig

object Injection {
    fun provideRepository(context: Context): StoriesRepository {
        val database = StoriesDatabase.getDatabase(context)
        val apiService = APIConfig.getApi()
        return StoriesRepository(database, apiService)
    }
}