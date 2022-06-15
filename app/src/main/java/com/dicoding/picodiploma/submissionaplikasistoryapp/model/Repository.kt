package com.dicoding.picodiploma.submissionaplikasistoryapp.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.dicoding.picodiploma.submissionaplikasistoryapp.Utils.Event
import com.dicoding.picodiploma.submissionaplikasistoryapp.api.API
import com.dicoding.picodiploma.submissionaplikasistoryapp.respones.AllStoriesResponses
import com.dicoding.picodiploma.submissionaplikasistoryapp.respones.ApiResponses
import com.dicoding.picodiploma.submissionaplikasistoryapp.respones.ListStoryItem
import com.dicoding.picodiploma.submissionaplikasistoryapp.respones.LoginResponses
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository private constructor(
    private val pref: UserPreference,
    private val api: API
) {
    private val _responseRegister = MutableLiveData<ApiResponses>()
    val responseRegister: LiveData<ApiResponses> = _responseRegister

    private val _responseLogin = MutableLiveData<LoginResponses>()
    val responseLogin: LiveData<LoginResponses> = _responseLogin

    private val _responseUpload = MutableLiveData<ApiResponses>()
    val responseUpload: LiveData<ApiResponses> = _responseUpload

    private val _list = MutableLiveData<AllStoriesResponses>()
    val list: LiveData<AllStoriesResponses> = _list

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    private val _listMap = MutableLiveData<List<ListStoryItem>>()
    val listmap: LiveData<List<ListStoryItem>> = _listMap

    fun postRegister(name: String, email: String, password: String) {
        _isLoading.value = true
        val client = api.postRegister(name, email, password)

        client.enqueue(object : Callback<ApiResponses> {
            override fun onResponse(
                call: Call<ApiResponses>,
                response: Response<ApiResponses>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _responseRegister.value = response.body()
                    _toastText.value = Event(response.body()?.message.toString())
                } else {
                    _toastText.value = Event(response.message().toString())
                    Log.e(
                        TAG,
                        "onFailure: ${response.message()}, ${response.body()?.message.toString()}"
                    )
                }
            }

            override fun onFailure(call: Call<ApiResponses>, t: Throwable) {
                _toastText.value = Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun postLogin(email: String, password: String) {
        _isLoading.value = true
        val client = api.postLogin(email, password)

        client.enqueue(object : Callback<LoginResponses> {
            override fun onResponse(
                call: Call<LoginResponses>,
                response: Response<LoginResponses>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _responseLogin.value = response.body()
                    _toastText.value = Event(response.body()?.message.toString())
                } else {
                    _toastText.value = Event(response.message().toString())
                    Log.e(
                        TAG,
                        "onFailure: ${response.message()}, ${response.body()?.message.toString()}"
                    )
                }
            }

            override fun onFailure(call: Call<LoginResponses>, t: Throwable) {
                _toastText.value = Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getAllStories(token: String) {
        _isLoading.value = true
        val client = api.getAllStories(token)

        client.enqueue(object : Callback<AllStoriesResponses> {
            override fun onResponse(
                call: Call<AllStoriesResponses>,
                response: Response<AllStoriesResponses>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _list.value = response.body()
                } else {
                    _toastText.value = Event(response.message().toString())
                    Log.e(
                        TAG,
                        "onFailure: ${response.message()}, ${response.body()?.message.toString()}"
                    )
                }
            }

            override fun onFailure(call: Call<AllStoriesResponses>, t: Throwable) {
                _toastText.value = Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun addStories(token: String, file: MultipartBody.Part, description: RequestBody) {
        _isLoading.value = true
        val client = api.postAddStories(token, file, description)

        client.enqueue(object : Callback<ApiResponses> {
            override fun onResponse(
                call: Call<ApiResponses>,
                response: Response<ApiResponses>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _responseUpload.value = response.body()
                    _toastText.value = Event(response.body()?.message.toString())
                } else {
                    _toastText.value = Event(response.message().toString())
                    Log.e(
                        TAG,
                        "onFailure: ${response.message()}, ${response.body()?.message.toString()}"
                    )
                }
            }

            override fun onFailure(call: Call<ApiResponses>, t: Throwable) {
                Log.d("error upload", t.message.toString())
            }

        }
        )
    }

    fun getStoryMap(token: String) {
        val client = api.getStoryBasedLocation(token)

        client.enqueue(object : Callback<AllStoriesResponses> {
            override fun onResponse(
                call: Call<AllStoriesResponses>,
                response: Response<AllStoriesResponses>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val value = response.body()
                    _listMap.value = value?.listStory
                } else {
                    _toastText.value = Event(response.message().toString())
                    Log.e(
                        TAG,
                        "onFailure: ${response.message()}, ${response.body()?.message.toString()}"
                    )
                }
            }

            override fun onFailure(call: Call<AllStoriesResponses>, t: Throwable) {
                _toastText.value = Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    suspend fun saveUser(session: UserModel) {
        pref.saveUser(session)
    }

    suspend fun login() {
        pref.login()
    }

    suspend fun logout() {
        pref.logout()
    }

    companion object {
        private const val TAG = "StoryRepository"

        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            preferences: UserPreference,
            apiService: API
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(preferences, apiService)
            }.also { instance = it }
    }
}