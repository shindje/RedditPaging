package com.example.redditpaging.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.redditpaging.api.RedditService
import com.example.redditpaging.data.RedditRepository
import com.example.redditpaging.databinding.ActivitySearchBinding
import com.example.redditpaging.model.PostsSearchResult

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel
    private val adapter = PostsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val repo = RedditRepository(RedditService.create())
        viewModel = ViewModelProvider(this, ViewModelFactory(repo)).get(SearchViewModel::class.java)

        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(decoration)
        setupScrollListener()

        initAdapter()
    }

    private fun initAdapter() {
        binding.list.adapter = adapter
        viewModel.postResult.observe(this) { result ->
            when (result) {
                is PostsSearchResult.Success -> {
                    adapter.submitList(result.data)
                    adapter.notifyDataSetChanged()
                }
                is PostsSearchResult.Error -> {
                    Toast.makeText(
                        this,
                        "Error while getting posts",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun setupScrollListener() {
        val layoutManager = binding.list.layoutManager as LinearLayoutManager
        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                viewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })
    }
}
