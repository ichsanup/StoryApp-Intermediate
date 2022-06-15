package com.dicoding.picodiploma.submissionaplikasistoryapp.pagging3

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dicoding.picodiploma.submissionaplikasistoryapp.api.API
import com.dicoding.picodiploma.submissionaplikasistoryapp.respones.ListStoryItem

class StoriesRepository (private val stories:StoriesDatabase, private val api:API){
    fun getQuote(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoriesPaggingSource(api)
            }
        ).liveData
    }
}