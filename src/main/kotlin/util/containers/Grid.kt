package util.containers

data class Field<T>(val x: Int, val y: Int, var value: T, var flag: Boolean = false) {
    override fun equals(other: Any?): Boolean {
        if(this=== other) {
            return true
        }
        if(other?.javaClass != javaClass) {return false}
        other as Field<T>
        return this.x == other.x && this.y == other.y
    }

    override fun hashCode(): Int {
        val tmp = ( y +  ((x+1)/2));
        return x +  ( tmp * tmp);
    }
}

data class Grid<T>(val input: List<List<T>>) {
    var grid = input.mapIndexed { i, row ->
        row.mapIndexed { j, t -> Field(i, j, t) }
    }

    var rowLength = input.get(0).size
    var colLength = input.size

    fun getNeighbors(x: Int, y: Int, diagonals: Boolean = true) = getNeighbors(grid[x][y], diagonals)

    fun getNeighbors(field: Field<T>, diagonals: Boolean = true): List<Field<T>> {
        val x = field.x
        val y = field.y

        val startX = if (x - 1 < 0) x else x - 1
        val startY = if (y - 1 < 0) y else y - 1
        val endX = if (x + 1 >= rowLength) x else x + 1
        val endY = if (y + 1 >= colLength) y else y + 1

        var neighbors = mutableListOf<Field<T>>()
        if (diagonals) {
            for (i in startX..endX) {
                for (j in startY..endY) {
                    neighbors.add(grid[i][j])
                }
            }
        } else {
            neighbors.add(grid[startX][y])
            neighbors.add(grid[x][startY])
            neighbors.add(grid[endX][y])
            neighbors.add(grid[x][endY])
        }
        return neighbors.filter { field -> field.x != x || field.y != y }
    }

    fun forEach(action: (Field<T>) -> Unit) = grid.forEach { it.forEach(action) }
    fun <R> map(transform: (Field<T>) -> R) = grid.map { it.map(transform) }
    fun <R> mapIndexed(transform: (index: Pair<Int, Int>, Field<T>) -> R) =
        grid.mapIndexed { i, f -> f.mapIndexed { j, v -> transform(i to j, v) } }

    fun filter(filterFun: (Field<T>) -> Boolean) = grid.flatten().filter(filterFun)
    fun all(predicate: (Field<T>) -> Boolean) = grid.all { it.all(predicate) }


}