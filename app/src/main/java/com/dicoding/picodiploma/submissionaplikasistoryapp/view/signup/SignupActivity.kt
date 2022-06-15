package com.dicoding.picodiploma.submissionaplikasistoryapp.view.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.submissionaplikasistoryapp.R
import com.dicoding.picodiploma.submissionaplikasistoryapp.databinding.ActivitySignupBinding
import com.dicoding.picodiploma.submissionaplikasistoryapp.view.login.LoginActivity
import com.dicoding.picodiploma.submissionaplikasistoryapp.view.welcome.ViewModelFactory


class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val signupViewModel: SignupViewModel by viewModels { factory }
    private lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
        playAnimation()
    }


    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val titletextview =
            ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(300)
        val nametextview =
            ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(300)
        val nameedittextlayout =
            ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val emailtextview =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(300)
        val emailedittextlayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val passwordtextview =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(300)
        val passwordedittextlayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val signupbutton =
            ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(300)

        AnimatorSet().apply {
            playSequentially(titletextview,
                nametextview,
                nameedittextlayout,
                emailtextview,
                emailedittextlayout,
                passwordtextview,
                passwordedittextlayout,
                signupbutton)
            start()
        }
    }

    private fun setupView() {
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.formr_register)
        }
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)
    }

    private fun setupAction() {
        binding.apply {
            signupButton.setOnClickListener {
                if (nameEditText.length() == 0 && emailEditText.length() == 0 && passwordEditText.length() == 0) {
                    nameEditText.error = getString(R.string.attention)
                    emailEditText.error = getString(R.string.attention)
                    passwordEditText.setError(getString(R.string.attention), null)
                } else if (nameEditText.length() != 0 && emailEditText.length() != 0 && passwordEditText.length() != 0) {
                    showLoading()
                    postText()
                    showToast()
                    moveActivity()
                }
            }
        }
    }

    private fun moveActivity() {
        signupViewModel.responeRegister.observe(this@SignupActivity) { response ->
            if (!response.error) {
                startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                finish()
            }
        }
    }

    private fun showToast() {
        signupViewModel.toastText.observe(this@SignupActivity) {
            it.getContentIfNotHandled()?.let { toastText ->
                Toast.makeText(
                    this@SignupActivity, toastText, Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun postText() {
        binding.apply {
            signupViewModel.postRegister(
                nameEditText.text.toString(),
                emailEditText.text.toString(),
                passwordEditText.text.toString()
            )
        }
    }

    private fun showLoading() {
        signupViewModel.isLoading.observe(this@SignupActivity) {
            binding.pbSignup.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}
