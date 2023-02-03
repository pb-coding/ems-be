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
        private val enphaseService: EnphaseService,
) {

    private val logger = KotlinLogging.logger {}

    fun checkEnphaseLoginStatus(userName: String): HttpResponse<*> {
        val enphaseAuthEntity = getLatestAccessTokenByUserName(userName = userName)
        if (enphaseAuthEntity == null) {
            logger.error("No access token found for this user. User needs to login into Enphase.")
            return HttpResponse.ok(EnphaseLoginStatus(false))
        }

        val accessToken = enphaseAuthEntity.accessToken
        logger.info("Access token found for this user: $accessToken")

        val accessTokenIsValid = enphaseService.checkIfAccessTokenIsValid(accessToken = accessToken)

        if (!accessTokenIsValid) {
            logger.error("Access token is not valid anymore. Trying to refresh tokens.")
            // TODO: Refresh tokens
            return HttpResponse.ok(EnphaseLoginStatus(false))
        }

        logger.error("Access token is valid.")

        return HttpResponse.ok(EnphaseLoginStatus(true))
    }

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

    fun addTokens(userId: Int, accessToken: String, refreshToken: String) {
        val latestToken = getLatestAccessTokensByUserId(userId)

        enphaseAuthRepository.update(
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

}
data class EnphaseLoginStatus(
    val isUserLoggedIntoEnphase: Boolean
)