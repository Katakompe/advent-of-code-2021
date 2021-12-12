package util.containers


data class Cave2(
    val id: String,
    val visitations: Int = if (id.equals("start")) 1 else 0,
    val largeCave: Boolean = id[0].isUpperCase()
) {
    fun visitable(doubleVisitUsed: Boolean): Boolean {
        val maxVisitations = if (doubleVisitUsed) 0 else 1
        return this.largeCave || this.visitations <= maxVisitations
    }

    fun visit(): Cave2 {
        return this.copy(visitations = this.visitations + 1)
    }
}

data class CaveState2(
    val caveStructure: Map<String, List<String>>,
    val caves: Map<String, Cave2> = caveStructure.mapValues { Cave2(it.key) },
    var doubleVisitUsed: Boolean = false
) {


    fun getPaths(cave: String): List<Pair<Boolean, Cave2>> {
        if (cave.equals("end")) {
            return listOf()
        } else {
            return caveStructure.getValue(cave)
                .flatMap {
                    val neighborCave = this.caves.getValue(it)

                    if (neighborCave.largeCave || doubleVisitUsed)
                        listOf(doubleVisitUsed to neighborCave)
                    else {
                        if (neighborCave.visitable(doubleVisitUsed = true)) {
                            listOf(doubleVisitUsed to neighborCave)
                        } else {
                            listOf(true to neighborCave)
                        }
                    }
                }
                .filter {
                    it.second.visitable(doubleVisitUsed)
                }
                .filter { !it.second.id.equals("start") }
        }
    }


    fun visitNeighbors(cave: String): List<Pair<String, CaveState2>> {
        val updatedCaves = getPaths(cave)
            .map { it.first to it.second.visit() }
            .groupBy { it.second.id }
            .mapValues {
                assert(it.value.size == 1)
                it.value.first()
            }

        if (cave.equals("end"))
            return listOf(cave to this)

        return updatedCaves.map {
            it.key to this.copy(
                caves = caves.plus((it.key to it.value.second)),
                doubleVisitUsed = it.value.first
            )
        }
    }
}

