package com.ems.be.enphase.auth

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import mu.KotlinLogging

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/enphase")
class EnphaseAuthController(
        val enphaseAuthService: EnphaseAuthService
) {

    private val logger = KotlinLogging.logger {}

    @Get("/oauth/{userid}")
    fun getEnphaseAuthCode(
        @PathVariable("userid") userId: String,
        @QueryValue("code") code: String? = null
    ): HttpResponse<String>? {
        return enphaseAuthService.retrieveEnphaseAuthTokens(userId, code)
    }
}