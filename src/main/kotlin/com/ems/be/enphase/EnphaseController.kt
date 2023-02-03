package com.ems.be.enphase

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import mu.KotlinLogging
import java.security.Principal

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/enphase/solar")
class EnphaseController(
    val enphaseService: EnphaseService,
) {
    private val logger = KotlinLogging.logger {}

    @Get("/systems")
    fun getAllSolarSystems(principal: Principal): HttpResponse<*> {
        return enphaseService.requestAllSolarSystems(principal.name)
    }

    @Get("/system/{solar_system_id}")
    fun getSolarSystemById(
            principal: Principal,
            @QueryValue("solar_system_id") solarSystemId: Int,
    ): HttpResponse<*> {
        return enphaseService.requestSolarSystemById(
                userName = principal.name,
                solarSystemId = solarSystemId
        )
    }

    @Get("/production")
    fun getSolarProductionData(
            principal: Principal,
            @QueryValue("solar_system_id") solarSystemId: Int,
            @QueryValue("start_at") startAt: String,
            @QueryValue("end_at") endAt: String,
    ): HttpResponse<*> {
        return enphaseService.requestSolarProductionData(
                userName = principal.name,
                solarSystemId = solarSystemId,
                startAt = startAt,
                endAt = endAt
        )
    }
}