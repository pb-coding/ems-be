package com.ems.be.enphase

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import mu.KotlinLogging
import javax.inject.Singleton

@Singleton
class EnphaseAuthService(
        private val enphaseAuthRepository: EnphaseAuthRepository,
        private val enphaseAuthClient: EnphaseAuthClient,
) {

    private val logger = KotlinLogging.logger {}

    fun getLatestAccessTokensByUserId(userId: Int): List<EnphaseAuthEntity> {
        return enphaseAuthRepository.getLatestAccessTokenByUserId(userId)
    }

    fun addTokens(userId: Int, accessToken: String, refreshToken: String) {
        enphaseAuthRepository.update(
                EnphaseAuthEntity(
                        userId = userId,
                        accessToken = accessToken,
                        refreshToken = refreshToken,
                )
        )
    }

    fun retrieveEnphaseAuthTokens(userId: String?, code: String?): HttpResponse<String>? {
        if (code.isNullOrBlank()) {
            logger.error("No code provided")
            return HttpResponse.badRequest("No code provided")
        }
        if (userId.isNullOrBlank()) {
            logger.error("No userid provided")
            return HttpResponse.badRequest("No username provided")
        }
        val userId = userId.toIntOrNull()
        if (userId == null) {
            logger.error("userId is not an Integer")
            return HttpResponse.badRequest("Invalid userid: Integer expected")
        }

        logger.info("Code $code for userId $userId provided")

        val enphaseAuthTokens = enphaseAuthClient.requestAccessAndRefreshTokens(userId = userId, code = code)
        logger.info("Access Token: ${enphaseAuthTokens.access_token}")

        enphaseAuthRepository.update(EnphaseAuthEntity(userId = userId, accessToken = enphaseAuthTokens.access_token, refreshToken = enphaseAuthTokens.refresh_token))

        return HttpResponse.ok("Access and refresh tokens retrieved")
    }

}