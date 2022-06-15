package com.dicoding.picodiploma.submissionaplikasistoryapp.view.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.submissionaplikasistoryapp.R
import com.dicoding.picodiploma.submissionaplikasistoryapp.databinding.ActivityHomeBinding
import com.dicoding.picodiploma.submissionaplikasistoryapp.map.MapsActivity
import com.dicoding.picodiploma.submissionaplikasistoryapp.pagging3.LoadingStateAdapter
import com.dicoding.picodiploma.submissionaplikasistoryapp.pagging3.StoriesViewModel
import com.dicoding.picodiploma.submissionaplikasistoryapp.pagging3.StoriesViewModelFactory
import com.dicoding.picodiploma.submissionaplikasistoryapp.view.camera.CameraActivity
import com.dicoding.picodiploma.submissionaplikasistoryapp.view.welcome.ViewModelFactory
import com.dicoding.picodiploma.submissionaplikasistoryapp.view.welcome.WelcomeActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var factory: ViewModelFactory
    private var token = " "
    private val homeViewModel: HomeViewModel by viewModels { factory }
    private val storiesViewModel: StoriesViewModel by viewModels {
        StoriesViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupView()
        setupAddAction()
    }

    private fun setupAddAction() {
        binding.addStory.setOnClickListener {
            startActivity(Intent(this@HomeActivity, CameraActivity::class.java))
        }
    }

    private fun setupAdapter() {
        val adapter = AllStoriesAdapter()
        binding.rvHome.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        storiesViewModel.stories.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)

        showLoading()
        homeViewModel.getUser().observe(this@HomeActivity) {
            token = it.token
            if (!it.isLogin){
                moveActivity()
            } else {
                setupAdapter()
            }
        }
        showToast()
    }

    private fun showToast() {
        homeViewModel.toastText.observe(this@HomeActivity) {
            it.getContentIfNotHandled()?.let { toastText ->
                Toast.makeText(
                    this@HomeActivity, toastText, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    /*private fun getAllStories(token: String) {
        homeViewModel.getAllStories(token)
    }*/

    //Out
    private fun moveActivity() {
        startActivity(Intent(this@HomeActivity, WelcomeActivity::class.java))
        finish()
    }

    private fun showLoading() {
        homeViewModel.isLoading.observe(this@HomeActivity) {
            binding.pbHome.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun setupView() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.story_page)
            setDisplayHomeAsUpEnabled(true)
        }

        binding.apply {
            rvHome.setHasFixedSize(true)
            rvHome.layoutManager = LinearLayoutManager(this@HomeActivity)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.maps ->{
                intent = Intent(this@HomeActivity, MapsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.logout -> {
                homeViewModel.logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}


