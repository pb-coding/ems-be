package com.ems.be.enphase

data class EnphaseAuthTokensMissingResponse(
        val code: Int = 202,
        val message: String = "Backend request accepted, but Enphase auth tokens are missing. Please login.",
)

data class EnphaseAuthTokensInvalidResponse(
        val code: Int = 202,
        val message: String = "Backend request accepted, but Enphase auth tokens are invalid. Please login.",
)

data class UnknownEnphaseErrorResponse(
        val code: Int = 202,
        val message: String = "Backend request accepted, but Enphase returned an unknown error.",
)