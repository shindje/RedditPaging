package com.example.redditpaging.data

import com.example.redditpaging.api.RedditService
import com.example.redditpaging.model.Post
import com.example.redditpaging.model.PostsSearchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import retrofit2.HttpException
import java.io.IOException

class RedditRepository(private val service: RedditService) {
    private val data = mutableListOf<Post>()
    private val searchResults = MutableSharedFlow<PostsSearchResult>(replay = 1)
    private var lastAfterPost = ""
    private var isRequestInProgress = false

    suspend fun getSearchResultStream(): Flow<PostsSearchResult> {
        lastAfterPost = ""
        data.clear()
        search()
        return searchResults
    }

    suspend fun searchNext() {
        if (isRequestInProgress) return
        search()
    }

    private suspend fun search(): Boolean {
        isRequestInProgress = true
        var successful = false

        try {
            val response = service.searchPosts(lastAfterPost, "")
            val posts = response.data.children
            data.addAll(posts)
            searchResults.emit(PostsSearchResult.Success(data))
            successful = true
            lastAfterPost = posts[posts.size - 1].data.name
        } catch (exception: IOException) {
            searchResults.emit(PostsSearchResult.Error(exception))
        } catch (exception: HttpException) {
            searchResults.emit(PostsSearchResult.Error(exception))
        }
        isRequestInProgress = false
        return successful
    }
}
