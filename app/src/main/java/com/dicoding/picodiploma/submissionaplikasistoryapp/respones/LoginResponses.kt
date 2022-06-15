package com.dicoding.picodiploma.submissionaplikasistoryapp.respones

import com.google.gson.annotations.SerializedName

data class LoginResponses(

	@field:SerializedName("loginResult")
	val loginResult: LoginResult? = null,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class LoginResult(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("token")
	val token: String
)


