package com.ems.be.enphase

import com.ems.be.enphase.auth.EnphaseAuthService
import io.micronaut.http.HttpResponse
import mu.KotlinLogging
import javax.inject.Singleton

@Singleton
class EnphaseService(
    // private val enphaseClient: EnphaseClient,
    private val enphaseAuthService: EnphaseAuthService,
) {

    private val logger = KotlinLogging.logger {}

    fun requestSolarProductionData() {
        val test = enphaseAuthService.getLatestAccessTokensByUserId(1)
        logger.info("Access Token: $test")
        // enphaseClient.requestProductionStats()
    }

}