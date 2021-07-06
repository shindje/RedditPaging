package com.example.redditpaging.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.redditpaging.R
import com.example.redditpaging.model.Post

class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val title: TextView = view.findViewById(R.id.title)
    private val stars: TextView = view.findViewById(R.id.stars)
    private val comments: TextView = view.findViewById(R.id.comments)

    private var post: Post? = null

    fun bind(post: Post?) {
        if (post != null) {
            this.post = post
            title.text = post.data.title
            stars.text = post.data.totalAwardsReceived.toString()
            comments.text = post.data.numComments.toString()
        }
    }

    companion object {
        fun create(parent: ViewGroup): PostViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.post_view_item, parent, false)
            return PostViewHolder(view)
        }
    }
}
