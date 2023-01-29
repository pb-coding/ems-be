package com.ems.be.enphase

import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import javax.persistence.EntityManager

@Repository
abstract class EnphaseAuthRepository(val entityManager: EntityManager) :
CrudRepository<EnphaseAuthEntity, Int> {
    @Query("""SELECT DISTINCT enphase_auth_entity from EnphaseAuthEntity enphase_auth_entity 
                WHERE enphase_auth_entity.userId = :userId"""
    )
    abstract fun getLatestAccessTokenByUserId(userId: Int): List<EnphaseAuthEntity>
}
