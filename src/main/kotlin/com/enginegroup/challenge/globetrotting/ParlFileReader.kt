package com.enginegroup.challenge.globetrotting

import java.io.File

object ParlFileReader {
    private const val PARL_FILE_PATH =
        "src/main/resources/API_SG.GEN.PARL.ZS_DS2_en_csv_v2_713162/API_SG.GEN.PARL.ZS_DS2_en_csv_v2_713162.csv"

    fun readParlFile(): Map<String, Double> {
        val lines = File(PARL_FILE_PATH).readLines().drop(5)  // seems to be a 5-line header
        // NB this is very crude - should really use a formal CSV reader but the header lines cause problems
        // It only pulls out the most recent stat, and only includes lines that have got any stats in at all
        return lines.map { line ->
            val bits = line.splitCsvLine()
            val countryIso3 = bits[1]
            val percentages = bits.drop(4) // years start from column 5
            val validPercentages = percentages.filter { it.length > 0 }
            if (validPercentages.isNotEmpty()) {
                val mostRecentPerc = validPercentages.last().toDouble()
                Pair(countryIso3, mostRecentPerc)
            } else null
        }.filterNotNull().toMap()
    }


}

// Can't believe I still have to write this in this day and age
// Really shoddy :) - can't handled escaped quotes
fun String.splitCsvLine(): List<String> {
    val bits = mutableListOf<String>()
    var inQuotes = false
    var buffer: String = ""
    (this.indices).forEach {
        when {
            this[it] == '\"' -> inQuotes = !inQuotes
            this[it] == ',' && !inQuotes -> { bits.add(buffer); buffer = "" }
            else -> buffer += this[it]
        }
    }

    // The Parl file lines always end with a comma - but for a more generic CSV file this would be an additional
    // entry. We risk losing the last entry on the line if we don't add it here:
    if (!inQuotes) {
        bits.add(buffer)
    }

    return bits
}
