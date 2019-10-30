package com.enginegroup.challenge.globetrotting

import kotlin.math.*

const val R = 6371.0

fun Country.calculateDistance(other: Country): Double {
    val lat1 = this.latitude!!.toRadians()
    val lat2 = other.latitude!!.toRadians()
    val longDelta = other.longitude!!.toRadians() - this.longitude!!.toRadians()

    return acos( (sin(lat1) * sin(lat2)) + (cos(lat1) * cos(lat2) * cos(longDelta)) ) * R
}

private fun Double.toRadians() = Math.toRadians(this)
