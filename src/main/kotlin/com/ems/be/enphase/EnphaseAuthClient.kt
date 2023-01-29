package com.ems.be.enphase

import com.nimbusds.jose.util.Base64
import io.micronaut.http.MediaType.TEXT_PLAIN
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client

@Client("https://api.enphaseenergy.com")
interface EnphaseAuthClient {
    // @Consumes(TEXT_PLAIN)
    @Post("/oauth/token")
    fun retrieveAccessAndRefreshTokens(
            @Header(name = "Authorization") authorization: String = "Basic ${Base64.encode("8969127dbb1692152b1fb04d2e421694:c6ac1bd3f47766bb7301c7ddf71dcc48")}",
            @PathVariable("grant_type") grantType: String = "authorization_code",
            @PathVariable("redirect_uri") redirectUri: String = "http://localhost:8080/enphase/oauth",
            @PathVariable("code") code: String?,
    ): String
}