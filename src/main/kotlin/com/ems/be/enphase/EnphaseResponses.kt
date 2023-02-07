package com.ems.be.enphase

import java.sql.Timestamp

/** Endpoint: /systems (overview) **/
data class EnphaseSolarSystemsOverviewResponse(
    val total: Int?,
    val current_page: Int?,
    val size: Int?,
    val count: Int?,
    val items: String?,
    val systems: List<EnphaseSolarSystemOverview>?,
)

data class EnphaseSolarSystemOverview(
    val system_id: Int?,
    val name: String?,
    val public_name: String?,
    val timezone: String?,
    val address: SolarSystemAddress?,
    val connection_type: String?,
    val status: String?,
    val last_report_at: Timestamp?,
    val last_energy_at: Timestamp?,
    val operational_at: Timestamp?,
    val attachment_type: String?,
    val interconnect_date: String?,
    val other_references: List<String>?,
    val energy_lifetime: Int?,
    val energy_today: Int?,
    val system_size: String?,
)

data class SolarSystemAddress(
    val state: String?,
    val country: String?,
    val postal_code: String?,
)

/** Endpoint: /systems/{solarSystemId} **/
data class EnphaseSolarSystemResponse(
    val system_id: Int?,
    val name: String?,
    val public_name: String?,
    val timezone: String?,
    val address: SolarSystemAddress?,
    val connection_type: String?,
    val status: String?,
    val last_report_at: Timestamp?,
    val last_energy_at: Timestamp?,
    val operational_at: Timestamp?,
    val other_references: List<String>?
)


/** Endpoint /rgm_stats **/
data class EnphaseProductionDataResponse(
    val system_id: Int,
    val total_devices: Int,
    val intervals: List<EnphaseProductionInterval>,
    val meta: EnphaseProductionDataMeta,
    val meter_intervals: List<EnphaseProductionDataMeterInterval>,
)

data class EnphaseProductionInterval(
    val end_at: Timestamp,
    val devices_reporting: Int,
    val wh_del: Int,
)

data class EnphaseProductionDataMeta(
    val status: String,
    val last_report_at: Timestamp,
    val last_energy_at: Timestamp,
    val operational_at: Timestamp,
)

data class EnphaseProductionDataMeterInterval(
    val meter_serial_number: String,
    val envoy_serial_number: String,
    val intervals: List<EnphaseProductionStatsSpecificMeterInterval>,
)

data class EnphaseProductionStatsSpecificMeterInterval(
    val channel: Int,
    val wh_del: Int,
    val curr_w: Int,
    val end_at: Timestamp,
)


/** Endpoint: /systems/{solarSystemId}/telemetry/consumption_meter **/
data class EnphaseConsumptionDataResponse(
    val system_id: Int,
    val granularity: String,
    val total_devices: Int,
    val start_date: String,
    val end_date: String,
    val items: String,
    val intervals: List<EnphaseConsumptionInterval>,
    val meta: EnphaseConsumptionMeta
)

data class EnphaseConsumptionInterval(
    val end_at: Timestamp,
    val devices_reporting: Int,
    val enwh: Int
)

data class EnphaseConsumptionMeta(
    val status: String,
    val last_report_at: Timestamp,
    val last_energy_at: Timestamp,
    val operational_at: Timestamp
)

/** Custom Summary Endpoint **/
data class CustomEnergyDataSummary(
    val intervals: List<CustomProductionConsumptionInterval>
)

data class CustomProductionConsumptionInterval(
    val end_at: Timestamp,
    val production: Int,
    val consumption: Int
)