package com.ems.be.enphase

import java.sql.Timestamp

// /rgm_stats
data class EnphaseProductionStatsResponse(
    val system_id: Int,
    val total_devices: Int,
    val intervals: List<EnphaseProductionStatsInterval>,
    val meta: EnphaseProductionStatsMeta,
    val meter_intervals: List<EnphaseProductionStatsMeterInterval>,
)

data class EnphaseProductionStatsInterval(
    val end_at: String,
    val devices_reporting: Int,
    val wh_del: Int,
)

data class EnphaseProductionStatsMeta(
    val status: String,
    val last_report_at: Timestamp,
    val last_energy_at: Timestamp,
    val operational_at: Timestamp,
)

data class EnphaseProductionStatsMeterInterval(
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

// /systems (overview)
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

// /systems/{solarSystemId}
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