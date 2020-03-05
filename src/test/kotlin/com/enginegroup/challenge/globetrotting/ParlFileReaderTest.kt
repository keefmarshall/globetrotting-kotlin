package com.enginegroup.challenge.globetrotting

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.File

internal class ParlFileReaderTest {

    val TEST_LINE_1 = """
            "Aruba","ABW","Proportion of seats held by women in national parliaments (%)","SG.GEN.PARL.ZS","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
        """.trimIndent()


    private val TEST_LINE_2 = """
        "Liechtenstein","LIE","Proportion of seats held by women in national parliaments (%)","SG.GEN.PARL.ZS","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","4","","","","","","","4","4","4","4","12","12","12","12","24","24","24","24","24","24","24","24","20","20","20","20","12","12","12",
""".trimIndent()


    @Test
    fun readParlFile() {
        val data = ParlFileReader.readParlFile()
        assertNotNull(data)
        assertEquals(240, data.size)
        assertNull(data["ABW"]) // no data for Aruba
        assertNotNull(data["GBR"])
        assertEquals(27.87, data["AFG"])

        assertNotNull(data["LIE"])
        assertEquals(12.0, data["LIE"])
    }

    @Test
    fun splitCsvLine() {
        val bits = TEST_LINE_1.splitCsvLine()
        assertEquals(65, bits.size)
        assertEquals("ABW", bits[1])
    }

    @Test
    fun splitCsvLine2() {
        val bits = TEST_LINE_2.splitCsvLine()
        assertEquals(65, bits.size)
        assertEquals("LIE", bits[1])
    }

}