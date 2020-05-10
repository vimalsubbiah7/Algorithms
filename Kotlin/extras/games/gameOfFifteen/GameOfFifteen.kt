package games.gameOfFifteen

import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {
    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        board.addNewValue(initializer)
    }

    override fun canMove() = true

    override fun hasWon(): Boolean {
        val list = mutableListOf<Int?>()
        board.getAllCells().forEach { cell ->
            list.add(board[cell])
        }

        if (list.last() != null) return false
        var value = list.first()!!
        for (i in 1..list.lastIndex) {
            list[i]?.let {
                if (it < value) return false
                value = it
            }
        }
        return true
    }

    override fun processMove(direction: Direction) {
        val cell = board.find { it == null }
        when (direction) {
            Direction.LEFT -> {
                with(board) {
                    cell?.getNeighbour(Direction.RIGHT)?.let { neighbour ->
                        board[cell] = board[neighbour].also {
                            board[neighbour] = board[cell]
                        }
                    }
                }
            }
            Direction.DOWN -> {
                with(board) {
                    cell?.getNeighbour(Direction.UP)?.let { neighbour ->
                        board[cell] = board[neighbour].also {
                            board[neighbour] = board[cell]
                        }
                    }
                }
            }
            Direction.UP -> {
                with(board) {
                    cell?.getNeighbour(Direction.DOWN)?.let { neighbour ->
                        board[cell] = board[neighbour].also {
                            board[neighbour] = board[cell]
                        }
                    }
                }
            }
            Direction.RIGHT -> {
                with(board) {
                    cell?.getNeighbour(Direction.LEFT)?.let { neighbour ->
                        board[cell] = board[neighbour].also {
                            board[neighbour] = board[cell]
                        }
                    }
                }
            }
        }

    }

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }
}


fun GameBoard<Int?>.addNewValue(initializer: GameOfFifteenInitializer) {

    var index = 0
    getAllCells().forEach { cell ->
        if (index <= initializer.initialPermutation.lastIndex) {
            this[cell] = initializer.initialPermutation[index]
        }
        index++
    }
}


