package com.ems.be.auth.enphase

import com.ems.be.enphase.EnphaseAuthClient
import com.nimbusds.jose.util.Base64
import com.nimbusds.jwt.JWTParser
import com.nimbusds.jwt.SignedJWT
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import mu.KotlinLogging
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@MicronautTest
class EnphaseAuthClientTest {

    private val logger = KotlinLogging.logger {}

    @Inject
    lateinit var enphaseAuthClient: EnphaseAuthClient // <1>

    @Test
    fun verifyAccessAndRefreshTokensCanBeRetrieved() {
        val enphaseAuthClientResponse = enphaseAuthClient.retrieveAccessAndRefreshTokens(code ="5zLbpC")
        logger.info("Access and refresh tokens: $enphaseAuthClientResponse")
        assertNotNull(enphaseAuthClientResponse)
        // assertNotNull(enphaseAuthClientResponse.accessToken)
        // assertTrue(JWTParser.parse(enphaseAuthClientResponse.accessToken) is SignedJWT)
    }
}