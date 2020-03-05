package com.enginegroup.challenge.globetrotting

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File

fun Any.writeToJsonFile(file: File) {
    val mapper = jacksonObjectMapper()
    mapper.registerKotlinModule()

    mapper.writeValue(file, this)
}
