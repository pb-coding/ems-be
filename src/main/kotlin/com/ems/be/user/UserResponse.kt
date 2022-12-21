package com.ems.be.user

data class UserResponse(
        val id: Int?,
        val userName: String,
        val firstName: String,
        val lastName: String,
        val email: String,
        val additionalTest: String,
) {
    constructor(user: UserEntity) : this (
            id = user.id,
            userName = user.userName,
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            additionalTest = "something"
    )
}