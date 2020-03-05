package com.enginegroup.challenge.globetrotting

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File

typealias Choices = List<Choice>

data class Choice(val country: Country, val score: Double)

class BestChoice() {

    private val countries = readCountryFile("src/main/resources/countries.json")
    private val cityMap   = generateLookupMap(countries)
    private val parlData  = ParlFileReader.readParlFile()

    fun findBestChoice(countryIso3: String): Choices {

        val homeCountry = findCountryFromIso(countryIso3)
        val homeScore = parlData[countryIso3] ?: error("Can't find parl data for $countryIso3")

        val neighbours = cityMap[homeCountry.capitalCity] ?: error("city ${homeCountry.capitalCity} not found")
        return neighbours.asSequence()
            .map { findCountryFromCapital(it.city) }
            .map { Choice(it,  parlData[it.id] ?: -1.0)  }
            .filterNot { it.score == -1.0 }
            .take(10)
            .plusElement(Choice(homeCountry, homeScore))
            .sortedByDescending { it.score }
            .toList()
    }

    fun findAllBestChoices(): Map<Country, Choices> {
        val allValidCountries = countries.map { it.id }.intersect(parlData.keys) // ISO3 IDs for all countries in both
        return allValidCountries
            .map { iso3 -> countries.find { it.id == iso3 }!!}
            .map { Pair(it, findBestChoice(it.id)) }
            .toMap()
    }

    fun writeBestChoicesToFile(file: File) {
        val bestChoices = findAllBestChoices()
            .map { Pair(it.key.iso2Code, it.value) }
            .toMap()

        bestChoices.writeToJsonFile(file)
    }


    fun writeParlDataToJsonFile(file: File) {
        val data = ParlFileReader.readParlFile()
        data.filter { pdat -> countries.find { it.id == pdat.key } != null }
            .map { Pair(findCountryFromIso(it.key).iso2Code, it.value) }
            .toMap()
            .writeToJsonFile(file)
    }

    fun findCountryFromIso(countryIso3: String) =
        countries.find { it.id == countryIso3 } ?: error("Code $countryIso3 not found")
    fun findCountryFromCapital(capital: String) =
        countries.find { it.capitalCity == capital }!!
}