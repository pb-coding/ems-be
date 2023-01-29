package com.ems.be.enphase

import com.nimbusds.jose.util.Base64
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import mu.KotlinLogging

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/enphase")
class EnphaseAuthController(
        private val enphaseAuthClient: EnphaseAuthClient
) {

    private val logger = KotlinLogging.logger {}

    @Get("/oauth")
    fun getEnphaseToken(@QueryValue("code") code: String? = null): MutableHttpResponse<String>? {
        if (code.isNullOrBlank()) {
            logger.error("No code provided")
            return HttpResponse.badRequest("No code provided")
        }
        else {
            logger.info("Code provided: $code")
            val accessAndRefreshTokens = enphaseAuthClient.retrieveAccessAndRefreshTokens(code = code)
            logger.info("Access and refresh tokens: $accessAndRefreshTokens")
            if (accessAndRefreshTokens.isNullOrBlank()) {
                logger.error("No access and refresh tokens retrieved")
                return HttpResponse.badRequest("No access and refresh tokens retrieved")
            }

            return HttpResponse.ok("Access and refresh tokens retrieved")
        }
    }
}