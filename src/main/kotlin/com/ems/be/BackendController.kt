package com.ems.be

import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import mu.KotlinLogging

@Controller("/backend") //
class BackendController {

    private val logger = KotlinLogging.logger {}
    @Get
    @Secured(SecurityRule.IS_ANONYMOUS)
    fun index(): MutableHttpResponse<String>? {
        logger.info("BACKEND REQUEST")
        return HttpResponse.ok("Status: backend is running") //
    }
}