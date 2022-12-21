package com.ems.be.user

import java.net.http.HttpResponse
import javax.persistence.Id

interface UserApi {
    fun getUserData(userIds: List<Int>?, onlyActive: Boolean?): HttpResponse<List<UserOverviewResponse>>
}

data class UserOverviewResponse(
        val id: Int,
        val userName: String,
        val firstName: String,
        val lastName: String,
        val email: String,
)