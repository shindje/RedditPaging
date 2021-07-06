package com.example.redditpaging.ui

import androidx.lifecycle.*
import com.example.redditpaging.data.RedditRepository
import com.example.redditpaging.model.PostsSearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: RedditRepository) : ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    val postResult: LiveData<PostsSearchResult> =
        liveData {
            val posts = repository.getSearchResultStream().asLiveData(Dispatchers.Main)
            emitSource(posts)
        }


    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            viewModelScope.launch {
                repository.searchNext()
            }
        }
    }
}
