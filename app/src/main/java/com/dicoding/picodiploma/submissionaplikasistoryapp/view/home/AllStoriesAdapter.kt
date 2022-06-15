package com.dicoding.picodiploma.submissionaplikasistoryapp.view.home

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.submissionaplikasistoryapp.R
import com.dicoding.picodiploma.submissionaplikasistoryapp.Utils.withDateFormat
import com.dicoding.picodiploma.submissionaplikasistoryapp.databinding.ItemRowStoryBinding
import com.dicoding.picodiploma.submissionaplikasistoryapp.respones.ListStoryItem
import com.dicoding.picodiploma.submissionaplikasistoryapp.view.detail.DetailStoryActivity
import com.dicoding.picodiploma.submissionaplikasistoryapp.view.detail.DetailStoryActivity.Companion.EXTRA_DATA

class AllStoriesAdapter :
    PagingDataAdapter<ListStoryItem, AllStoriesAdapter.ListViewHolder>(DIFF_CALLBACK) {

    inner class ListViewHolder(private val binding: ItemRowStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: ListStoryItem) {
            binding.apply {
                txtNamastory.text = story.name
                txtDetailstory.text = story.description
                txtWaktudibuat.text = story.createdAt.withDateFormat()
                Glide.with(itemView.context)
                    .load(story.photoUrl)
                    .fitCenter()
                    .apply(
                        RequestOptions
                            .placeholderOf(R.drawable.loading)
                            .error(R.drawable.error)
                    ).into(imgRowstory)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailStoryActivity::class.java)
                    intent.putExtra(EXTRA_DATA, story)

                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair(imgRowstory, "story"),
                            Pair(txtNamastory, "name"),
                            Pair(txtDetailstory, "desc")
                        )
                    itemView.context.startActivity(intent, optionsCompat.toBundle())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemRowStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem,
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}