package com.enginegroup.challenge.globetrotting

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.File

internal class BestChoiceTest {

    @Test
    fun findBestChoice() {

        val bc = BestChoice()

        val choice = bc.findBestChoice("GBR")[0]
        assertEquals("BEL", choice.country.id)

        val choice2 = bc.findBestChoice("USA")[0]
        assertEquals("CUB", choice2.country.id)
    }

    @Test
    fun findAllBestChoices() {
        val bc = BestChoice()

        val allChoices = bc.findAllBestChoices()
        assertEquals(193, allChoices.size)
    }

    @Test
    fun writeBestChoicesToFile() {
        val bc = BestChoice()

        bc.writeBestChoicesToFile(File("src/test/resources/best-choices.json"))
    }

    @Test
    fun writeParlDataToJsonFile() {
        val bc = BestChoice()

        bc.writeParlDataToJsonFile(File("src/test/resources/parl_data.json"))
    }

}