package com.ems.be.enphase

import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client

// TODO: set domain as environment variable
// TODO: set key in db or as environment variable
@Client("https://api.enphaseenergy.com/api/v4")
interface EnphaseClient {

    @Get("/systems")
    fun requestAllSolarSystemsOverview(
        accessToken: String,
        @Header(name = "Authorization") authorization: String = "Bearer $accessToken",
        @QueryValue("key") key: String = "75c7404db819a24ee0bafcfd6714c163",
    ): EnphaseSolarSystemsOverviewResponse

    //handle http error codes
    //https://docs.micronaut.io/latest/guide/index.html#httpClientErrorHandling

    @Get("/systems/{solarSystemId}")
    fun requestSolarSystemById(
        accessToken: String,
        @Header(name = "Authorization") authorization: String = "Bearer $accessToken",
        @PathVariable("solarSystemId") solarSystemId: Int,
        @QueryValue("key") key: String = "75c7404db819a24ee0bafcfd6714c163",
    ): EnphaseSolarSystemResponse

    @Get("/systems/{solarSystemId}/rgm_stats")
    fun requestProductionStats(
        accessToken: String,
        @Header(name = "Authorization") authorization: String = "Bearer $accessToken",
        @PathVariable("solarSystemId") solarSystemId: Int,
        @QueryValue("end_at") endAt: String,
        @QueryValue("start_at") startAt: String,
        @QueryValue("key") key: String = "75c7404db819a24ee0bafcfd6714c163",
    ): EnphaseProductionStatsResponse
}