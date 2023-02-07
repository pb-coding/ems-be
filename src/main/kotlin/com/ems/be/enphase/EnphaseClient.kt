package com.ems.be.enphase

import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client
import java.time.LocalDateTime

// TODO: set domain as environment variable
// TODO: set key in db or as environment variable
@Client("https://api.enphaseenergy.com/api/v4")
interface EnphaseClient {

    /** Returns a list of systems for which the user can make API requests. By default, systems are returned in batches of 10. The maximum size is 100.**/
    @Get("/systems")
    fun requestAllSolarSystemsOverview(
        accessToken: String,
        @Header(name = "Authorization") authorization: String = "Bearer $accessToken",
        @QueryValue("key") key: String = "75c7404db819a24ee0bafcfd6714c163",
    ): EnphaseSolarSystemsOverviewResponse

    //handle http error codes
    //https://docs.micronaut.io/latest/guide/index.html#httpClientErrorHandling




    /** Retrieves a System by ID **/
    @Get("/systems/{solarSystemId}")
    fun requestSolarSystemById(
        accessToken: String,
        @Header(name = "Authorization") authorization: String = "Bearer $accessToken",
        @PathVariable("solarSystemId") solarSystemId: Int,
        @QueryValue("key") key: String = "75c7404db819a24ee0bafcfd6714c163",
    ): EnphaseSolarSystemResponse




    /** Returns performance statistics as measured by the revenue-grade meters installed on the specified system. If the total duration requested is more than one week, returns one week of intervals. Intervals are 15 minutes in length and start at the top of the hour.
    Requests for times that do not fall on the 15-minute marks are rounded down. For example, a request for 08:01, 08:08, 08:11, or 08:14 (in epoch format) is treated as a request for 08:00 (in epoch format). Intervals are listed by their end times in epoch format.
    The requested date range in one API hit cannot be more than 7 days and the requested start at must be within 2 years from current time. If the start_at specified is earlier than the system’s first reported date, then midnight of the system’s first reported date is considered as start_at. **/
    @Get("/systems/{solarSystemId}/rgm_stats")
    fun requestProductionData(
        accessToken: String,
        @Header(name = "Authorization") authorization: String = "Bearer $accessToken",
        @PathVariable("solarSystemId") solarSystemId: Int,
        @QueryValue("key") key: String = "75c7404db819a24ee0bafcfd6714c163",

        /** Start of period to report on in Unix epoch time. If no start is specified, the assumed start is midnight today, in the timezone of the system. If the start is earlier than one year ago, the response includes an empty intervals list. If the start is earlier than the system’s operational_date, the response data begins at midnight of the first reported interval date. **/
        @QueryValue("start_at") startAt: String,

        /** End of reporting period in Unix epoch time. If no end is specified, default to the time of the request or (start time + 1 week), whichever is earlier. If the end is later than the last reported interval the response data ends with the last reported interval.**/
        @QueryValue("end_at") endAt: String,
    ): EnphaseProductionDataResponse




    /** Excerpt from Enphase Docs: https://developer-v4.enphase.com/docs.html
     * Retrieves telemetry for all the consumption meters of a system.
     * If the start_at specified is earlier than the system’s first reported date, then midnight of the system’s first reported date is considered as start_at.
     * The end_at is calculated as the minimum of the time of the request and (start time + granularity).
     * The meaning of granularity is as follow:
     * If granularity is 15mins, maximum 1 interval will appear in response. If granularity is day, maximum 96 intervals, and if granularity is week, maximum 672 intervals, will appear in response where each interval is of 15 mins duration.
     * The requested start date must be within 2 years from current date.
     * By default start_at will appear in response. If start_date parameter is passed in the url, then start_date field will appear in response.
     * By default end_at will appear in response. If end_date parameter is passed in the url then end_date field will appear in response. **/
    @Get("/systems/{solarSystemId}/telemetry/consumption_meter")
    fun getConsumptionDataForDate(
        accessToken: String,
        @Header(name = "Authorization") authorization: String = "Bearer $accessToken",
        @PathVariable("solarSystemId") solarSystemId: Int,
        @QueryValue("key") key: String = "75c7404db819a24ee0bafcfd6714c163",

        /** The granularity of the telemetry data. Possible values are 'week', 'day', '15mins'. Default is 'day' **/
        @QueryValue("granularity") granularity: String = "day",

        /** Requested start time for telemetry data in Epoch time format. Alternatively one can use start_date in place of start_at for passing the start date as String in YYYY-MM-DD format. If no start_at is specified, defaults to midnight today, in the timezone of the system. If the start_at specified is earlier than the system’s first reported date, then the system first reported date is considered as start_at. By default start_at will appear in response. If start_date parameter is passed in the url, then start_date field will appear in response. **/
        @QueryValue("start_date") startDate: String,
    ): EnphaseConsumptionDataResponse
}