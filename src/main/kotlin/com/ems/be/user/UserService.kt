package com.ems.be.user

import javax.inject.Singleton

@Singleton
class UserService(
        private val userRepository: UserRepository
) {
    fun getAllUsers(): List<UserEntity> {
        return userRepository.getAllUsers()
    }

    fun getUserByUserName(userName: String): UserEntity {
        val requestedUser = userRepository.findByUserName(userName)
        return requestedUser.first()
    }
    fun createUser(userName: String) {
        val user = UserEntity(userName = userName, firstName = "test", lastName = "test", email = "test", isActive = true)
        userRepository.update(user)
    }

    fun deleteUser(userName: String) {
        userRepository.deleteByUserName(userName)
    }

}