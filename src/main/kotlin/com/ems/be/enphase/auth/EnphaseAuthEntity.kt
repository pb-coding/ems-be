package com.ems.be.enphase.auth

import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "enphase_auth_entity", schema = "be")
class EnphaseAuthEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int? = null,
    @Column(name = "user_id")
    var userId: Int,
    @Column(name = "access_token")
    var accessToken: String,
    @Column(name = "refresh_token")
    var refreshToken: String,
    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "last_updated_at")
    var lastUpdatedAt: LocalDateTime = LocalDateTime.now(),
)