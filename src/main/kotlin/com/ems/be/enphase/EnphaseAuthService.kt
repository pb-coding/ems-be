package com.ems.be.enphase

import io.micronaut.http.HttpRequest
import javax.inject.Singleton

@Singleton
class EnphaseAuthService(
        private val enphaseAuthRepository: EnphaseAuthRepository
) {
    fun getLatestAccessTokensByUserId(userId: Int): List<EnphaseAuthEntity> {
        return enphaseAuthRepository.getLatestAccessTokenByUserId(userId)
    }

    fun addTokens(userId: Int, accessToken: String, refreshToken: String) {
        enphaseAuthRepository.update(
                EnphaseAuthEntity(
                        userId = userId,
                        accessToken = accessToken,
                        refreshToken = refreshToken,
                )
        )
    }

}