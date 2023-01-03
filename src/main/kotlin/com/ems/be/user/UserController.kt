package com.ems.be.user

import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.http.MediaType

@Controller("/user")
class UserController (private val userService: UserService) {
    @Get("/")
    @Secured(SecurityRule.IS_ANONYMOUS)
    fun showEndpointStatus(): MutableHttpResponse<String>? {
        return HttpResponse.ok("User API endpoint - online")
    }

    @Get("/all")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    fun getAllUsers(): MutableHttpResponse<List<UserEntity>> {
        return HttpResponse.ok(userService.getAllUsers())
    }

    @Get("/{identifier}")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    fun getUserByUserName(@PathVariable("identifier") identifier: String): HttpResponse<UserEntity>? {
        return HttpResponse.ok(userService.getUserByUserName(identifier.toString()))
    }

    @Post(value = "/create", processes = [MediaType.TEXT_PLAIN])
    @Secured(SecurityRule.IS_AUTHENTICATED)
    fun createUser(@Body userName: String): MutableHttpResponse<String>? {
        userService.createUser(userName)
        return HttpResponse.ok("creating user")
    }

    @Delete("/delete/{userName}")
    @Secured(SecurityRule.IS_ANONYMOUS)
    fun deleteUserByUserName(@PathVariable userName: String): MutableHttpResponse<String>? {
        userService.deleteUser(userName)
        return HttpResponse.ok("deleting user")
    }

}