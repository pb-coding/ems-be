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

    @Test
    fun verifyRequestOfProductionStats() {
        val accessToken = "eyJhbGciOiJSUzI1NiJ9.eyJhdWQiOlsib2F1dGgyLXJlc291cmNlIl0sImFwcF90eXBlIjoic3lzdGVtIiwiaXNfaW50ZXJuYWxfYXBwIjpmYWxzZSwidXNlcl9uYW1lIjoiaW5mb0B2aWxsYS1qb3NlcGhpbmUuZGUiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiZW5sX2NpZCI6IiIsImVubF9wYXNzd29yZF9sYXN0X2NoYW5nZWQiOiIxNjYyMTM1NDQxIiwiZXhwIjoxNjc1NDMyMTU1LCJlbmxfdWlkIjoiMzA1OTc4NSIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiIyYjE2YzhhNy1hNzU2LTQ1YWMtODAwYS00NDNkYjQyNTVmYTkiLCJjbGllbnRfaWQiOiI4OTY5MTI3ZGJiMTY5MjE1MmIxZmIwNGQyZTQyMTY5NCJ9.SfD0vUmCo_g98FaAXxqVXJ71cCgwCPHklKyX-6mFCOY8wTTvitEAZOAcHMIkJ584rQsrR04BaA_ZY00bffCeChjE5IIINuF04EqYRLNuk6AfBBYvDPbL3ErnQIpPbbqZBxcYzu_qTtzzXtTLF6vVlTRnUt5iANHCZGOQBZ5EgWo"
        val solarSystemId = 3447361
        val startAt = "1671881021"
        val endAt = "1671884621"
        val enphaseClientResponse = enphaseClient.requestProductionStats(solarSystemId = solarSystemId, accessToken = accessToken, startAt = startAt, endAt = endAt)
        logger.info("Production Stats response: $enphaseClientResponse")
        assertEquals(enphaseClientResponse.system_id, solarSystemId)
    }


}