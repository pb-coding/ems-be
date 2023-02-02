package com.ems.be.enphase

import java.sql.Timestamp

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