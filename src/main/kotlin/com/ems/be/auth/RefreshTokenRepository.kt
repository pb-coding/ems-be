package com.ems.be.auth

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.repository.CrudRepository
import java.util.Optional
import javax.transaction.Transactional
import javax.validation.constraints.NotBlank

@JdbcRepository
interface RefreshTokenRepository : CrudRepository<RefreshTokenEntity, Long> {

    @Transactional
    fun save(@NotBlank username: String,
             @NotBlank refreshToken: String,
             revoked: Boolean): RefreshTokenEntity

    fun findByRefreshToken(@NotBlank refreshToken: String): Optional<RefreshTokenEntity>

    fun updateByUsername(@NotBlank username: String,
                         revoked: Boolean): Long
}
