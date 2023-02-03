package com.ems.be.enphase.auth

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import mu.KotlinLogging
import java.security.Principal


@Controller("/enphase")
class EnphaseAuthController(
        val enphaseAuthService: EnphaseAuthService
) {

    private val logger = KotlinLogging.logger {}

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get("/login_status")
    fun checkLoginStatus(principal: Principal): HttpResponse<*> {
        return enphaseAuthService.checkEnphaseLoginStatus(principal.name)
    }

    // TODO: make this more secure - users may not manipulate setting their access tokens in the db for different users - maybe give access token as query param?

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Get("/oauth/{userid}")
    fun getEnphaseAuthCode(
            @PathVariable("userid") userId: String,
            @QueryValue("code") code: String? = null
    ): HttpResponse<String>? {
        return enphaseAuthService.retrieveEnphaseAuthTokens(userId, code)
    }
}