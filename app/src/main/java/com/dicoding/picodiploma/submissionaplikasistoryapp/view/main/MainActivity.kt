package com.dicoding.picodiploma.submissionaplikasistoryapp.view.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.submissionaplikasistoryapp.databinding.ActivityMainBinding
import com.dicoding.picodiploma.submissionaplikasistoryapp.view.home.HomeActivity
import com.dicoding.picodiploma.submissionaplikasistoryapp.view.welcome.ViewModelFactory
import com.dicoding.picodiploma.submissionaplikasistoryapp.view.welcome.WelcomeActivity


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var factory: ViewModelFactory
    private val mainViewModel: MainViewModel by viewModels { factory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel()
        setupAction()
        setupLanguage()
    }

    private fun setupLogout() {
        mainViewModel.logout()
    }

    private fun mainViewModel() {
        factory = ViewModelFactory.getInstance(this)
    }

    private fun setupLanguage() {
        binding.settingImageView.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }

    private fun setupAction() {
        binding.lanjutkanMain.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        binding.logoutbtn.setOnClickListener {
            setupLogout()
            startActivity(Intent(this, WelcomeActivity::class.java))
        }
    }

}





