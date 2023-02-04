package com.ems.be.enphase.auth

import com.ems.be.enphase.EnphaseService
import com.ems.be.user.UserService
import io.micronaut.http.HttpResponse
import mu.KotlinLogging
import javax.inject.Singleton

@Singleton
class EnphaseAuthService(
        private val enphaseAuthRepository: EnphaseAuthRepository,
        private val enphaseAuthClient: EnphaseAuthClient,
        private val userService: UserService,
) {

    private val logger = KotlinLogging.logger {}

    fun getLatestAccessTokenByUserName(userName: String): EnphaseAuthEntity? {
        val userId = userService.getUserByUserName(userName)?.id

        if (userId == null) {
            logger.error("No user found for this username.")
            return null
        }
        return getLatestAccessTokensByUserId(userId = userId)
    }

    fun getLatestAccessTokensByUserId(userId: Int): EnphaseAuthEntity? =
        enphaseAuthRepository.getAccessTokensByUserId(userId).maxByOrNull { it.createdAt }

    fun addTokens(userId: Int, accessToken: String, refreshToken: String): EnphaseAuthEntity {
        val latestToken = getLatestAccessTokensByUserId(userId)

        return enphaseAuthRepository.update(
            EnphaseAuthEntity(
                id = latestToken?.id,
                userId = userId,
                accessToken = accessToken,
                refreshToken = refreshToken
            ),
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

        val enphaseAuthTokens = enphaseAuthClient.requestEnphaseTokensByAuthCode(userId = userId, code = code)
        logger.info("Access Token: ${enphaseAuthTokens.access_token}")

        addTokens(userId, enphaseAuthTokens.access_token, enphaseAuthTokens.refresh_token)

        return HttpResponse.ok("Access and refresh tokens retrieved")
    }

    fun refreshEnphaseAuthTokens(userName: String, refreshToken: String): EnphaseAuthEntity? {
        val userId = userService.getUserByUserName(userName)?.id

        if (userId == null) {
            logger.error("No user found for this username.")
            return null
        }
        val refreshedEnphaseAuthTokens = enphaseAuthClient.requestRefreshedTokensByRefreshToken(refreshToken = refreshToken)
        // TODO: error handling not working
        if (refreshedEnphaseAuthTokens == null) {
            logger.error("Tokens could not be refreshed.")
            return null
        }
        return addTokens(userId, refreshedEnphaseAuthTokens.access_token, refreshedEnphaseAuthTokens.refresh_token)
    }

}
data class EnphaseLoginStatus(
    val isUserLoggedIntoEnphase: Boolean
)