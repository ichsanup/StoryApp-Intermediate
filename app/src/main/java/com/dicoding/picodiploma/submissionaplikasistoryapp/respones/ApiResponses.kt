package com.dicoding.picodiploma.submissionaplikasistoryapp.respones

import com.google.gson.annotations.SerializedName

data class ApiResponses(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
