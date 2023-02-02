package com.ems.be.enphase.auth

import com.nimbusds.jose.util.Base64
import io.micronaut.http.HttpHeaders.AUTHORIZATION
import io.micronaut.http.annotation.*
import io.micronaut.http.client.annotation.Client

@Client("https://api.enphaseenergy.com")
interface EnphaseAuthClient {

    @Post("/oauth/token")
    fun requestEnphaseTokensByAuthCode(
        userId: Int,
        @Header(AUTHORIZATION) authorization: String = "Basic ${Base64.encode("8969127dbb1692152b1fb04d2e421694:c6ac1bd3f47766bb7301c7ddf71dcc48")}",
        @PathVariable("grant_type") grantType: String = "authorization_code",
        @PathVariable("redirect_uri") redirectUri: String = "http://localhost:8080/enphase/oauth/${userId}",
        @PathVariable("code") code: String?,
    ): EnphaseAuthTokens

    @Post("/oauth/token")
    fun requestRefreshedTokensByRefreshToken(
        @Header(AUTHORIZATION) authorization: String = "Basic ${Base64.encode("8969127dbb1692152b1fb04d2e421694:c6ac1bd3f47766bb7301c7ddf71dcc48")}",
        @QueryValue("grant_type") grantType: String = "refresh_token",
        @QueryValue("refresh_token") refreshToken: String,
    ): EnphaseAuthTokens
}