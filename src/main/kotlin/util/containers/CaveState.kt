package util.containers


data class Cave(
    val id: String,
    val visitations: Int = if (id.equals("start")) 1 else 0,
    val largeCave: Boolean = id[0].isUpperCase()
) {
    fun visitable(): Boolean {
        return this.largeCave || this.visitations == 0
    }

    fun visit(): Cave {
        return this.copy(visitations = this.visitations + 1)
    }
}

data class CaveState(
    val caveStructure: Map<String, List<String>>,
    val caves: Map<String, Cave> = caveStructure.mapValues { Cave(it.key) }
) {


    fun getPaths(cave: String): List<Cave> {
        if (cave.equals("end")) {
            return listOf()
        } else {
            return caveStructure.getValue(cave)
                .map { this.caves.getValue(it) }
                .filter { it.visitable() }
        }
    }


    fun visitNeighbors(cave: String): List<Pair<String, CaveState>> {
        val updatedCaves = getPaths(cave)
            .map { it.visit() }
            .groupBy { it.id }
            .mapValues {
                assert(it.value.size == 1)
                it.value.first()
            }

        if(cave.equals("end"))
            return listOf(cave to this)

        return updatedCaves.map {
            it.key to this.copy(
                caves = caves.plus((it.key to it.value))
            )
        }
    }
}

