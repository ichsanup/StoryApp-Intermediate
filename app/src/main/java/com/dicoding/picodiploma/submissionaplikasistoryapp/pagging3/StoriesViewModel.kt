package com.dicoding.picodiploma.submissionaplikasistoryapp.pagging3

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.picodiploma.submissionaplikasistoryapp.respones.ListStoryItem

class StoriesViewModel (storiesRepository: StoriesRepository):ViewModel(){
    val stories: LiveData<PagingData<ListStoryItem>> = storiesRepository.getQuote().cachedIn(viewModelScope)
}

class StoriesViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoriesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StoriesViewModel(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}