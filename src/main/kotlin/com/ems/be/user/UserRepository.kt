package com.ems.be.user

import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import javax.persistence.EntityManager

@Repository
abstract class UserRepository(val entityManager: EntityManager) :
CrudRepository<UserEntity, Int> {
    @Query("""SELECT DISTINCT user_entity from UserEntity user_entity""")
    abstract fun getAllUsers(): List<UserEntity>

    @Query(
            """SELECT user_entity FROM UserEntity user_entity
                WHERE user_entity.userName = :userName"""
    )
    abstract fun findByUserName(userName: String): List<UserEntity>

    @Query(
            """DELETE from UserEntity user_entity
                    where user_entity.userName = :userName"""
    )
    abstract fun deleteByUserName(userName: String)
}
