package com.ems.be.auth

import io.micronaut.http.MediaType.TEXT_PLAIN
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import java.security.Principal

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/api/auth")
class AuthController {
    @Produces(TEXT_PLAIN)
    @Get("/login_status")
    fun index(principal: Principal): String = principal.name
}