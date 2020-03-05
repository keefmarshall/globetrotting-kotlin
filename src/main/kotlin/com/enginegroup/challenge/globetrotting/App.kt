package com.enginegroup.challenge.globetrotting

fun main(args: Array<String>) {

    val countries = readCountryFile("src/main/resources/countries.json")
    val cityMap = generateLookupMap(countries)

//    findRoute(args, countries, cityMap)

}

private fun findRoute(
    args: Array<String>,
    countries: List<Country>,
    cityMap: CitiesByDistance
) {
    var targetCity = "Washington D.C."
    var targetRange = 5000.0

    if (args.isNotEmpty()) {
        val bits = args[0].split("|")
        targetCity = countries.find { it.name == bits[1] }?.capitalCity ?: targetCity
        targetRange = bits[0].toDouble()
    }

    val rf = RouteFinder(cityMap)
    val route = rf.findRoute(targetCity, targetRange, emptyList())

    println("${route.size} ${route.joinToString(",")}")
}
