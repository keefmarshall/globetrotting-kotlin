package com.enginegroup.challenge.globetrotting

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CountryFileReaderTest {

    @Test
    fun readCountryFile() {
        val countries = readCountryFile("src/main/resources/countries.json")
        assertEquals(211, countries.size)
    }
}