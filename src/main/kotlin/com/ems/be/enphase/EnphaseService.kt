package com.ems.be.enphase

import com.ems.be.enphase.auth.EnphaseAuthService
import io.micronaut.http.HttpResponse
import mu.KotlinLogging
import javax.inject.Singleton

@Singleton
class EnphaseService(
    private val enphaseClient: EnphaseClient,
    private val enphaseAuthService: EnphaseAuthService,
) {

    private val logger = KotlinLogging.logger {}

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

        val enphaseProductionStats = enphaseClient.requestProductionStats(
                accessToken = accessToken,
                solarSystemId = solarSystemId,
                endAt = startAt,
                startAt = endAt
        )
        return HttpResponse.ok(enphaseProductionStats)
    }

}