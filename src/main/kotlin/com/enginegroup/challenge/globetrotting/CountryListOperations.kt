package com.enginegroup.challenge.globetrotting

import javax.swing.Box

data class CityDistance(val city: String, val distance: Double)
typealias CitiesByDistance = Map<String, List<CityDistance>>

// I want a Map where:
// - the key is the city name
// - the value is a sorted List of Tuples
//    - the first entry in each Tuple is another city name
//    - the second entry in each Tuple is the distance from the source city
// - the list is sorted by ascending distance
fun generateLookupMap(countryList: List<Country>) : CitiesByDistance =
    countryList.map { c ->
        c.capitalCity!! to sortedListOfOtherCities(c, countryList)
    }.toMap()

fun sortedListOfOtherCities(country: Country, countryList: List<Country>) =
    countryList
        .filter { it.id != country.id }
        .map{ c -> CityDistance(c.capitalCity!!, country.calculateDistance(c)) }
        .sortedBy { it.distance }
