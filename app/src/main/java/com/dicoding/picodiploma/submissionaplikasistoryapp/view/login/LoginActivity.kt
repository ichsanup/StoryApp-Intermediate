package com.dicoding.picodiploma.submissionaplikasistoryapp.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.submissionaplikasistoryapp.R
import com.dicoding.picodiploma.submissionaplikasistoryapp.databinding.ActivityLoginBinding
import com.dicoding.picodiploma.submissionaplikasistoryapp.model.UserModel
import com.dicoding.picodiploma.submissionaplikasistoryapp.view.home.HomeActivity
import com.dicoding.picodiploma.submissionaplikasistoryapp.view.main.MainActivity
import com.dicoding.picodiploma.submissionaplikasistoryapp.view.welcome.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var factory: ViewModelFactory
    private val loginViewModel: LoginViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
        setupViewModel()
        playAnimation()
        setupAction()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val titletextview =
            ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(300)
        val messagetextview =
            ObjectAnimator.ofFloat(binding.messagetxtview, View.ALPHA, 1f).setDuration(300)
        val emailtextview =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(300)
        val emailedittextlayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val passwordtextview =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(300)
        val passwordeditextlayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val loginbutton =
            ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(300)

        AnimatorSet().apply {
            playSequentially(titletextview,
                messagetextview,
                emailtextview,
                emailedittextlayout,
                passwordtextview,
                passwordeditextlayout,
                loginbutton)
            start()
        }
    }

    private fun setupAction() {
        binding.apply {
            loginButton.setOnClickListener {
                if (emailEditText.length() == 0 && passwordEditText.length() == 0) {
                    emailEditText.error = getString(R.string.attention)
                    passwordEditText.setError(getString(R.string.attention), null)
                } else if (emailEditText.length() != 0 && passwordEditText.length() != 0) {
                    showLoading()
                    postText()
                    showToast()
                    loginViewModel.login()
                    moveActivity()
                }
            }
        }
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)
    }

    private fun setupView() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.login_form)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun showToast() {
        loginViewModel.toastText.observe(this@LoginActivity) {
            it.getContentIfNotHandled()?.let { toastText ->
                Toast.makeText(
                    this@LoginActivity, toastText, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showLoading() {
        loginViewModel.isLoading.observe(this@LoginActivity) {
            binding.pbLogin.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun postText() {
        binding.apply {
            loginViewModel.postLogin(
                emailEditText.text.toString(),
                passwordEditText.text.toString()
            )
        }

        loginViewModel.responseLogin.observe(this@LoginActivity) { response ->
            sessionSave(
                UserModel(
                    response.loginResult?.name.toString(),
                    AUTH_KEY + (response.loginResult?.token.toString()),
                    true
                )
            )
        }
    }

    private fun moveActivity() {
        loginViewModel.responseLogin.observe(this@LoginActivity) { response ->
            if (!response.error) {
                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                finish()
            }
        }
    }

    private fun sessionSave(session: UserModel){
        loginViewModel.saveUser(session)
    }

    companion object {
        private const val AUTH_KEY = "Bearer "
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}