package com.ems.be.auth.user

import com.ems.be.user.UserService
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

@MicronautTest
class UserServiceTest(private val userService: UserService) {

    @Test
    fun getUserByUserName() {
        val user = userService.getUserByUserName("pb1497")
        assertNotNull(user)
        assertEquals("pb1497", user.userName)
    }
}