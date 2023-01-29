package com.ems.be.enphase

import com.fasterxml.jackson.databind.ObjectMapper


data class EnphaseAuthTokens(
        val access_token: String,
        val token_type: String,
        val refresh_token: String,
        val expires_in: String,
        val scope: String,
        val enl_uid: String,
        val enl_cid: String,
        val enl_password_last_changed: String,
        val is_internal_app: Boolean,
        val app_type: String,
        val jti: String,
)