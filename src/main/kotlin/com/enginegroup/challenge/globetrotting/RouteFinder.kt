package com.enginegroup.challenge.globetrotting

class RouteFinder(private val cityMap: CitiesByDistance) {

    fun findRoute(city: String, range: Double, visited: List<String>) : List<String> {
        val others = mutableListOf<CityDistance>()
        for (other in cityMap[city] ?: error("City $city not found in list!")) {
            if (other.distance > range) {
                break;
            }

            if (!visited.contains(other.city)) {
                others.add(CityDistance(other.city, range - other.distance))
            }
        }

        val route = mutableListOf(city)
        if (others.isNotEmpty()) {
            val newVisited = mutableListOf<String>()
            newVisited.addAll(visited)
            newVisited.add(city);

            val longest = others.parallelStream()
                .map { this.findRoute(it.city, it.distance, newVisited) }
                .max { a, b -> a.size.compareTo(b.size) }
                .get()

            route.addAll(longest)
        }

        return route;
    }
}