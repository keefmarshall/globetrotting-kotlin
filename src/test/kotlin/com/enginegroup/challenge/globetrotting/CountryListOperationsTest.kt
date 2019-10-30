package com.enginegroup.challenge.globetrotting

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class CountryListOperationsTest {

    @Test
    fun sortedListOfOtherCities() {
        val sortedList = sortedListOfOtherCities(countries[0], countries)

        assertEquals(3, sortedList.size)
        assertEquals("Paris", sortedList[0].city)
    }

    @Test
    fun generateLookupMap() {
        val cityMap = generateLookupMap(countries)
        assertEquals(4, cityMap.size)
        assertTrue(cityMap.containsKey("London"))
        assertEquals("Paris", cityMap["London"]!![0].city)
    }
}