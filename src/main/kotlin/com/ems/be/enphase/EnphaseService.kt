package com.ems.be.enphase

import com.ems.be.enphase.auth.EnphaseAuthService
import com.ems.be.enphase.auth.EnphaseLoginStatus
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.exceptions.HttpClientResponseException
import mu.KotlinLogging
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Singleton

@Singleton
class EnphaseService(
    private val enphaseClient: EnphaseClient,
    private val enphaseAuthService: EnphaseAuthService,
) {

    private val logger = KotlinLogging.logger {}

    fun checkEnphaseLoginStatus(userName: String): HttpResponse<*> {
        val enphaseAuthEntity = enphaseAuthService.getLatestAccessTokenByUserName(userName = userName)
        if (enphaseAuthEntity == null) {
            logger.error("No access token found for this user. User needs to login into Enphase.")
            return HttpResponse.ok(EnphaseLoginStatus(false))
        }

        val accessToken = enphaseAuthEntity.accessToken
        logger.info("Access token found for this user: $accessToken")

        val enphaseClientResponse = enphaseClient.requestAllSolarSystemsOverview(accessToken = accessToken)
        logger.info(enphaseClientResponse.toString())
        if (enphaseClientResponse.total == null) {
            logger.error("Access token is not valid anymore. Trying to refresh tokens.")
            val refreshedEnphaseAuthTokens = enphaseAuthService.refreshEnphaseAuthTokens(
                    userName = userName,
                    refreshToken = enphaseAuthEntity.refreshToken
            )
            if (refreshedEnphaseAuthTokens == null) {
                logger.error("Access token could not be refreshed. User needs to login into Enphase.")
                return HttpResponse.ok(EnphaseLoginStatus(false))
            }
            return HttpResponse.ok(EnphaseLoginStatus(true))
        }

        logger.info("Access token is valid.")

        return HttpResponse.ok(EnphaseLoginStatus(true))
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

    data class EnphaseAccessTokenMissingResponse(
        val code: Int = 202,
        val message: String = "Backend request accepted, but Enphase access token is missing. Please login.",
    )

    data class EnphaseAccessTokenInvalidResponse(
        val code: Int = 202,
        val message: String = "Backend request accepted, but Enphase access token is invalid. Please login.",
    )

    fun requestSolarSystemById(userName: String, solarSystemId: Int): HttpResponse<*> {
        val accessToken = enphaseAuthService.getLatestAccessTokenByUserName(userName = userName)?.accessToken
        if (accessToken == null) {
            logger.error("No access token found for this user.")
            return HttpResponse.badRequest(EnphaseAccessTokenMissingResponse())
        }
        logger.info("Access token found for this user: $accessToken")

        try {
            val enphaseSolarSystem = enphaseClient.requestSolarSystemById(accessToken = accessToken, solarSystemId = solarSystemId)
            return HttpResponse.ok(enphaseSolarSystem)
        }
        catch (e: HttpClientResponseException) {
            return HttpResponse.badRequest(EnphaseAccessTokenInvalidResponse())
        }


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

        val enphaseProductionData = enphaseClient.requestProductionData(
                accessToken = accessToken,
                solarSystemId = solarSystemId,
                endAt = endAt,
                startAt = startAt
        )
        return HttpResponse.ok(enphaseProductionData)
    }

    fun requestSolarConsumptionData(
        userName: String,
        solarSystemId: Int,
        startDate: String
    ): HttpResponse<*> {
        val accessToken = enphaseAuthService.getLatestAccessTokenByUserName(userName = userName)?.accessToken
        if (accessToken == null) {
            logger.error("No access token found for this user.")
            return HttpResponse.badRequest("No access token found for this user.")
        }
        logger.info("Access token found for this user: $accessToken")

        val enphaseConsumptionData = enphaseClient.getConsumptionDataForDate(
            accessToken = accessToken,
            solarSystemId = solarSystemId,
            startDate = startDate
        )
        return HttpResponse.ok(enphaseConsumptionData)
    }

    fun combineProductionAndConsumptionIntervals(
        productionIntervals: List<EnphaseProductionInterval>,
        consumptionIntervals: List<EnphaseConsumptionInterval>
    ): List<CustomProductionConsumptionInterval> = productionIntervals.map { productionInterval ->
        CustomProductionConsumptionInterval(
            productionInterval.end_at,
            productionInterval.wh_del,
            consumptionIntervals
                .find { it.end_at == productionInterval.end_at }
                ?.enwh
                ?: 0
        )
    }

    fun summarizeProductionAndConsumption(
        userName: String,
        solarSystemId: Int,
        startDate: String
    ): HttpResponse<*> {
        val accessToken = enphaseAuthService.getLatestAccessTokenByUserName(userName = userName)?.accessToken
        if (accessToken == null) {
            logger.error("No access token found for this user.")
            return HttpResponse.badRequest("No access token found for this user.")
        }
        logger.info("Access token found for this user: $accessToken")


        val german_zone = ZoneId.of("Europe/Berlin").getRules().getOffset(LocalDateTime.now());
        val startAtDateTime = Timestamp.valueOf("$startDate 00:00:00").toLocalDateTime().atOffset(german_zone)
        val startAt = startAtDateTime.toEpochSecond()
        val endAt = startAtDateTime.plusDays(1).toEpochSecond()

        val enphaseProductionData = enphaseClient.requestProductionData(
            accessToken = accessToken,
            solarSystemId = solarSystemId,
            endAt = endAt.toString(),
            startAt = startAt.toString()
        )

        val enphaseConsumptionData = enphaseClient.getConsumptionDataForDate(
            accessToken = accessToken,
            solarSystemId = solarSystemId,
            startDate = startDate
        )

        val summaryProductionConsumptionData = combineProductionAndConsumptionIntervals(enphaseProductionData.intervals, enphaseConsumptionData.intervals)
                .map { it.copy(consumption = it.consumption * -1) }
        return HttpResponse.ok(summaryProductionConsumptionData)
    }

}