package com.ems.be.enphase

import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client

@Client("https://api.enphaseenergy.com/api/v4")
interface EnphaseClient {
    @Get("/systems/{solarSystemId}/rgm_stats")
    fun requestProductionStats(
        solarSystemId: Int,
        accessToken: String,
        @Header(name = "Authorization") authorization: String = "Bearer $accessToken",
        @PathVariable("end_at") endAt: String,
        @PathVariable("start_at") startAt: String,
        @PathVariable("key") key: String = "75c7404db819a24ee0bafcfd6714c163",
    ): EnphaseProductionStatsResponse
}