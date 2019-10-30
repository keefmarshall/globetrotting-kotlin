package com.enginegroup.challenge.globetrotting

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.math.abs

internal class DistanceCalculatorTest {

    @Test
    fun calculateDistance() {
        val country1 = Country("GBR", "United Kingdom", "London",-0.126236,51.5002)
        val country2 = Country("IRE", "Ireland", "Dublin", -6.26749, 53.3441)

        val distance = country1.calculateDistance(country2)
        assertTrue( abs(distance - 463.0) < 1.0)
    }
}