package com.ems.be.enphase.auth

import com.nimbusds.jwt.JWTParser
import com.nimbusds.jwt.SignedJWT
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import mu.KotlinLogging
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
        val enphaseAuthClientResponse = enphaseAuthClient.requestEnphaseTokensByAuthCode(userId = 1, code ="5zLbpC")
        logger.info("Access and refresh tokens: $enphaseAuthClientResponse")
        assertNotNull(enphaseAuthClientResponse)
        assertNotNull(enphaseAuthClientResponse.access_token)
        assertTrue(JWTParser.parse(enphaseAuthClientResponse.access_token) is SignedJWT)
    }

    @Test
    fun verifyRefreshedTokensCanBeRetrieved() {
        val refreshToken = "eyJhbGciOiJSUzI1NiJ9.eyJhcHBfdHlwZSI6InN5c3RlbSIsInVzZXJfbmFtZSI6ImluZm9AdmlsbGEtam9zZXBoaW5lLmRlIiwiZW5sX2NpZCI6IiIsImVubF9wYXNzd29yZF9sYXN0X2NoYW5nZWQiOiIxNjYyMTM1NDQxIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImNsaWVudF9pZCI6Ijg5NjkxMjdkYmIxNjkyMTUyYjFmYjA0ZDJlNDIxNjk0IiwiYXVkIjpbIm9hdXRoMi1yZXNvdXJjZSJdLCJpc19pbnRlcm5hbF9hcHAiOmZhbHNlLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiYXRpIjoiMjRhZWQyZGMtNTRhMC00ZmQyLTgyMWYtYmI1YzU0NzI5NjlkIiwiZXhwIjoxNjc1OTU2MjIwLCJlbmxfdWlkIjoiMzA1OTc4NSIsImp0aSI6IjUwNDc2YzZiLWZjYWItNDQxNi05NmE1LTYyZWM1MmViZjFiMSJ9.CWD7UECSCcN8x-9lPlWGbDZFS51dYo3RCC4tV1cNbplpQ8purgLzhff2ZEBmqKS6AUisS46IcC8OIyNUFRMggFU34L0nxu-p9_XeNzkCdd17Pdb9g2hopGk0OLGeeSrZtv_9MXSsbWTmY5Gkj79VnwVX-5yi0bSgWdqx83anYPY"
        val enphaseAuthClientResponse = enphaseAuthClient.requestRefreshedTokensByRefreshToken(refreshToken = refreshToken)
        assertNotNull(enphaseAuthClientResponse)
        assertNotNull(enphaseAuthClientResponse.access_token)
        assertTrue(JWTParser.parse(enphaseAuthClientResponse.access_token) is SignedJWT)
    }
}