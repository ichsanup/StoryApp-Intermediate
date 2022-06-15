package com.dicoding.picodiploma.submissionaplikasistoryapp.view.detail

import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.submissionaplikasistoryapp.R
import com.dicoding.picodiploma.submissionaplikasistoryapp.Utils.Utils
import com.dicoding.picodiploma.submissionaplikasistoryapp.databinding.ActivityDetailStoryBinding
import com.dicoding.picodiploma.submissionaplikasistoryapp.respones.ListStoryItem
import java.util.*

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding
    private lateinit var story: ListStoryItem
    private val vm: DetailViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
        setupData()

        story = intent.getParcelableExtra(EXTRA_DATA)!!
        vm.setDetailStory(story)
        displayResult()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun displayResult() {
        with(binding){
            txtWaktudibuat.text = getString(R.string.Created, Utils.formatData(vm.storyItem.createdAt,
                TimeZone.getDefault().id ))
        }
    }

    private fun setupData() {
        val data = intent.getParcelableExtra<ListStoryItem>(EXTRA_DATA) as ListStoryItem
        binding.apply {
            txtNamadetail.text = data.name
            txtDetail.text = data.description
            Glide.with(this@DetailStoryActivity)
                .load(data.photoUrl)
                .fitCenter()
                .apply(
                    RequestOptions
                        .placeholderOf(R.drawable.loading)
                        .error(R.drawable.error)
                ).into(imgDetail)
        }
    }

    private fun setupView() {
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.detail_page)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}