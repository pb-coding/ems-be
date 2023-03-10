package com.ems.be.auth

import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import java.time.Instant
import javax.validation.constraints.NotBlank

@MappedEntity
data class RefreshTokenEntity(

        @field:Id
        @GeneratedValue
        var id: Long? = null,

        @NotBlank
        var username: String,

        @NotBlank
        var refreshToken: String,

        var revoked: Boolean,

        @DateCreated
        var dateCreated: Instant? = null
)

