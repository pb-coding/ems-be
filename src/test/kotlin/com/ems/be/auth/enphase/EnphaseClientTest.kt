package com.ems.be.enphase

import com.ems.be.enphase.auth.EnphaseAuthClient
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import mu.KotlinLogging
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

@MicronautTest
class EnphaseClientTest {

    private val logger = KotlinLogging.logger {}

    @Inject
    lateinit var enphaseClient: EnphaseClient

    val accessToken = "eyJhbGciOiJSUzI1NiJ9.eyJhdWQiOlsib2F1dGgyLXJlc291cmNlIl0sImFwcF90eXBlIjoic3lzdGVtIiwiaXNfaW50ZXJuYWxfYXBwIjpmYWxzZSwidXNlcl9uYW1lIjoiaW5mb0B2aWxsYS1qb3NlcGhpbmUuZGUiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiZW5sX2NpZCI6IiIsImVubF9wYXNzd29yZF9sYXN0X2NoYW5nZWQiOiIxNjYyMTM1NDQxIiwiZXhwIjoxNjc1NTAyMzkxLCJlbmxfdWlkIjoiMzA1OTc4NSIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiIwMTI5MTZhZi1mY2NkLTRjNjEtYTJjYi1jOWM3MTk4ZGY3NGQiLCJjbGllbnRfaWQiOiI4OTY5MTI3ZGJiMTY5MjE1MmIxZmIwNGQyZTQyMTY5NCJ9.Xs18U7eRWiT01l3z0uM9oi3pLDhkzR3WhRrPuac_EAWoZYQ0uEHFEfc4jS6QMps4Qg9bX-qy5_fmvIWTlNQfdbD2H5h4PB3_ncZUxvp_N_kUN4k4O3siUyUDo7B_Yqsu2tJ_ZtyG4eZGpxfSizPQa0tb-Fw88mYArzv0o7UShj0"

    @Test
    fun verifySystemOverviewRequest() {
        val enphaseClientResponse = enphaseClient.requestAllSolarSystemsOverview(accessToken = accessToken)
        logger.info("System overview response: $enphaseClientResponse")
        logger.info("System total value: ${enphaseClientResponse.total}")
        logger.info("System found: ${enphaseClientResponse.systems}")
        assertNotNull(enphaseClientResponse.total)
        assertNotNull(enphaseClientResponse.systems)
    }

    @Test
    fun verifyRequestOfSolarSystemById() {
        val enphaseClientResponse = enphaseClient.requestSolarSystemById(solarSystemId = 3447361, accessToken = accessToken)
        logger.info("Solar system response: $enphaseClientResponse")
        assertEquals(enphaseClientResponse.system_id, 3447361)
    }

    @Test
    fun verifyRequestOfProductionStats() {
        val solarSystemId = 3447361
        val startAt = "1671881021"
        val endAt = "1671884621"
        val enphaseClientResponse = enphaseClient.requestProductionStats(solarSystemId = solarSystemId, accessToken = accessToken, startAt = startAt, endAt = endAt)
        logger.info("Production Stats response: $enphaseClientResponse")
        assertEquals(enphaseClientResponse.system_id, solarSystemId)
    }
}