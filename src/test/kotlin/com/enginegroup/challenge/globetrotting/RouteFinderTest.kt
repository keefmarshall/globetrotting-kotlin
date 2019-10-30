package com.enginegroup.challenge.globetrotting

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class RouteFinderTest {


    @Test
    fun findRoute() {
        val rf = RouteFinder(generateLookupMap(countries))

        val longest = rf.findRoute("London", 1500.0, emptyList())
        assertEquals(3, longest.size)
    }
}