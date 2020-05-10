package board

import board.Direction.*

open class MySquareBoard(final override val width: Int) : SquareBoard {
    protected var cells : Array<Array<Cell>>

    init {
        if (width <= 0) throw IllegalArgumentException()

        cells = Array(width) { Array(width){Cell(0, 0)}}
        for (i in 0 until width) {
            val rowCells : Array<Cell> = Array(width) { Cell(0, 0) }
            for (j in 0 until width) {
                rowCells[j] = Cell(i + 1, j + 1)
            }
            cells[i] = rowCells
        }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        return if (i - 1 in 0 until width && j - 1 in 0 until width) cells[i - 1][j - 1] else null
    }

    override fun getCell(i: Int, j: Int): Cell {
        return if (i - 1 in 0 until width && j - 1 in 0 until width) cells[i - 1][j - 1] else throw IllegalArgumentException()
    }

    override fun getAllCells(): Collection<Cell> {
        var list : Collection<Cell> = ArrayList()
        for (cellsStr in cells) {
            for (cell in cellsStr) {
                list += cell
            }
        }
        return list
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        val list = mutableListOf<Cell>()
        val iterator = IntProgression.fromClosedRange(jRange.first,
                if (jRange.last < width) jRange.last else width,
                jRange.step)
        for (j in iterator) {
            list.add(cells[i - 1][j - 1])
        }
        return list
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        val list = mutableListOf<Cell>()
        val iterator: IntProgression = IntProgression.fromClosedRange(iRange.first,
                if (iRange.last < width) iRange.last else width,
                iRange.step)
        for (i in iterator) {
            list.add(cells[i - 1][j - 1])
        }
        return list
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        var nI = i
        var nJ = j
        when (direction) {
            UP -> nI = i - 1
            LEFT -> nJ = j - 1
            DOWN -> nI = i + 1
            RIGHT -> nJ = j + 1
        }

        return if (nJ in 1 until (width + 1) && nI in 1 until (width + 1)) cells[nI - 1][nJ - 1] else null

    }
}

class MyGameBoard<T>(width: Int) : MySquareBoard(width), GameBoard<T> {
    private val board = getAllCells()
            .map { it to null }
            .toMap<Cell, T?>()
            .toMutableMap()

    override operator fun get(cell: Cell): T? = board[cell]

    override operator fun set(cell: Cell, value: T?) {
        board[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> = board.entries.filter { predicate(it.value) }.map { it.key }
    override fun find(predicate: (T?) -> Boolean): Cell? = board.entries.find { predicate(it.value) }?.key
    override fun any(predicate: (T?) -> Boolean): Boolean = board.entries.any { predicate(it.value) }
    override fun all(predicate: (T?) -> Boolean): Boolean = board.entries.all { predicate(it.value) }
}


fun createSquareBoard(width: Int): SquareBoard = MySquareBoard(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = MyGameBoard(width)

