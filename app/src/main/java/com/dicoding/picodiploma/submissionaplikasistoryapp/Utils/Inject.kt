package com.dicoding.picodiploma.submissionaplikasistoryapp.Utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.picodiploma.submissionaplikasistoryapp.api.APIConfig
import com.dicoding.picodiploma.submissionaplikasistoryapp.model.Repository
import com.dicoding.picodiploma.submissionaplikasistoryapp.model.UserPreference

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("token")

object Inject {
    fun repositoryProvide(context: Context): Repository {
        val preferences = UserPreference.getInstance(context.dataStore)
        val api = APIConfig.getApi()
        return Repository.getInstance(preferences, api)
    }
}
