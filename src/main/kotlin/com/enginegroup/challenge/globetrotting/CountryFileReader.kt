package com.enginegroup.challenge.globetrotting

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File

@JsonIgnoreProperties(ignoreUnknown = true)
data class Country(
    val id: String,
    val iso2Code: String,
    val name: String,
    val capitalCity: String?,
    val longitude: Double?,
    val latitude: Double?
)

fun readCountryFile(filename: String): List<Country> {
    val mapper = jacksonObjectMapper()
    mapper.registerKotlinModule()

    val contents: JsonNode = mapper.readTree(File("../countries.json"))
    val countries: List<Country> = mapper.readValue(contents[1].traverse())

    return countries.filter{  it.capitalCity != null && it.capitalCity.isNotBlank() }
}
