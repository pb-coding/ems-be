package com.ems.be.enphase

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/enphase/solar")
class EnphaseController(
    val enphaseService: EnphaseService,
) {
    @Get("/production")
    fun getSolarProductionData() = enphaseService.requestSolarProductionData()
}