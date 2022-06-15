package com.dicoding.picodiploma.submissionaplikasistoryapp.view.detail

import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.submissionaplikasistoryapp.respones.ListStoryItem

class DetailViewModel: ViewModel() {
    lateinit var storyItem : ListStoryItem

    fun setDetailStory(story : ListStoryItem): ListStoryItem{
        storyItem = story
        return storyItem
    }
}