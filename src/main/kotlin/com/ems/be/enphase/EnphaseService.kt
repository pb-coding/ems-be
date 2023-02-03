package com.ems.be.enphase

import com.ems.be.enphase.auth.EnphaseAuthService
import com.ems.be.enphase.auth.EnphaseLoginStatus
import io.micronaut.http.HttpResponse
import mu.KotlinLogging
import javax.inject.Singleton

@Singleton
class EnphaseService(
    private val enphaseClient: EnphaseClient,
    private val enphaseAuthService: EnphaseAuthService,
) {

    private val logger = KotlinLogging.logger {}

    fun checkIfAccessTokenIsValid(accessToken: String): Boolean {
        val enphaseSolarSystems = enphaseClient.requestAllSolarSystemsOverview(accessToken = accessToken)
        if (enphaseSolarSystems == null) {
            return false
        }
        return true
    }

    fun requestAllSolarSystems(userName: String): HttpResponse<*> {
        val accessToken = enphaseAuthService.getLatestAccessTokenByUserName(userName = userName)?.accessToken
        if (accessToken == null) {
            logger.error("No access token found for this user.")
            return HttpResponse.badRequest("No access token found for this user.")
        }
        logger.info("Access token found for this user: $accessToken")

        val enphaseSolarSystems = enphaseClient.requestAllSolarSystemsOverview(accessToken = accessToken)
        return HttpResponse.ok(enphaseSolarSystems)
    }

    fun requestSolarSystemById(userName: String, solarSystemId: Int): HttpResponse<*> {
        val accessToken = enphaseAuthService.getLatestAccessTokenByUserName(userName = userName)?.accessToken
        if (accessToken == null) {
            logger.error("No access token found for this user.")
            return HttpResponse.badRequest("No access token found for this user.")
        }
        logger.info("Access token found for this user: $accessToken")

        val enphaseSolarSystem = enphaseClient.requestSolarSystemById(accessToken = accessToken, solarSystemId = solarSystemId)
        return HttpResponse.ok(enphaseSolarSystem)
    }

    fun requestSolarProductionData(
            userName: String,
            solarSystemId: Int,
            startAt: String,
            endAt: String
    ): HttpResponse<*> {
        val accessToken = enphaseAuthService.getLatestAccessTokenByUserName(userName = userName)?.accessToken
        if (accessToken == null) {
            logger.error("No access token found for this user.")
            return HttpResponse.badRequest("No access token found for this user.")
        }
        logger.info("Access token found for this user: $accessToken")

        val enphaseProductionStats = enphaseClient.requestProductionStats(
                accessToken = accessToken,
                solarSystemId = solarSystemId,
                endAt = endAt,
                startAt = startAt
        )
        return HttpResponse.ok(enphaseProductionStats)
    }

}