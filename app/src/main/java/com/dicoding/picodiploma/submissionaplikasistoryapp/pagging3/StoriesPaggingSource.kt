package com.dicoding.picodiploma.submissionaplikasistoryapp.pagging3

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.picodiploma.submissionaplikasistoryapp.api.API
import com.dicoding.picodiploma.submissionaplikasistoryapp.respones.ListStoryItem

class StoriesPaggingSource(private val api: API): PagingSource<Int, ListStoryItem>() {
    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1) }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = api.getAllPaging("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXBwTUp4Tkd4VEtmcU5lWmEiLCJpYXQiOjE2NTM0NTM1NDV9.Abd8dh6JD7zCrrSbm58vQMXSJAPSVPqitM6RJHxuK3s",position, params.loadSize)
            LoadResult.Page(
                data = responseData.listStory,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.listStory.isNullOrEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}