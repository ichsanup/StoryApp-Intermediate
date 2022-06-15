package com.dicoding.picodiploma.submissionaplikasistoryapp.Utils

open class Event<out T>(private val content: T) {
    @Suppress("MemberVisibilityHaveToBePrivate")
    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}