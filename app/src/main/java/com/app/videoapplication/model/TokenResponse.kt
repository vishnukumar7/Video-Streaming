package com.app.videoapplication.model

import android.icu.text.CaseMap.Title
import com.squareup.moshi.Json

data class TokenResponse(

	@Json(name = "expires_at") val expiresAt: String,

	@Json(name = "success")
	val success: Boolean,

	@Json(name = "request_token")
	val requestToken: String
)

